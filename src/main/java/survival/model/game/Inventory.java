package survival.model.game;

import java.util.Map;
import java.util.HashMap;

/**
 * 플레이어의 인벤토리를 관리하는 클래스
 */
public class Inventory {
    // 필드
    private Map<ResourceType, Integer> resources; // 자원 저장
    private Item raft; // 아이템 저장

    /**
     * 기본 생성자
     */
    public Inventory() {
        resources = new HashMap<>();
    }

    /**
     * 자원 추가
     * 
     * @param name   자원 이름
     * @param amount 추가할 양
     */
    public void addResource(ResourceType name, int amount) {
        resources.put(name, resources.getOrDefault(name, 0) + amount);
    }

    /**
     * 자원 제거
     * 
     * @param name   자원 이름
     * @param amount 제거할 양
     * @return 제거 성공 여부
     */
    public boolean removeResource(ResourceType name, int amount) {
        if (resources.getOrDefault(name, 0) >= amount) {
            resources.replace(name, resources.get(name) - amount);
            return true;
        }
        return false;
    }

    /**
     * 아이템 추가
     * 
     * @param name 아이템 이름
     */
    public void addItem(Item item) {
        raft = item;
    }

    /**
     * 특정 자원 보유 여부 확인
     * 
     * @param name 자원 이름
     * @return 보유 여부
     */
    public boolean hasResource(String name) {
        return resources.getOrDefault(name, 0) > 0;
    }

    /**
     * 여러 자원 보유 여부 확인
     * 
     * @param required 필요한 자원 맵
     * @return 모든 자원 보유 여부
     */
    public boolean hasResources(Map<String, Integer> required) {
        for (Map.Entry<String, Integer> entry : required.entrySet()) {
            String resourceName = entry.getKey();
            int requiredAmount = entry.getValue(); // 필요한 자원 양

            if (resources.getOrDefault(resourceName, 0) < requiredAmount) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 특정 자원 개수 확인
     * 
     * @param name 자원 이름
     * @return 보유 개수
     */
    public int getResourceCount(String name) {
        return resources.getOrDefault(name, 0);
    }

    /**
     * 모든 자원 반환
     * 
     * @return 자원 맵
     */
    public Map<ResourceType, Integer> getResources() {
        return this.resources;
    }
    
    /**
     * 모든 자원 유형 보유 여부 확인
     * 
     * @return 모든 자원 유형 보유 여부
     */
    public boolean hasAllResourceTypes() {
        // 모든 ResourceType 열거형 값 확인
        for (ResourceType type : ResourceType.values()) {
            // 해당 자원을 보유하고 있지 않으면 false 반환
            if (resources.getOrDefault(type, 0) <= 0) {
                return false;
            }
        }
        // 모든 자원 유형을 보유하고 있으면 true 반환
        return true;
    }
}
