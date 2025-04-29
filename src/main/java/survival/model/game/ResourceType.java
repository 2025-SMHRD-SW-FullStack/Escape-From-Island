package survival.model.game;

/**
 * 게임 내 자원 유형 열거형
 */
public enum ResourceType {
    WOOD("나무"),
    STONE("돌"),
    FOOD("식량"),
    WATER("물"),
    CLOTH("천"),
    METAL("금속");
    
    private final String label;
    
    ResourceType(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    /**
     * 라벨에 해당하는 ResourceType 반환
     * @param label 자원 라벨
     * @return 해당 ResourceType, 없으면 null
     */
    public static ResourceType fromLabel(String label) {
        for (ResourceType type : values()) {
            if (type.getLabel().equals(label)) {
                return type;
            }
        }
        return null;
    }
} 