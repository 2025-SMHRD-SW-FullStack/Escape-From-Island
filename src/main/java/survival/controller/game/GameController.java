package survival.controller.game;

import survival.model.game.GameEndState;
import survival.model.game.GameState;
import survival.model.game.Inventory;
import survival.model.game.Player;
import survival.model.user.User;
import survival.model.game.ActionType;
import survival.controller.user.AuthController;
import survival.controller.user.AchievementController;
import survival.util.Constants;
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
     * 
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
        Player player = new Player(100, 100, Constants.INITIAL_AP, new Inventory());

        gameState = new GameState(Constants.INITIAL_DAY, player);
        
        // 업적 획득 목록 초기화
        if (achievementController != null) {
            achievementController.resetEarnedAchievements();
        }
        
        processGame();

        // 게임 시작 시 업적 체크 (첫 생존자 업적)
        if (achievementController != null) {
            achievementController.checkAndUpdateAchievements(gameState);
        }
    }

    /**
     * 하루 종료 처리
     */
    public void endDay() {
        // 메소드 구현 부분

        // 다음 날로 넘어가기
        gameState.nextDay(); // 일차 +1 , 여기에 AP도 자동으로 추가됨
        view.showMessage("다음 날로 넘어갑니다. 행동력이 회복되었습니다!");

        // 하루 종료 후 업적 체크 (생존 전문가 업적)
        if (achievementController != null) {
            achievementController.checkAndUpdateAchievements(gameState);
        }
    }

    /**
     * 로그인 처리
     * 
     * @return 로그인 성공 여부
     */
    public boolean handleLogin(String id, String pw) {
        boolean success = authController.login(id, pw);

        if (success) {
            User user = authController.getCurrentUser();
            achievementController.setCurrentUser(user);
        }

        return success;
    }

    /**
     * 회원가입 처리
     * 
     * @return 회원가입 성공 여부
     */
    public boolean handleRegistration(String id, String pw) {

        boolean membership = authController.register(id, pw);
        if (membership) {
            return true;
        }
        return false;
    }

    /**
     * 게임 진행
     */
    public void processGame() {
        // 게임 상태가 초기화되지 않았다면 게임 시작 처리
        if (gameState == null) {
            startGame();
            return;
        }

        // 게임 실행 플래그
        boolean isGameRunning = true;

        // 날짜 기반 메인 루프
        while (isGameRunning && gameState.getDay() <= Constants.DAYS_TO_ESCAPE) {
            // 오늘이 몇 일차인지 출력
            view.displayDay(gameState.getDay());

            // 플레이어 상태 표시
            Player player = gameState.getPlayer();

            // 일일 행동 루프 - 행동력이 0이 될 때까지 반복
            while (isGameRunning && player.getAp() > 0) {
                // 행동 메뉴 표시
                view.displayPlayerStatus(player);

                view.showMessage("\n행동을 선택하세요:");
                view.showMessage("1. 탐험하기");
                
                // 뗏목 제작 가능 여부 확인
                boolean canCraftRaft = actionController.canCraftRaft(player);
                String craftOption = "2. 뗏목 제작";
                if (canCraftRaft) {
                    craftOption += " (가능)";
                }
                view.showMessage(craftOption);
                
                view.showMessage("3. 휴식하기");
                int choice = view.getIntInput(1, 3);

                boolean isDone = false;
                switch (choice) {
                    case 1: // 탐험
                        isDone = handleAction(ActionType.EXPLORE);
                        break;
                    case 2: // 뗏목 제작
                        isDone = handleAction(ActionType.CRAFT);
                        break;
                    case 3: // 휴식
                        isDone = handleAction(ActionType.REST);
                        break;
                }

                // 결과 처리
                if (!isDone) {
                    // 행동 실패 (handleAction 메서드에서 메시지 처리)
                }
                
                // 게임 종료 여부 체크 
                if (isGameEnded()) {
                    isGameRunning = false;
                    break;
                }
            }

            // 하루 종료 처리
            if (isGameRunning) {
                if (player.getAp() <= 0) {
                    view.showMessage("밤이 깊었습니다. 잠이 쏟아져 근처에서 잠자리 준비를 하고 잠에 들었습니다.");
                }
                
                // 마지막 날이 아니면 다음 날로 넘어감
                if (gameState.getDay() < Constants.DAYS_TO_ESCAPE) {
                    endDay();
                } else {
                    // 마지막 날에 도달하고 탈출하지 못했으면 게임 실패
                    endGame(false);
                    isGameRunning = false;
                }
            }
        }
    }

    /**
     * 플레이어 행동 처리
     * 
     * @param actionType 행동 유형
     * @return 처리 결과
     */
    private boolean handleAction(ActionType actionType) {
        if (actionController == null || gameState == null) {
            return false;
        }

        // 행동력이 부족한지 먼저 확인
        Player player = gameState.getPlayer();
        if (!player.hasAP(actionType.getApCost())) {
            view.showMessage("행동력이 부족합니다.");
            return false;
        }

        boolean result = actionController.performAction(gameState.getPlayer(), actionType);

        // 게임 종료 여부 체크 (특별 이벤트로 인한 종료)
        if (result) {
            if (actionType == ActionType.EXPLORE) {
                // 플레이어 체력 확인 (사망 체크)
                if (gameState.getPlayer().getHp() <= 0) {
                    // 사망 시 게임 종료 처리
                    endGame(false);
                    return true; // 사망했지만 행동은 수행된 것으로 처리
                }
                
                // 탐험 후 업적 체크 (자원 수집가 업적)
                if (achievementController != null) {
                    achievementController.checkAndUpdateAchievements(gameState);
                }
            } else if (actionType == ActionType.CRAFT) {
                // 뗏목 제작 성공 시 화면에 결과 표시 후 정상적으로 게임 종료
                endGame(true); // 승리로 게임 종료
                
                // 아래 값을 null로 설정하면 isGameEnded()에서 게임 종료로 인식
                gameState = null;
                return true;
            }
        }

        return result;
    }

    /**
     * 게임 종료 여부 확인
     * 
     * @return 게임 종료 여부
     */
    private boolean isGameEnded() {
        // gameState가 null이면 게임 종료
        if (gameState == null) {
            return true;
        }
        
        Player player = gameState.getPlayer();
        
        // 플레이어 사망 체크
        if (player.getHp() <= 0) {
            return true;
        }
        
        // 마지막 날짜 체크
        if (gameState.getDay() > Constants.DAYS_TO_ESCAPE) {
            return true;
        }
        
        // 기타 종료 조건 체크 가능
        
        return false;
    }

    /**
     * 게임 종료 처리
     * 
     * @param isSuccess 게임 성공 여부
     */
    private void endGame(boolean isSuccess) {
        // 업적 체크 (탈출 성공 또는 실패)
        if (achievementController != null) {
            achievementController.checkAndUpdateAchievements(isSuccess ? null : gameState);
        }
        
        // 게임 종료 상태 결정
        GameEndState endState;
        if (isSuccess) {
            endState = GameEndState.VICTORY;
        } else {
            // 실패 원인 파악 (시간 초과 또는 사망)
            if (gameState != null && gameState.getPlayer().getHp() > 0 && gameState.getDay() >= Constants.DAYS_TO_ESCAPE) {
                // 시간 초과로 인한 실패도 DEATH 상태로 처리
                endState = GameEndState.DEATH;
            } else {
                // 사망으로 인한 실패
                endState = GameEndState.DEATH;
            }
        }
        
        // 게임 결과 표시
        view.displayEnding(endState);
        
        // 획득한 업적 표시
        if (achievementController != null && achievementController.hasEarnedNewAchievements()) {
            achievementController.displayEarnedAchievements(view);
        }
        
        // 게임 상태 초기화
        gameState = null;
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
