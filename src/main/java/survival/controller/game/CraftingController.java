package survival.controller.game;

import survival.model.game.Player;
import survival.model.game.Inventory;
import survival.model.game.Item;
import survival.model.game.Recipe;
import survival.view.GameView;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * 아이템 제작 시스템을 관리하는 컨트롤러 클래스
 */
public class CraftingController {
    // 필드
    private Map<String, Item> availableItems;
    private GameView view;
    
    /**
     * 생성자
     * @param view 게임 화면
     */
    public CraftingController(GameView view) {
        this.view = view;
        this.availableItems = new HashMap<>();
        initializeItems();
    }
    
    /**
     * 사용 가능한 모든 아이템 목록 반환
     * @return 아이템 이름 목록
     */
    public List<String> getAvailableItems() {
        return new ArrayList<>(availableItems.keySet());
    }
    
    /**
     * 현재 인벤토리로 제작 가능한 아이템 목록 반환
     * @param inventory 인벤토리
     * @return 제작 가능한 아이템 이름 목록
     */
    public List<String> getCraftableItems(Inventory inventory) {
        return new ArrayList<>(); // 임시 반환값
    }
    
    /**
     * 아이템 제작 처리
     * @param player 플레이어
     * @param itemName 아이템 이름
     * @return 제작 성공 여부
     */
    public boolean craftItem(Player player, String itemName) {
        return false; // 임시 반환값
    }
    
    /**
     * 제작 가능 여부 확인
     * @param player 플레이어
     * @param itemName 아이템 이름
     * @return 제작 가능 여부
     */
    public boolean canCraft(Player player, String itemName) {
        return false; // 임시 반환값
    }
    
    /**
     * 아이템 레시피 반환
     * @param itemName 아이템 이름
     * @return 레시피
     */
    public Recipe getItemRecipe(String itemName) {
        return null; // 임시 반환값
    }
    
    /**
     * 아이템 목록 초기화
     */
    public void initializeItems() {
        // 메소드 구현 부분
    }
} 
