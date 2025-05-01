package survival.util;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 게임 오디오(배경음악, 효과음) 관리 클래스
 */
public class AudioManager {
    
    // 싱글톤 인스턴스
    private static AudioManager instance;
    
    // 효과음용 오디오 클립 맵
    private Map<String, Clip> sfxClips;
    
    // 배경음악 관련 필드
    private Map<String, String> bgmFileNames;
    private Thread currentBgmThread;
    private BGMPlayer currentBgmPlayer;
    private String currentBgmId;
    
    // 음량 설정 (0.0 ~ 1.0)
    private float bgmVolume = 0.5f;
    private float sfxVolume = 0.7f;
    
    // 오디오 타입
    public enum AudioType {
        BGM, // 배경음악
        SFX  // 효과음
    }
    
    // 기본 오디오 ID
    public static final String BGM_MENU = "menu";
    public static final String BGM_GAME = "game";
    public static final String BGM_VICTORY = "victory";
    public static final String BGM_DEFEAT = "defeat";
    
    /**
     * 생성자
     */
    private AudioManager() {
        sfxClips = new HashMap<>();
        bgmFileNames = new ConcurrentHashMap<>();
    }
    
    /**
     * 싱글톤 인스턴스 반환
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    
    /**
     * 오디오 파일 로드
     * 
     * @param audioId 오디오 식별자
     * @param fileName 파일 이름
     * @param type 오디오 타입
     * @return 로드 성공 여부
     */
    public boolean loadAudio(String audioId, String fileName, AudioType type) {
        try {
            if (type == AudioType.BGM) {
                // 배경음악은 파일 이름만 저장해두고 실제 재생 시 로드
                bgmFileNames.put(audioId, fileName);
                System.out.println("배경음악 등록: " + audioId + " (" + fileName + ")");
                return true;
            } else {
                // 효과음은 바로 로드하여 사용
                return loadSfx(audioId, fileName);
            }
        } catch (Exception e) {
            System.err.println("오디오 로드 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 효과음 로드
     * 
     * @param audioId 오디오 식별자
     * @param fileName 파일 이름
     * @return 로드 성공 여부
     */
    private boolean loadSfx(String audioId, String fileName) {
        try {
            // 리소스 경로 설정
            String resourcePath = "/audio/" + fileName;
            InputStream is = getClass().getResourceAsStream(resourcePath);
            
            // 리소스를 찾지 못한 경우 클래스로더로 시도
            if (is == null) {
                String relativeResourcePath = resourcePath.substring(1);
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(relativeResourcePath);
                
                if (is == null) {
                    System.err.println("오디오 리소스를 찾을 수 없음: " + resourcePath);
                    return false;
                }
            }
            
            // 버퍼링된 스트림으로 변환
            BufferedInputStream bis = new BufferedInputStream(is);
            
            // 오디오 스트림 생성
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis);
            
            // 오디오 포맷 가져오기
            AudioFormat format = audioStream.getFormat();
            
            // 오디오 클립 생성
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            
            // 볼륨 설정을 위한 컨트롤 가져오기
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                
                // 음량을 dB 단위로 변환 (0.0 ~ 1.0 범위를 dB로 변환)
                float dB = (float) (Math.log10(sfxVolume) * 20.0);
                
                // 범위 확인 및 조정
                float min = gainControl.getMinimum();
                float max = gainControl.getMaximum();
                if (dB < min) dB = min;
                if (dB > max) dB = max;
                
                gainControl.setValue(dB);
            }
            
            // 오디오 클립 저장
            sfxClips.put(audioId, clip);
            System.out.println("효과음 로드 성공: " + audioId + " (" + resourcePath + ")");
            return true;
            
        } catch (UnsupportedAudioFileException e) {
            System.err.println("지원되지 않는 오디오 파일 형식: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("오디오 파일 IO 오류: " + e.getMessage());
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("오디오 라인 사용 불가: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("효과음 로드 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 배경음악 재생
     * 
     * @param audioId 오디오 ID
     */
    public void playBgm(String audioId) {
        // 현재 배경음악이 같은 것인지 확인
        if (currentBgmId != null && currentBgmId.equals(audioId) && 
            currentBgmPlayer != null && currentBgmPlayer.isPlaying()) {
            System.out.println("이미 재생 중인 배경음악입니다: " + audioId);
            return;
        }
        
        // 현재 배경음악 중지
        stopCurrentBgm();
        
        // 배경음악 파일 이름 가져오기
        String fileName = bgmFileNames.get(audioId);
        if (fileName == null) {
            System.err.println("등록되지 않은 배경음악입니다: " + audioId);
            return;
        }
        
        try {
            // 새 배경음악 플레이어 생성
            currentBgmPlayer = new BGMPlayer(audioId, fileName, bgmVolume);
            
            // 쓰레드 생성 및 시작
            currentBgmThread = new Thread(currentBgmPlayer, "BGM-Player-" + audioId);
            currentBgmThread.setDaemon(true); // 데몬 쓰레드로 설정하여 메인 쓰레드 종료 시 함께 종료되도록 함
            currentBgmThread.start();
            
            // 현재 BGM ID 설정
            currentBgmId = audioId;
            
            System.out.println("[디버그] 배경음악 재생 쓰레드 시작: " + audioId);
            
            // 쓰레드 시작 메시지가 출력되도록 잠시 대기
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // 무시
            }
        } catch (Exception e) {
            System.err.println("배경음악 재생 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 현재 배경음악 중지
     */
    public void stopCurrentBgm() {
        if (currentBgmPlayer != null) {
            // 플레이어에 중지 요청
            currentBgmPlayer.requestStop();
            
            // 쓰레드가 정상적으로 종료될 때까지 짧게 대기
            if (currentBgmThread != null && currentBgmThread.isAlive()) {
                try {
                    currentBgmThread.join(1000); // 최대 1초 대기
                    
                    // 여전히 살아있으면 강제 중단
                    if (currentBgmThread.isAlive()) {
                        currentBgmThread.interrupt();
                    }
                } catch (InterruptedException e) {
                    System.err.println("배경음악 쓰레드 종료 대기 중 인터럽트 발생: " + e.getMessage());
                }
            }
            
            currentBgmPlayer = null;
            currentBgmThread = null;
            currentBgmId = null;
        }
    }
    
    /**
     * 배경음악 일시정지
     */
    public void pauseBgm() {
        if (currentBgmPlayer != null && currentBgmPlayer.isPlaying()) {
            currentBgmPlayer.pause();
        }
    }
    
    /**
     * 배경음악 재개
     */
    public void resumeBgm() {
        if (currentBgmPlayer != null && currentBgmPlayer.isPaused()) {
            currentBgmPlayer.resume();
        }
    }
    
    /**
     * 효과음 재생
     * 
     * @param audioId 오디오 ID
     */
    public void playSfx(String audioId) {
        Clip clip = sfxClips.get(audioId);
        if (clip != null) {
            // 처음부터 재생하기 위해 프레임 위치 리셋
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("효과음을 찾을 수 없음: " + audioId);
        }
    }
    
    /**
     * 배경음악 볼륨 설정
     * 
     * @param volume 볼륨 (0.0 ~ 1.0)
     */
    public void setBgmVolume(float volume) {
        if (volume < 0.0f || volume > 1.0f) {
            return;
        }
        
        this.bgmVolume = volume;
        
        // 현재 재생중인 배경음악 볼륨 업데이트
        if (currentBgmPlayer != null) {
            currentBgmPlayer.setVolume(volume);
        }
    }
    
    /**
     * 효과음 볼륨 설정
     * 
     * @param volume 볼륨 (0.0 ~ 1.0)
     */
    public void setSfxVolume(float volume) {
        if (volume < 0.0f || volume > 1.0f) {
            return;
        }
        
        this.sfxVolume = volume;
        
        // 모든 효과음 볼륨 설정
        for (Clip clip : sfxClips.values()) {
            if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                
                // 음량을 dB 단위로 변환
                float dB = (float) (Math.log10(volume) * 20.0);
                
                // 범위 확인 및 조정
                float min = gainControl.getMinimum();
                float max = gainControl.getMaximum();
                if (dB < min) dB = min;
                if (dB > max) dB = max;
                
                gainControl.setValue(dB);
            }
        }
    }
    
    /**
     * 모든 오디오 리소스 해제
     */
    public void dispose() {
        // 현재 재생 중인 배경음악 중지
        stopCurrentBgm();
        
        // 모든 효과음 클립 정지 및 리소스 해제
        for (Clip clip : sfxClips.values()) {
            if (clip != null) {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.close();
            }
        }
        
        // 맵 비우기
        sfxClips.clear();
        bgmFileNames.clear();
    }
    
    /**
     * 현재 재생 중인 배경음악 ID 반환
     * 
     * @return 현재 재생 중인 배경음악 ID (없으면 null)
     */
    public String getCurrentBgmId() {
        return currentBgmId;
    }
    
    /**
     * 배경음악 재생 여부 확인
     * 
     * @return 배경음악 재생 중이면 true
     */
    public boolean isBgmPlaying() {
        return currentBgmPlayer != null && currentBgmPlayer.isPlaying();
    }
    
    /**
     * 배경음악 일시정지 여부 확인
     * 
     * @return 배경음악 일시정지 상태이면 true
     */
    public boolean isBgmPaused() {
        return currentBgmPlayer != null && currentBgmPlayer.isPaused();
    }
} 