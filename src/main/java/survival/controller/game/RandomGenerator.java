package survival.controller.game;

import survival.model.game.Event;
import survival.model.game.EventType;
import survival.model.game.ResourceType;

import java.util.Random;

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
        return 0; // 임시 반환값
    }
} 
