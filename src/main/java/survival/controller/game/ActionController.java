package survival.controller.game;

import survival.model.game.Player;
import survival.model.game.ResourceType;
import survival.model.game.Event;

import java.util.Scanner;

import survival.model.game.ActionType;
import survival.view.GameView;
import static survival.util.Constants.*;

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
    public boolean performAction(Player player, ActionType actionType) {
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
                return craft(player);
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
        // 플레이어의 자원 획득
    	// Player 객체를 통해 자원을 인벤토리에 추가(저장)해야 함!
    	// Player.inventory.addResource (?) <- 자원 추가 메소드
    	// 아직 코드 구현 안함
    	
    	// 이벤트 발생 (이벤트 발생 여부 확인 메소드 isEventTriggered())
    	// Yes -> 랜덤 이벤트 처리
    	if(random.isEventTriggered()) {
    		Event event = getRandomEvent();
    		
    		// 이벤트 효과 실행
    		event.execute(player);
    		
    		// 이벤트 설명 출력
    		view.showMessage("[이벤트 발생!] " + event.getDescription());
    	} else {	
    		// No -> 자원 획득 메시지 출력
    		view.showMessage(selectedResource.getLabel() + "를(을)" + amount + "개 획득했습니다!");
    		
    	}

    	// 자원 획득 갯수가 1~3개 중 몇 개 나올지 정하는 변수
    	
    	// 자원 선택 갯수에 맞게 자원 타입 랜덤하게 생성 (반복문)
    	// 자원 타입을 랜덤으로 생성하고, 자원 타입 선택
    	// 자원 종류 결정 -> 획득량 결정 -> 자원 획득
    	// 이벤트 발생 -> Yes 랜덤 이벤트 처리
    	// 			-> No 자원 획득 메시지
    	
    	// 랜덤한 자원 배열 받아오기
    	ResourceType[] findResources = random.getRandomResources();
    	
    	// 발견한 자원이 없다면 텍스트 출력
    	if(findResources.length == 0) {
    		view.showMessage("탐험 결과 : 아무 자원도 발견하지 못했습니다.");
    		return;
    	}
    	
    	// 발견한 자원들이 있다면 플레이어에게 자원 목록 보여주기
    	view.showMessage("탐험 결과 : 다음 자원을 발견했습니다. \n 하나를 선택하세요 : ");
    	for(int i = 0; i < findResources.length; i++) {
    		view.showMessage((i+1) + ". " + findResources[i].getLabel());
    	}
    	// 그 중 플레이어가 하나의 자원을 선택 (입력 받기)
    	int choice = view.getIntInput(1, findResources.length);
    	ResourceType selectedResource = findResources[choice-1];
    	
    	// 선택한 자원의 획득량 결정 (예: 1~3 랜덤)
    	int amount = random.getRandomNumber(1, 3);	   	
    }
    
    /**
     * 아이템 제작 처리
     * @param player 플레이어
     * @param itemName 아이템 이름
     * @return 제작 성공 여부
     */
    public boolean craft(Player player) {
        if(!craftingController.canCraft(player)) {
            view.showMessage("현재 땟목을 제작할 수 없습니다.");
            return false;
        }

        craftingController.craftItem(player);
        view.showMessage("땟목을 제작하였습니다.");
        
    	return true;
    }
    
    /**
     * 휴식 행동 처리
     * @param player 플레이어
     */
    public void rest(Player player) {
        // 메소드 구현 부분
    	// 체력 회복 player의 updateHP() 메소드로 체력 업데이트
    	// 체력 회복 메시지 출력 view.showMessage() 메소드
    	player.updateHP(REST_HP_GAIN);
    	view.showMessage("체력을 " + REST_HP_GAIN +  " 회복하였습니다.");
    }
    
    /**
     * 랜덤 이벤트 생성
     * @return 이벤트 객체
     */
    public Event getRandomEvent() {
        // return null; // 임시 반환값
    	return random.generateRandomEvent();
    }
} 
