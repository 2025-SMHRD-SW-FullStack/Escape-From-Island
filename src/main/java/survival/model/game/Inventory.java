package survival.model.game;

import java.util.Map;
import java.util.HashMap;

/**
 * 플레이어의 인벤토리를 관리하는 클래스
 */
public class Inventory {
    // 필드
    private Map<String, Integer> resources; // 자원 저장
    private Map<String, Integer> items;     // 아이템 저장
    
    /**
     * 기본 생성자
     */
    public Inventory() {
        resources = new HashMap<>();
        items = new HashMap<>();
    }
    
    /**
     * 자원 추가
     * @param name 자원 이름
     * @param amount 추가할 양
     */
    public void addResource(String name, int amount) {
        // 메소드 구현 부분
    }
    
    /**
     * 자원 제거
     * @param name 자원 이름
     * @param amount 제거할 양
     * @return 제거 성공 여부
     */
    public boolean removeResource(String name, int amount) {
        return false; // 임시 반환값
    }
    
    /**
     * 아이템 추가
     * @param name 아이템 이름
     */
    public void addItem(String name) {
        // 메소드 구현 부분
    }
    
    /**
     * 아이템 제거
     * @param name 아이템 이름
     * @param amount 제거할 양
     * @return 제거 성공 여부
     */
    public boolean removeItem(String name, int amount) {
        return false; // 임시 반환값
    }
    
    /**
     * 특정 자원 보유 여부 확인
     * @param name 자원 이름
     * @return 보유 여부
     */
    public boolean hasResource(String name) {
        return false; // 임시 반환값
    }
    
    /**
     * 여러 자원 보유 여부 확인
     * @param required 필요한 자원 맵
     * @return 모든 자원 보유 여부
     */
    public boolean hasResources(Map<String, Integer> required) {
        return false; // 임시 반환값
    }
    
    /**
     * 특정 아이템 보유 여부 확인
     * @param name 아이템 이름
     * @return 보유 여부
     */
    public boolean hasItem(String name) {
        return false; // 임시 반환값
    }
    
    /**
     * 특정 자원 개수 확인
     * @param name 자원 이름
     * @return 보유 개수
     */
    public int getResourceCount(String name) {
        return 0; // 임시 반환값
    }
    
    /**
     * 특정 아이템 개수 확인
     * @param name 아이템 이름
     * @return 보유 개수
     */
    public int getItemCount(String name) {
        return 0; // 임시 반환값
    }
    
    /**
     * 모든 자원 반환
     * @return 자원 맵
     */
    public Map<String, Integer> getResources() {
        return resources;
    }
    
    /**
     * 모든 아이템 반환
     * @return 아이템 맵
     */
    public Map<String, Integer> getItems() {
        return items;
    }
    
    @Override
    public String toString() {
        return "Inventory"; // 임시 구현
    }
} 
