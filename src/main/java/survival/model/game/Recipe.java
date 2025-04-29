package survival.model.game;

import java.util.Map;

/**
 * 아이템 제작 레시피를 관리하는 클래스
 */
public class Recipe {
    // 필드
    private Map<String, Integer> resources; // 필요한 자원
    private Map<String, Integer> items;     // 필요한 아이템
    
    /**
     * 생성자
     * @param resources 필요한 자원 맵
     * @param items 필요한 아이템 맵
     */
    public Recipe(Map<String, Integer> resources, Map<String, Integer> items) {
        this.resources = resources;
        this.items = items;
    }
    
    /**
     * 제작 가능 여부 확인
     * @param inventory 인벤토리
     * @return 제작 가능 여부
     */
    public boolean canCraft(Inventory inventory) {
        return false; // 임시 반환값
    }
    
    /**
     * 자원 소비
     * @param inventory 인벤토리
     */
    public void consumeResources(Inventory inventory) {
        // 메소드 구현 부분
    }
    
    /**
     * 자원 요구사항 반환
     * @return 필요한 자원 맵
     */
    public Map<String, Integer> getResourceRequirements() {
        return resources;
    }
    
    /**
     * 아이템 요구사항 반환
     * @return 필요한 아이템 맵
     */
    public Map<String, Integer> getItemRequirements() {
        return items;
    }
} 
