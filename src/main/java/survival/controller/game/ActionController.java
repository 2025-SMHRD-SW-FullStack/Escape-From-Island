package survival.controller.game;

import static survival.util.Constants.REST_HP_GAIN;

import survival.model.game.ActionType;
import survival.model.game.Event;
import survival.model.game.EventType;
import survival.model.game.GameEndState;
import survival.model.game.Player;
import survival.model.game.ResourceType;
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
     * 
     * @param view 게임 화면
     */
    public ActionController(GameView view) {
        this.view = view;
        this.random = new RandomGenerator();
        this.craftingController = new CraftingController(view);
        // 추가 초기화 코드
    }

    /**
     * 플레이어가 뗏목을 제작할 수 있는지 확인
     * 
     * @param player 플레이어
     * @return 뗏목 제작 가능 여부
     */
    public boolean canCraftRaft(Player player) {
        return craftingController.canCraft(player);
    }

    /**
     * 행동 수행
     * 
     * @param player     플레이어
     * @param actionType 행동 유형
     * @param itemName   제작할 아이템 이름 (제작 행동인 경우)
     * @return 행동 결과 상태 (성공 : true, 실패 : false)
     */
    public boolean performAction(Player player, ActionType actionType) {
        // 행동력 확인
        if (!player.hasAP(actionType.getApCost())) {
            return false; // 행동력 부족
        }

        // 행동 실행
        boolean success = false;
        switch (actionType) {
            case EXPLORE:
                explore(player);
                success = true;
                break;
            case CRAFT:
                success = craft(player);
                break;
            case REST:
                rest(player);
                success = true;
                break;
            default:
                return false;
        }
        
        // 행동이 성공했을 때만 행동력 소모
        if (success) {
            player.useAP(actionType.getApCost());
        }
        
        return success;
    }

    /**
     * 탐색 행동 처리
     * 
     * @param player 플레이어
     */
    public void explore(Player player) {
        // 메소드 구현 부분

        // 이벤트 타입 가져오기
        EventType eventType = random.getTriggeredEventType();

        // 자원 획득 이벤트일 때
        if (eventType == EventType.RESOURCE_GAIN) {
            // 랜덤한 자원 배열 받아오기
            ResourceType[] findResources = random.getRandomResources();

            // 발견한 자원이 없다면 텍스트 출력
            if (findResources.length == 0) {
                view.showMessage("탐험 결과 : 아무 자원도 발견하지 못했습니다.");
                return;
            }

            // 발견한 자원들이 있다면 플레이어에게 자원 목록 보여주기
            view.showMessage("탐험 결과 : 다음 자원을 발견했습니다. \n 하나를 선택하세요 : ");
            for (int i = 0; i < findResources.length; i++) {
                view.showMessage((i + 1) + ". " + findResources[i].getLabel());
            }
            // 그 중 플레이어가 하나의 자원을 선택 (입력 받기)
            int choice = view.getIntInput(1, findResources.length);
            ResourceType selectedResource = findResources[choice - 1];

            // 이벤트 생성 (자원 무조건 1개만 줌)
            Event event = random.generateResourceGainEvent(selectedResource, 1);

            // 이벤트 실행 및 메시지 출력
            event.execute(player);
            view.showMessage(event.getDescription());
        } else if (eventType == EventType.SPECIAL) {
            Event event = random.generateSpecialRandomEvent();
            event.execute(player);

            view.showMessage("[이벤트 발생!] " + event.getDescription());

            if (event.getEndState() != null) {
                GameEndState endState = event.getEndState();

                switch (endState) {
                    case VICTORY:
                        endGame(true);
                        break;
                    case DEATH:
                        endGame(false);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid end state: " + endState);
                }
            }
        }
    }

    /**
     * 아이템 제작 처리
     * 
     * @param player   플레이어
     * @param itemName 아이템 이름
     * @return 제작 성공 여부
     */
    public boolean craft(Player player) {
        if (!craftingController.canCraft(player)) {
            view.showMessage("현재 땟목을 제작할 수 없습니다.");
            return false;
        }

        craftingController.craftItem(player);
        view.showMessage("땟목을 제작하였습니다!");
        view.showMessage("땟목을 타고 섬을 탈출하는데 성공했습니다!");
        
        // 즉시 승리 처리
        endGame(true);
        
        return true;
    }

    /**
     * 휴식 행동 처리
     * 
     * @param player 플레이어
     */
    public void rest(Player player) {
        // 메소드 구현 부분
        // 체력 회복 player의 updateHP() 메소드로 체력 업데이트
        // 체력 회복 메시지 출력 view.showMessage() 메소드
        player.updateHP(REST_HP_GAIN);
        view.showMessage("체력을 " + REST_HP_GAIN + " 회복하였습니다.");
    }

    /**
     * 랜덤 이벤트 생성
     * 
     * @return 이벤트 객체
     */
    public Event getRandomEvent() {
        // return null; // 임시 반환값
        return random.generateSpecialRandomEvent();
    }

    /**
     * 게임 종료 처리
     * 
     * @param isEscaped 탈출 여부
     */
    public void endGame(boolean isEscaped) {
        // view에 직접 표시하는 방식은 사용하지 않음
        // GameController에서 endGame이 호출되어 처리되도록 함
    }
}
