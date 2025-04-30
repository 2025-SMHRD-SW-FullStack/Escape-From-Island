package survival.controller.game;

import survival.model.game.Player;
import survival.model.game.ResourceType;
import survival.model.game.Event;

import java.util.Scanner;

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
    	// 자원 획득 갯수가 1~3개 중 몇 개 나올지 정하는 변수
    	
    	// 자원 선택 갯수에 맞게 자원 타입 랜덤하게 생성 (반복문)
    	// 자원 타입을 랜덤으로 생성하고, 자원 타입 선택
    	// 자원 종류 결정 -> 획득량 결정 -> 자원 획득
    	// 이벤트 발생 -> Yes 랜덤 이벤트 처리
    	// 			-> No 자원 획득 메시지
    	
    	// 랜덤한 자원 배열 받아오기
    	ResourceType[] resources = random.getRandomResources();
    	
    	// 발견한 자원이 없다면 텍스트 출력
    	if(resources.length == 0) {
    		view.showMessage("탐험 결과 : 아무 자원도 발견하지 못했습니다.");
    		return;
    	}
    	
    	// 발견한 자원들이 있다면 사용자에게 자원 목록 보여주기
    	view.showMessage("다음 자원 중 하나를 선택하세요 : ");
    	for(int i = 0; i < resources.length; i++) {
    		view.showMessage((i+1) + ". " + resources[i].getLabel());
    	}
    	// 그 중 사용자가 하나를 선택 (입력 받기)
    	
    	
    }
    
    /**
     * 아이템 제작 처리
     * @param player 플레이어
     * @param itemName 아이템 이름
     * @return 제작 성공 여부
     */
    public boolean craft(Player player, String itemName) {
        // CraftingController 객체에서 현재 인벤토리로 제작
    	// 가능한 아이템 목록을 반환하기 getCraftableItems()
    	// 아이템 선택 : 아이템 제작 처리인 craftItem() 메소드 호출

    	return false; // 임시 반환값
    }
    
    /**
     * 휴식 행동 처리
     * @param player 플레이어
     */
    public void rest(Player player) {
        // 메소드 구현 부분
    	// 체력 회복 player의 updateHP() 메소드로 체력 업데이트
    	// 체력 회복 메시지 출력 view.showMessage() 메소드
    }
    
    /**
     * 랜덤 이벤트 생성
     * @return 이벤트 객체
     */
    public Event getRandomEvent() {
        return null; // 임시 반환값
    }
} 
