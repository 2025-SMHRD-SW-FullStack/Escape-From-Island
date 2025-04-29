package survival.controller.game;

import survival.model.game.Player;
import survival.model.game.Event;
import survival.model.game.ActionType;
import survival.view.GameView;

/**
 * 플레이어 행동을 처리하는 컨트롤러 클래스
 */
public class ActionController {
    // 필드
    private RandomGenerator random;
    private CraftingController craftingController;
    private GameView view;
    
    /**
     * 생성자
     * @param view 게임 화면
     */
    public ActionController(GameView view) {
        this.view = view;
        this.random = new RandomGenerator();
        // 추가 초기화 코드
    }
    
    /**
     * 행동 수행 
     * @param player 플레이어
     * @param actionType 행동 유형
     * @param itemName 제작할 아이템 이름 (제작 행동인 경우)
     * @return 행동 결과 상태 (성공 : true, 실패 : false)
     */
    public boolean performAction(Player player, ActionType actionType, String itemName) {
        // 행동력 확인
        if (!player.hasAP(actionType.getApCost())) {
            return false; // 행동력 부족
        }
        
        // 행동력 소모
        player.useAP(actionType.getApCost());
        
        // 행동 실행
        switch (actionType) {
            case EXPLORE:
                explore(player);
                return true;
            case CRAFT:
                return craft(player, itemName);
            case REST:
                rest(player);
                return true;
            default:
                return false;
        }
    }
    
    /**
     * 탐색 행동 처리
     * @param player 플레이어
     */
    public void explore(Player player) {
        // 메소드 구현 부분
    }
    
    /**
     * 아이템 제작 처리
     * @param player 플레이어
     * @param itemName 아이템 이름
     * @return 제작 성공 여부
     */
    public boolean craft(Player player, String itemName) {
        return false; // 임시 반환값
    }
    
    /**
     * 휴식 행동 처리
     * @param player 플레이어
     */
    public void rest(Player player) {
        // 메소드 구현 부분
    }
    
    /**
     * 랜덤 이벤트 생성
     * @return 이벤트 객체
     */
    public Event getRandomEvent() {
        return null; // 임시 반환값
    }
} 
