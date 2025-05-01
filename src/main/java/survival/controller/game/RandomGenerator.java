package survival.controller.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import survival.model.game.Event;
import survival.model.game.EventType;
import survival.model.game.ResourceType;
import static survival.util.Constants.*;

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
     * @return 이벤트 발생 여부
     */
    public boolean isEventTriggered() {
        // return false; // 임시 반환값
        // EVENT_CHANCE : 0.3 으로 이벤트 발생 확률 설정
        return random.nextDouble() < EVENT_CHANCE;
    }

    /**
     * 랜덤 이벤트 생성
     * 
     * @return 이벤트 객체
     */
    public Event generateRandomEvent() {
        // return null; // 임시 반환값
        double rd = random.nextDouble(); // 0.0 ~ 1.0

        // 게임 이벤트 확률에 맞게 설정
        // 피해 이벤트 (40%)
        if (rd < DAMAGE_EVENT_CHANCE) {
            return new Event(
                    EventType.DAMAGE,
                    "폭우로 인해 체력을 잃었습니다.",
                    player -> player.updateHP(-10) // 체력 10 감소
            );
        }

        // 자원 획득 이벤트 (50%) → 누적 0.4 ~ 0.9
        // 자원 중 랜덤으로 하나 가져와서 획득량 1~3개 중 랜덤으로
        else if (rd < DAMAGE_EVENT_CHANCE + RESOURCE_EVENT_CHANCE) {
            // 랜덤 자원 1개
            ResourceType resource = getRandomResourceType();
            int amount = getRandomNumber(1, 3); // 1~3개 획득

            return new Event(
                    EventType.RESOURCE_GAIN,
                    "주변에서 유용한 자원을 발견했습니다!" + resource.getLabel() + "를" + amount + "개 추가로 획득했습니다!",
                    player -> player.getInventory().addResource(resource, amount));
        }
        // 회복 이벤트 (10%) → 나머지 (0.9 ~ 1.0)
        // special 이벤트는 일단 뺌
        else {
            return new Event(
                    EventType.HEAL,
                    "휴식을 취하며 체력을 회복했습니다.",
                    player -> player.updateHP(REST_HP_GAIN) // 체력 20 회복
            );
        }

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
