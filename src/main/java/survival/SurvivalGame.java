package survival;

import survival.controller.game.GameController;
import survival.dao.DatabaseManager;
import survival.util.AudioManager;
import survival.util.AudioManager.AudioType;
import survival.view.GameView;
import survival.view.UIConstants;

/**
 * 게임 초기화 및 시작 로직을 담당하는 클래스
 */
public class SurvivalGame {
    // 필드
    private GameController gameController;
    private GameView view;
    private boolean initialized;
    private AudioManager audioManager;

    /**
     * 생성자
     * 
     * @param view 게임 화면
     */
    public SurvivalGame(GameView view) {
        this.view = view;
        this.gameController = new GameController(view);
        this.initialized = false;
        this.audioManager = AudioManager.getInstance();
        
        // 오디오 파일 로드
        initializeAudio();
    }

    /**
     * 오디오 시스템 초기화
     */
    private void initializeAudio() {
        
        audioManager.loadAudio(AudioManager.BGM_MENU, "menu_bgm.wav", AudioType.BGM);
        audioManager.loadAudio(AudioManager.BGM_GAME, "game_bgm.wav", AudioType.BGM);
        audioManager.loadAudio(AudioManager.BGM_VICTORY, "victory_bgm.wav", AudioType.BGM);
        audioManager.loadAudio(AudioManager.BGM_DEFEAT, "defeat_bgm.wav", AudioType.BGM);
    
    }

    /**
     * 게임 시스템 초기화
     * 
     * @return 초기화 성공 여부
     */
    public boolean initialize() {
        // 데이터베이스 초기화
        boolean dbInitSuccess = DatabaseManager.getInstance().initDatabase();

        // 초기화 실패 시 오류 메시지 표시
        if (!dbInitSuccess) {
            view.displayError("데이터베이스 초기화 실패로 게임을 시작할 수 없습니다.");
            return true; // 임시로 변경했습니다. 4/30 15:53 최호철
        }

        initialized = true;
        return true;
    }

    /**
     * 게임 메인 루프 실행
     */
    public void run() {
        // 초기화 확인
        if (!initialized && !initialize()) {
            return;
        }

        // 시작 메시지 출력
        view.showMessage("무인도에 불시착한 당신! 생존을 위한 도전이 시작됩니다!");
        view.showMessage("================================");

        boolean running = true;
        while (running) {
            // 메인 메뉴 표시
            view.displayLoginMenu();

            // 사용자 입력 받기
            int choice = view.getIntInput(1, 3);

            switch (choice) {
                case 1: // 로그인 화면 구현
                    // 아이디 입력
                    view.showMessageNoln(UIConstants.Login_ID);
                    String id = view.getStringInput();
                    // 비밀번호 입력
                    view.showMessageNoln(UIConstants.Login_PW);
                    String pw = view.getStringInput();

                    // 로그인 처리
                    boolean isSuccess = gameController.handleLogin(id, pw);
                    if (!isSuccess) {
                        view.showMessage(UIConstants.LOGIN_FAIL);
                        break;
                    }
                    
                    view.showMessage(UIConstants.LOGIN_SUCCESS);
                    // 로그인 성공 시 메뉴 배경음악 재생
                    audioManager.playBgm(AudioManager.BGM_MENU);

                    boolean shouldLoop = true;
                    while (shouldLoop) {
                        view.displayMenu();
                        int choice2 = view.getIntInput(1, 3);
                        switch (choice2) {
                            case 1:
                                // 게임 시작 시 게임 배경음악으로 변경
                                audioManager.playBgm(AudioManager.BGM_GAME);
                                gameController.startGame();
                                // 게임 종료 후에는 승리/패배 BGM이 이미 재생 중이므로 
                                // 메뉴 음악은 사용자가 엔터 키를 눌러 메뉴로 돌아올 때 자동 재생
                                break;
                            case 2:
                                gameController.processAchievements();
                                break;
                            case 3:
                                view.showMessage("게임을 종료합니다");
                                shouldLoop = false;
                                running = false;
                                break;
                        }
                    }
                    break;

                case 2: // 회원 가입 화면 구현
                    // 가입할 아이디 입력
                    view.showMessageNoln(UIConstants.INPUT_USERNAME);
                    String userid = view.getStringInput();
                    // 가입할 비밀번호 입력
                    view.showMessageNoln(UIConstants.INPUT_PASSWORD);
                    String password = view.getStringInput();
                    boolean isRegiSuccess = gameController.handleRegistration(userid, password);
                    if (isRegiSuccess) {
                        view.showMessage(UIConstants.REGISTER_SUCCESS);
                    } else {
                        view.showMessage(UIConstants.REGISTER_FAIL);
                    }
                    break;
                case 3: // 게임 종료
                    view.showMessage("게임을 종료합니다. 안녕히 가세요!");
                    running = false;
                    break;
            }
        }
    }

    /**
     * 게임 시작 처리
     */
    public void startGame() {
        // 게임 시작 - 바로 게임 컨트롤러의 게임 시작 메서드 호출
        gameController.startGame();
    }

    /**
     * 게임 종료 시 리소스 정리
     */
    public void cleanup() {
        // 배경음악 중지 및 리소스 해제
        if (audioManager != null) {
            audioManager.stopCurrentBgm();
            audioManager.dispose();
        }
        
        // 데이터베이스 연결 종료
        DatabaseManager.getInstance().closeConnection();
    }
}
