package survival.controller.game;

import survival.dto.GameEndDTO;
import survival.dto.PlayerDTO;
import survival.model.game.GameState;
import survival.model.game.Inventory;
import survival.model.game.Player;
import survival.model.user.User;
import survival.model.game.ActionType;
import survival.model.game.GameEndState;
import survival.controller.user.AuthController;
import survival.controller.user.AchievementController;
import survival.util.Constants;
import survival.util.DTOConverter;
import survival.view.GameView;
import static survival.util.Constants.*;

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
        Player player = new Player(Constants.INITIAL_HP, Constants.INITIAL_HP, Constants.INITIAL_AP, new Inventory());

        gameState = new GameState(Constants.INITIAL_DAY, player, false, false, null);
        processGame();
    }

    /**
     * 하루 진행 처리
     */
    public void processDay() {
        // 메소드 구현 부분
        // 하루 동안 발생하는 게임의 핵심 로직
        // 자원 변화, 상태 변화 등

        // 오늘이 몇 일차인지 출력
        view.displayDay(gameState.getDay());
        view.showMessage("일차");

        // 플레이어 상태 표시
        PlayerDTO playerDTO = DTOConverter.convertToDTO(gameState.getPlayer());
        view.displayPlayerStatus(playerDTO);

        // 하루에 한 번만 행동 처리
        processGame();

        // 하루 종료 처리 호출
        endDay();
    }

    /**
     * 하루 종료 처리
     */
    public void endDay() {
        // 메소드 구현 부분

        if (checkVictory()) {
            // 종료 메시지 전달
            GameEndDTO endDTO = new GameEndDTO(
                    gameState.isVictory(),
                    gameState.getEndState().getMessage());
            view.displayEnding(endDTO);
            return;
        }

        // 다음 날로 넘어가기
        gameState.nextDay(); // 일차 +1 , 여기에 AP도 자동으로 추가됨
        view.showMessage("다음 날로 넘어갑니다. 행동력이 회복되었습니다!");

        // 다음 날 진행
        processDay();
    }

    /**
     * 승리 조건 확인
     * 
     * @return 승리 여부
     */
    public boolean checkVictory() {
        // return false; // 임시 반환값
        if (gameState == null)
            return false;

        // 현재 날짜와 플레이어의 체력 가져와서 종료, 승리 조건 체크
        int currentDay = gameState.getDay();
        int currentHP = gameState.getPlayer().getHp();

        // 게임 종료 조건 확인
        if (currentDay >= DAYS_TO_ESCAPE) {
            if (currentHP <= GAME_OVER_HP) {
                // 체력이 0 이하 -> 사망
                gameState.endGame(GameEndState.DEATH);
            } else if (gameState.getPlayer().getInventory().hasItem(RAFT_ITEM_NAME)) {
                // 뗏목 아이템 보유 -> 탈출 성공
                gameState.endGame(GameEndState.VICTORY);
            } else {
                // 강제 종료
                gameState.endGame(GameEndState.GIVE_UP);
            }

            return true; // 종료

        }
        return false;
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
    	if(membership) {
        return true;
    	}
    	return false;
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
        view.showMessageNoln("살아남아야 한다. 살아남기 위해 뭘 해야 할까? ");
        
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
     * 
     * @param actionType 행동 유형
     * @param itemName   아이템 이름 (제작 시)
     * @return 처리 결과
     */
    private boolean handleAction(ActionType actionType, String itemName) {
        if (actionController == null || gameState == null) {
            return false;
        }

        boolean result = actionController.performAction(gameState.getPlayer(), actionType, itemName);

        // 행동 결과에 따른 메시지 표시
        // if (result) {
        // switch(actionType) {
        // case EXPLORE:
        // view.showMessage("탐험을 완료했습니다.");
        // break;
        // case CRAFT:
        // view.showMessage("아이템 제작을 완료했습니다.");
        // break;
        // case REST:
        // view.showMessage("휴식을 취했습니다. 체력이 회복되었습니다.");
        // break;
        // }
        // }

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
