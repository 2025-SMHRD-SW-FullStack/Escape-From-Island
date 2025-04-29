package survival.dto;

/**
 * 플레이어 정보 전달용 DTO 클래스
 */
public class PlayerDTO {
    private int health;
    private int maxHealth;
    private int energy;
    private int maxEnergy;
    private int hunger;
    private int maxHunger;
    
    public PlayerDTO(int health, int maxHealth, int energy, int maxEnergy, int hunger, int maxHunger) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.hunger = hunger;
        this.maxHunger = maxHunger;
    }
    
    // Getters
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getEnergy() {
        return energy;
    }
    
    public int getMaxEnergy() {
        return maxEnergy;
    }
    
    public int getHunger() {
        return hunger;
    }
    
    public int getMaxHunger() {
        return maxHunger;
    }
} 