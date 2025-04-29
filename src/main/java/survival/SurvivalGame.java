package survival;

import survival.controller.game.GameController;
import survival.dao.DatabaseManager;
import survival.view.GameView;

/**
 * 게임 초기화 및 시작 로직을 담당하는 클래스
 */
public class SurvivalGame {
    // 필드
    private GameController gameController;
    private GameView view;
    private boolean initialized;
    
    /**
     * 생성자
     * @param view 게임 화면
     */
    public SurvivalGame(GameView view) {
        this.view = view;
        this.gameController = new GameController(view);
        this.initialized = false;
    }
    
    /**
     * 게임 시스템 초기화
     * @return 초기화 성공 여부
     */
    public boolean initialize() {
        // 데이터베이스 초기화
        boolean dbInitSuccess = DatabaseManager.getInstance().initDatabase();
        
        // 초기화 실패 시 오류 메시지 표시
        if (!dbInitSuccess) {
            view.displayError("데이터베이스 초기화 실패로 게임을 시작할 수 없습니다.");
            return false;
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
            view.displayMenu();
            
            // 사용자 입력 받기
            int choice = view.getIntInput(1, 3);
            
            switch (choice) {
                case 1: // 게임 시작
                    startGame();
                    break;
                case 2: // 업적 확인
                    gameController.processAchievements();
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
        // 데이터베이스 연결 종료
        DatabaseManager.getInstance().closeConnection();
    }
} 
