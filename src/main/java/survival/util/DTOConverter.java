package survival.util;

import survival.dto.GameEndDTO;
import survival.dto.InventoryDTO;
import survival.dto.PlayerDTO;
import survival.model.game.GameEndState;
import survival.model.game.Inventory;
import survival.model.game.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * 모델 객체를 DTO로 변환하는 유틸리티 클래스
 */
public class DTOConverter {
    
    /**
     * Player 모델을 PlayerDTO로 변환
     */
    public static PlayerDTO convertToDTO(Player player) {
        if (player == null) {
            return null;
        }
        
        return new PlayerDTO(
            player.getHp(),
            player.getMaxHp(),
            player.getAp(),
            100, // 최대 AP 값은 예시로 100 사용
            0,   // 배고픔 시스템이 없으면 기본값 0 사용
            100  // 최대 배고픔 시스템이 없으면 기본값 100 사용
        );
    }
    
    /**
     * Inventory 모델을 InventoryDTO로 변환
     */
    public static InventoryDTO convertToDTO(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        
        Map<String, Integer> itemMap = new HashMap<>();
        
        // inventory의 아이템 맵을 가져와서 DTO에 복사
        // 실제 구현에서는 Inventory 모델의 getItemMap() 같은 메서드가 필요
        
        return new InventoryDTO(itemMap);
    }
    
    /**
     * GameEndState를 GameEndDTO로 변환
     */
    public static GameEndDTO convertToDTO(GameEndState endState) {
        if (endState == null) {
            return null;
        }
        
        boolean victory = (endState == GameEndState.VICTORY);
        String message = endState.getMessage();
        
        return new GameEndDTO(victory, message);
    }
} 