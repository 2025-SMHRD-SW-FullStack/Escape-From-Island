package survival.model.game;

/**
 * 게임 내 아이템을 관리하는 클래스
 */
public class Item {
    // 필드
    private String name;
    private ItemType type;
    private Recipe recipe;
    
    /**
     * 생성자
     * @param name 아이템 이름
     * @param type 아이템 타입
     * @param recipe 제작 레시피
     */
    public Item(String name, ItemType type, Recipe recipe) {
        this.name = name;
        this.type = type;
        this.recipe = recipe;
    }
    
    /**
     * 아이템 제작 가능 여부 확인
     * @param inventory 인벤토리
     * @return 제작 가능 여부
     */
    public boolean canCraft(Inventory inventory) {
        return recipe != null && recipe.canCraft(inventory);
    }
    
    /**
     * 아이템 이름 반환
     * @return 아이템 이름
     */
    public String getName() {
        return name;
    }
    
    /**
     * 아이템 타입 반환
     * @return 아이템 타입
     */
    public ItemType getType() {
        return type;
    }
    
    /**
     * 아이템 레시피 반환
     * @return 제작 레시피
     */
    public Recipe getRecipe() {
        return recipe;
    }
} 
