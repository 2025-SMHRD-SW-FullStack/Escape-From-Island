package survival.controller.game;

import static survival.util.Constants.MAX_RESOURCE_AMOUNT;
import static survival.util.Constants.MIN_RESOURCE_AMOUNT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import survival.model.game.Event;
import survival.model.game.EventType;
import survival.model.game.GameEndState;
import survival.model.game.ResourceType;

/**
 * 랜덤 이벤트 및 자원을 생성하는 클래스
 */
public class RandomGenerator {
    // 필드
    private Random random;
    // 식인종, 배발견 하면 바로 게임종료
    private GameEndState endState; 

    /**
     * 기본 생성자
     */
    public RandomGenerator() {
        this.random = new Random();
    }
 
    public GameEndState getEndState() {
		return endState;
	}

	public void setEndState(GameEndState endState) {
		this.endState = endState;
	}


	/**
     * 랜덤 자원 유형 반환
     * 
     * @return 자원 유형
     */
    public ResourceType getRandomResourceType() {
        ResourceType[] types = ResourceType.values();
        int index = random.nextInt(types.length);
        return types[index];
    }

    // 랜덤으로 자원종류 선택, 선택된 종류가 몇 개 있는지 알려주는 메소드
    public ResourceType[] getRandomResources() {
        // 모든 자원종류의 enums 값들 중복 없이 고르기
        List<ResourceType> resourceList = new ArrayList<>(Arrays.asList(ResourceType.values()));
        Collections.shuffle(resourceList);
        int count = getRandomNumber(MIN_RESOURCE_AMOUNT, Math.min(MAX_RESOURCE_AMOUNT, resourceList.size())); // 최대값은 자원
                                                                                                              // 수 이하로
        return resourceList.subList(0, count).toArray(new ResourceType[0]);
    }

    /**
     * 랜덤 자원 이름 반환
     * 
     * @return 자원 이름
     */
    public ResourceType getRandomResource() {
        return getRandomResourceType();
    }

    /**
     * 이벤트 발생 여부 확인
     * 
     * @return 이벤트 타입 ( 자원 이벤트 or 특별 이벤트 )
     */
    public EventType getTriggeredEventType() {
        // return false; // 임시 반환값
    	double rd = random.nextDouble(); // 0.0 ~ 1.0
    	
    	if (rd < 0.3) {
    		// 이벤트 유형 : 특별 이벤트 30%
    		return EventType.SPECIAL;
    	} else {
    		// 이벤트 유형 :  자원 획득 이벤트 70%
    		return EventType.RESOURCE_GAIN;
    	}
    }
    
    /**
     * 자원 획득 이벤트 생성
     * 
     * @return 이벤트 객체
     */
    // 자원 타입과, 해당 자원의 갯수 매개변수로
    public Event generateResourceGainEvent(ResourceType resource, int amount) {
    	return new Event(
			EventType.RESOURCE_GAIN,
			resource.getLabel() + "를(을)" + amount + "개 획득했습니다!",
			player -> player.getInventory().addResource(resource.getLabel(), amount)
		);
    }

    /**
     * 특별 이벤트 랜덤 생성
     * 
     * @return 이벤트 객체
     */
    public Event generateSpecialRandomEvent() {
        // return null; // 임시 반환값
    	
    	// 이벤트 유형에 맞게 이벤트 발생 상황 나누기
    	// 자원 획득 이벤트일 때
    	// 식인종, 배발견 ->  0.5%
    	// 나머지 이벤트 -> 나머지 퍼센트 1/6로 나눠서 분배
    	double chance = random.nextDouble() * 1000; // 0.0 ~ 999.999
    	
    	// 식인종 이벤트
    	if (chance < 5) {
    		Event event = new Event(
    			EventType.SPECIAL,
    			"식인종에게 잡혀버렸습니다... 사망 x.x ",
    			player -> {} // 체력 조정 불필요
			);
    		event.setEndState(GameEndState.DEATH); // 게임 종료 상태 설정
    		return event;	
    		
    	} else if(chance < 10) {
    		// 배 발견
    		Event event = new Event(
    			EventType.SPECIAL,
    			"배를 발견했습니다! 탈출에 성공! >ㅁ<",
    			player -> {} 
			);
    		event.setEndState(GameEndState.VICTORY); // 게임 종료 상태 설정
    		return event;
    	} else {
    		// 나머지 6가지 : 990개 중 각 165씩 할당
    		double subChance = (chance - 10) / 165;
    		int randomEvent = (int) subChance; // 0~5로 구분
    		int hpChance = getRandomNumber(5, 10);
    		
    		switch (randomEvent) {
    		case 0:
    			return new Event(
					EventType.SPECIAL,
					"야생동물에게 공격당했습니다!",
					player -> player.updateHP(-hpChance)
				);
    		case 1:
    			return new Event(
					EventType.SPECIAL,
					"산사태가 발생했습니다!",
					player -> {
						player.updateHP(-hpChance);
						player.useAP(1);
					}
				);
    		case 2:
    			return new Event(
					EventType.SPECIAL,
					"폭염으로 지쳤습니다...",
					player -> {
						player.updateHP(-hpChance);
						player.useAP(1);
					}
				);
    		case 3:
    			return new Event(
					EventType.SPECIAL,
					"폭우로 인해 체력이 떨어졌습니다.",
					player -> {
						player.updateHP(-hpChance);
						player.useAP(1);
					}
				);
    		case 4:
    			return new Event(
					EventType.SPECIAL,
					"길을 잃어 에너지를 소비했습니다.",
					player -> player.useAP(1)
				);
    		case 5:
    			return new Event(
					EventType.SPECIAL,
					"산딸기를 발견했습니다!",
					player -> {
						player.updateHP(hpChance);
						player.addAP(1);
					}
				);
    		}
    	
    	}
    	throw new IllegalStateException("정의되지 않은 이벤트입니다.");
    }

    /**
     * 범위 내 랜덤 정수 생성
     * 
     * @param min 최소값
     * @param max 최대값
     * @return 랜덤 정수
     */
    public int getRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
        // min ~ max 까지의 랜덤 숫자 출력
        // return 0; // 임시 반환값
    }

    
}
