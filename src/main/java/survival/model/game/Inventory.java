package survival.model.game;

import java.util.Map;
import java.util.HashMap;

/**
 * 플레이어의 인벤토리를 관리하는 클래스
 */
public class Inventory {
    // 필드
    private Map<String, Integer> resources; // 자원 저장
    private Map<String, Integer> items; // 아이템 저장

    /**
     * 기본 생성자
     */
    public Inventory() {
        resources = new HashMap<>();
        items = new HashMap<>();
    }

    /**
     * 자원 추가
     * 
     * @param name   자원 이름
     * @param amount 추가할 양
     */
    public void addResource(String name, int amount) {
        resources.put(name, resources.getOrDefault(name, 0) + amount);
    }

    /**
     * 자원 제거
     * 
     * @param name   자원 이름
     * @param amount 제거할 양
     * @return 제거 성공 여부
     */
    public boolean removeResource(String name, int amount) {
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
    public void addItem(String name) {
        items.put(name, items.getOrDefault(name, 0) + 1);
    }

    /**
     * 아이템 제거
     * 
     * @param name   아이템 이름
     * @param amount 제거할 양
     * @return 제거 성공 여부
     */
    public boolean removeItem(String name, int amount) {
        if (items.getOrDefault(name, 0) >= amount) {
            items.replace(name, items.get(name) - amount);
            return true;
        }
        return false;
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
     * 특정 아이템 보유 여부 확인
     * 
     * @param name 아이템 이름
     * @return 보유 여부
     */
    public boolean hasItem(String name) {
        return items.getOrDefault(name, 0) > 0;
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
     * 특정 아이템 개수 확인
     * 
     * @param name 아이템 이름
     * @return 보유 개수
     */
    public int getItemCount(String name) {
        return items.getOrDefault(name, 0);
    }

    /**
     * 모든 자원 반환
     * 
     * @return 자원 맵
     */
    public Map<String, Integer> getResources() {
        return this.resources;
    }

    /**
     * 모든 아이템 반환
     * 
     * @return 아이템 맵
     */
    public Map<String, Integer> getItems() {
        return this.items;
    }

    @Override
    public String toString() {
        return "Inventory";
    }
}
