package survival.controller.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import survival.model.game.Event;
import survival.model.game.ResourceType;

/**
 * 랜덤 이벤트 및 자원을 생성하는 클래스
 */
public class RandomGenerator {
    // 필드
    private Random random;
    
    /**
     * 기본 생성자
     */
    public RandomGenerator() {
        this.random = new Random();
    }
    
    /**
     * 랜덤 자원 유형 반환
     * @return 자원 유형
     */
    public ResourceType getRandomResourceType() {
        ResourceType[] types = ResourceType.values();
        int index = random.nextInt(types.length);
        return types[index];
    }
    
    // 랜덤으로 자원종류 선택, 선택된 종류가 몇 개 있는지 알려주는 메소드
    public ResourceType[] getRandomResources() {
//    	ResourceType[] resourceArray = new ResourceType[getRandomNumber(1, 5)];
//    	String type = resource.getLabel();
//    	return Arrays.stream(resourceArray)
//    		.map(r -> ResourceType.type)
//    		.toArray(ResourceType[]::new);
    	
    	// 모든 자원종류의 enums 값들 중복 없이 고르기
    	List<ResourceType> resourceList = new ArrayList<>(Arrays.asList(ResourceType.values()));
    	Collections.shuffle(resourceList);
    	int count = getRandomNumber(1, Math.min(5, resourceList.size())); // 최대값은 자원 수 이하로
    	return resourceList.subList(0, count).toArray(new ResourceType[0]);
    }
    
    /**
     * 랜덤 자원 이름 반환
     * @return 자원 이름
     */
    public String getRandomResource() {
        return getRandomResourceType().getLabel();
    }
    
    /**
     * 이벤트 발생 여부 확인
     * @return 이벤트 발생 여부
     */
    public boolean isEventTriggered() {
        return false; // 임시 반환값
    }
    
    /**
     * 랜덤 이벤트 생성
     * @return 이벤트 객체
     */
    public Event generateRandomEvent() {
        return null; // 임시 반환값
    }
    
    /**
     * 범위 내 랜덤 정수 생성
     * @param min 최소값
     * @param max 최대값
     * @return 랜덤 정수
     */
    public int getRandomNumber(int min, int max) {
    	return random.nextInt(max-min+1) + min;
        // min ~ max 까지의 랜덤 숫자 출력
    	// return 0; // 임시 반환값
    }
} 
