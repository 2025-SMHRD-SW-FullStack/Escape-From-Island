package survival.util;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 배경음악 재생을 위한 쓰레드 클래스
 */
public class BGMPlayer implements Runnable {

    // 오디오 클립
    private Clip audioClip;

    // 오디오 ID
    private String audioId;

    // 재생 상태 플래그
    private volatile boolean isPlaying = false;
    private volatile boolean isPaused = false;
    private volatile boolean stopRequested = false;

    // 볼륨 설정
    private float volume = 0.5f;

    // 사운드 리소스 경로
    private final String resourcePath;

    /**
     * 생성자
     * 
     * @param audioId  오디오 ID
     * @param fileName 파일 이름
     * @param volume   볼륨 (0.0 ~ 1.0)
     */
    public BGMPlayer(String audioId, String fileName, float volume) {
        this.audioId = audioId;
        this.resourcePath = "/audio/" + fileName;
        this.volume = volume;
    }

    /**
     * 쓰레드 실행 메서드
     */
    @Override
    public void run() {
        try {
            // 오디오 리소스 로드
            loadAudio();

            // 재생 중 플래그 설정
            isPlaying = true;

            // 오디오 클립이 성공적으로 로드되었는지 확인
            if (audioClip != null) {
                // 처음부터 재생
                audioClip.setFramePosition(0);

                // 무한 반복 설정
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);

                // 재생 시작
                audioClip.start();

                // 메시지가 출력되도록 잠시 대기
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // 무시
                }

                // 오디오 클립이 재생 중이거나 중지 요청이 없는 동안 쓰레드 유지
                while (isPlaying && !stopRequested) {
                    try {
                        // 일시정지 상태 체크
                        synchronized (this) {
                            while (isPaused && !stopRequested) {
                                wait(); // 일시정지 상태에서 통지 대기
                            }
                        }

                        // 중지 요청 확인
                        if (stopRequested) {
                            break;
                        }

                        // 재생이 끝났는지 확인 (무한 반복 설정이 있어도 확인)
                        if (!audioClip.isRunning() && isPlaying && !isPaused) {
                            audioClip.setFramePosition(0);
                            audioClip.start();
                        }

                        // CPU 부하 감소를 위한 짧은 슬립
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                // 재생 종료
                if (audioClip.isRunning()) {
                    audioClip.stop();
                }
                audioClip.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    /**
     * 오디오 파일 로드
     */
    private void loadAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream is = getClass().getResourceAsStream(resourcePath);

        // 리소스를 찾지 못한 경우 클래스로더로 시도
        if (is == null) {
            String relativeResourcePath = resourcePath.substring(1);
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(relativeResourcePath);

            if (is == null) {
                throw new IOException("오디오 리소스를 찾을 수 없음: " + resourcePath);
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
        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);

        // 볼륨 설정
        setVolume(volume);
    }

    /**
     * 볼륨 설정
     * 
     * @param volume 볼륨 (0.0 ~ 1.0)
     */
    public synchronized void setVolume(float volume) {
        if (volume < 0.0f || volume > 1.0f) {
            return;
        }

        this.volume = volume;

        if (audioClip != null && audioClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);

            // 음량을 dB 단위로 변환
            float dB = (float) (Math.log10(volume) * 20.0);

            // 범위 확인 및 조정
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            if (dB < min)
                dB = min;
            if (dB > max)
                dB = max;

            gainControl.setValue(dB);
        }
    }

    /**
     * 일시정지 설정
     */
    public synchronized void pause() {
        if (isPlaying && !isPaused && audioClip != null) {
            isPaused = true;
            audioClip.stop();
        }
    }

    /**
     * 재생 재개
     */
    public synchronized void resume() {
        if (isPlaying && isPaused && audioClip != null) {
            isPaused = false;
            audioClip.start();
            notifyAll(); // 대기 중인 쓰레드에 통지
        }
    }

    /**
     * 재생 중지 요청
     */
    public synchronized void requestStop() {
        stopRequested = true;
        isPaused = false;
        notifyAll(); // 일시정지 상태에서 대기 중인 경우 깨움
    }

    /**
     * 리소스 정리
     */
    private void cleanup() {
        isPlaying = false;
        if (audioClip != null) {
            if (audioClip.isRunning()) {
                audioClip.stop();
            }
            audioClip.close();
            audioClip = null;
        }
    }

    /**
     * 오디오 ID 반환
     */
    public String getAudioId() {
        return audioId;
    }

    /**
     * 재생 중 여부 확인
     */
    public boolean isPlaying() {
        return isPlaying && !isPaused;
    }

    /**
     * 일시정지 상태 확인
     */
    public boolean isPaused() {
        return isPaused;
    }
}