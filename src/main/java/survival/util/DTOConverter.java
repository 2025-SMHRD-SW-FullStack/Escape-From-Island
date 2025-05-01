package survival.util;

import survival.dto.GameEndDTO;
import survival.dto.InventoryDTO;
import survival.dto.PlayerDTO;
import survival.model.game.GameEndState;
import survival.model.game.Inventory;
import survival.model.game.Player;
import survival.model.game.ResourceType;

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
                Constants.INITIAL_AP);
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