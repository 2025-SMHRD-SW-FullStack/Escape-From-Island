package survival.controller.game;

import survival.model.game.Player;
import survival.model.game.Inventory;
import survival.model.game.Item;
import survival.model.game.ItemType;
import survival.model.game.Recipe;
import survival.model.game.ResourceType;
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
    private GameView view;
    // 땟목 아이템
    private Item raft;

    /**
     * 생성자
     * 
     * @param view 게임 화면
     */
    public CraftingController(GameView view) {
        this.view = view;

        Map<ResourceType, Integer> resources = new HashMap<>();
        resources.put(ResourceType.WOOD, 5);
        resources.put(ResourceType.CLOTH, 3);
        resources.put(ResourceType.STONE, 2);

        raft = new Item("뗏목", ItemType.RAFT, new Recipe(resources));
    }

    /**
     * 아이템 제작 처리
     * 
     * @param player   플레이어
     * @return 제작 성공 여부
     */
    public void craftItem(Player player) {
        Inventory inventory = player.getInventory();
        
        // 제작에 필요한 자원 차감
        Recipe raftRecipe = raft.getRecipe();
        Map<ResourceType, Integer> requiredResources = raftRecipe.getResource();
        
        // 모든 자원 차감
        for (Map.Entry<ResourceType, Integer> entry : requiredResources.entrySet()) {
            ResourceType type = entry.getKey();
            int requiredAmount = entry.getValue();
            
            // 자원 제거
            inventory.removeResource(type, requiredAmount);
        }
        
        // 뗏목 아이템 추가
        inventory.addItem(raft);
    }

    /**
     * 제작 가능 여부 확인
     * 
     * @param player   플레이어
     * @param itemName 아이템 이름
     * @return 제작 가능 여부
     */
    public boolean canCraft(Player player) {
        Inventory inventory = player.getInventory();

        Recipe raftRecipe = raft.getRecipe();
        Map<ResourceType, Integer> requiredResources = raftRecipe.getResource();
        
        // 모든 자원이 충분한지 확인
        for (Map.Entry<ResourceType, Integer> entry : requiredResources.entrySet()) {
            ResourceType type = entry.getKey();
            int requiredAmount = entry.getValue();
            
            // 현재 보유 자원 확인
            int currentAmount = inventory.getResources().getOrDefault(type, 0);
            
            // 하나라도 부족하면 false 반환
            if (currentAmount < requiredAmount) {
                return false;
            }
        }
        
        // 모든 자원이 충족되면 true 반환
        return true;
    }

}
