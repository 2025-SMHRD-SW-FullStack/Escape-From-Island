package survival.model.game;

import survival.util.Constants;

/**
 * 플레이어 행동 유형 열거형
 */
public enum ActionType {
    EXPLORE(Constants.EXPLORE_AP_COST, "탐험"),
    CRAFT(Constants.CRAFT_AP_COST, "제작"),
    REST(Constants.REST_AP_COST, "휴식");
    
    private final int apCost;
    private final String label;
    
    ActionType(int apCost, String label) {
        this.apCost = apCost;
        this.label = label;
    }
    
    public int getApCost() {
        return apCost;
    }
    
    public String getLabel() {
        return label;
    }
} 