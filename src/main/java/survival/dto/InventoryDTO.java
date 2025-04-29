package survival.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 인벤토리 정보 전달용 DTO 클래스
 */
public class InventoryDTO {
    private Map<String, Integer> items;
    
    public InventoryDTO() {
        this.items = new HashMap<>();
    }
    
    /**
     * 아이템 맵을 기반으로 DTO 생성
     */
    public InventoryDTO(Map<String, Integer> items) {
        this.items = new HashMap<>(items);
    }
    
    /**
     * 아이템 추가
     */
    public void addItem(String itemName, int count) {
        items.put(itemName, items.getOrDefault(itemName, 0) + count);
    }
    
    /**
     * 아이템 목록 반환
     */
    public Map<String, Integer> getItems() {
        return new HashMap<>(items);
    }
    
    /**
     * 아이템 개수 반환
     */
    public int getItemCount(String itemName) {
        return items.getOrDefault(itemName, 0);
    }
    
    /**
     * 아이템 보유 여부 확인
     */
    public boolean hasItem(String itemName) {
        return items.containsKey(itemName) && items.get(itemName) > 0;
    }
} 