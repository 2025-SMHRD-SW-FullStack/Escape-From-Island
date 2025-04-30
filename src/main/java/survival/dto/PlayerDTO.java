package survival.dto;

/**
 * 플레이어 정보 전달용 DTO 클래스
 */
public class PlayerDTO {
    private int health;
    private int maxHealth;
    private int energy;
    private int maxEnergy;

    public PlayerDTO(int health, int maxHealth, int energy, int maxEnergy) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
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
}