package survival.controller.game;

import survival.dto.GameEndDTO;
import survival.dto.PlayerDTO;
import survival.model.game.GameState;
import survival.model.game.ActionType;
import survival.controller.user.AuthController;
import survival.controller.user.AchievementController;
import survival.util.DTOConverter;
import survival.view.GameView;

import java.util.List;

/**
 * 게임 전체 흐름을 제어하는 컨트롤러 클래스
 */
public class GameController {
    // 필드
    private GameState gameState;
    private ActionController actionController;
    private GameView view;
    private AuthController authController;
    private AchievementController achievementController;
    
    /**
     * 생성자
     * @param view 게임 화면
     */
    public GameController(GameView view) {
        this.view = view;
        this.actionController = new ActionController(view);
        this.achievementController = new AchievementController();
        this.authController = new AuthController();
    }
    
    /**
     * 게임 시작
     */
    public void startGame() {
        // 게임 상태 초기화
        gameState = new GameState();
        processGame();
    }
    
    /**
     * 하루 진행 처리
     */
    public void processDay() {
        // 메소드 구현 부분
    	// 하루 동안 발생하는 게임의 핵심 로직
    	// 자원 변화, 상태 변화(허기, ..), 이벤트 발생
    }
    
    /**
     * 하루 종료 처리
     */
    public void endDay() {
        // 메소드 구현 부분
    	// 하루 결산 , 다음날 준비
    	// ap관련 처리
    	// 만약 7일차면 GameState 객체의 endGame <- 게임 종료 처리
    	// 7일차가 아니면  다음날로 진행하는 메소드인 GameState 객체의
    	// nextDay를 불러오면 되나?
    }
    
    /**
     * 승리 조건 확인
     * @return 승리 여부
     */
    public boolean checkVictory() {
        return false; // 임시 반환값
    }
    
    /**
     * 로그인 처리
     * @return 로그인 성공 여부
     */
    public boolean handleLogin() {
    	// DB에 있는 회원 아이디와 비밀번호가 같다면 로그인 성공 처리
        return false; // 임시 반환값
    }
    
    /**
     * 회원가입 처리
     * @return 회원가입 성공 여부
     */
    public boolean handleRegistration() {
    	
        return false; // 임시 반환값
    }
    
    /**
     * 게임 진행
     */
    public void processGame() {
        if (gameState == null) {
            startGame();
            return;
        }
        
        // 플레이어 상태 표시 - DTO 사용
        PlayerDTO playerDTO = DTOConverter.convertToDTO(gameState.getPlayer());
        view.displayPlayerStatus(playerDTO);
        
        // 행동 메뉴 표시
        view.showMessage("\n행동을 선택하세요:");
        view.showMessage("1. 탐험하기");
        view.showMessage("2. 아이템 제작");
        view.showMessage("3. 휴식하기");
        
        int choice = view.getIntInput(1, 3);
        
        // 선택한 행동 처리
        boolean result = false;
        switch (choice) {
            case 1: // 탐험
                result = handleAction(ActionType.EXPLORE, null);
                break;
            case 2: // 제작
                // 제작할 아이템 선택 로직
                result = handleAction(ActionType.CRAFT, "임시아이템");
                break;
            case 3: // 휴식
                result = handleAction(ActionType.REST, null);
                break;
        }
        
        // 결과 처리
        if (!result) {
            view.showMessage("행동력이 부족합니다.");
        }
    }
    
    /**
     * 플레이어 행동 처리
     * @param actionType 행동 유형
     * @param itemName 아이템 이름 (제작 시)
     * @return 처리 결과
     */
    private boolean handleAction(ActionType actionType, String itemName) {
        if (actionController == null || gameState == null) {
            return false;
        }
        
        boolean result = actionController.performAction(gameState.getPlayer(), actionType, itemName);
        
        // 행동 결과에 따른 메시지 표시
        if (result) {
            switch(actionType) {
                case EXPLORE:
                    view.showMessage("탐험을 완료했습니다.");
                    break;
                case CRAFT:
                    view.showMessage("아이템 제작을 완료했습니다.");
                    break;
                case REST:
                    view.showMessage("휴식을 취했습니다. 체력이 회복되었습니다.");
                    break;
            }
        }
        
        return result;
    }
    
    /**
     * 업적 처리
     */
    public void processAchievements() {
        if (achievementController == null) {
            view.showMessage("업적 시스템이 초기화되지 않았습니다.");
            return;
        }
        
        // 업적 데이터 가져오기
        List<String> achievements = achievementController.getAchievementTitles();
        int totalAchievements = achievementController.getTotalAchievementsCount();
        
        // 뷰에 업적 데이터 전달
        view.displayAchievements(achievements, totalAchievements);
    }
} 
