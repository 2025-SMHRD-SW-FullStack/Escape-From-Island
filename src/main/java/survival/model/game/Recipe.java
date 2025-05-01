package survival.model.game;

import java.util.Map;

/**
 * 아이템 제작 레시피를 관리하는 클래스
 */
public class Recipe {
    // 필드
    private Map<ResourceType, Integer> resources; // 필요한 자원

    /**
     * 생성자
     * 
     * @param resources 필요한 자원 맵
     * @param items     필요한 아이템 맵
     */
    public Recipe(Map<ResourceType, Integer> resources) {
        this.resources = resources;
    }

    /**
     * 제작 가능 여부 확인
     * 
     * @param inventory 인벤토리
     * @return 제작 가능 여부
     */
    public boolean canCraft(Inventory inventory) {
        return resources.entrySet().stream()
                .filter(resource -> inventory.getResourceCount(resource.getKey().toString()) > resource.getValue())
                .findAny()
                .isPresent();
    }

    /**
     * 자원 요구사항 반환
     * 
     * @return 필요한 자원 맵
     */
    public Map<ResourceType, Integer> getResource() {
        return resources;
    }
}
