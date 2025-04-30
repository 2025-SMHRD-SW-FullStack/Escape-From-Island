package survival.model.game;

/**
 * 플레이어 상태와 인벤토리를 관리하는 클래스
 */
public class Player {
    // 필드
    private int hp;
    private int maxHp;
    private int ap;
    private Inventory inventory;

    
    public Player() {

    }

    public Player(int hp, int maxHp, int ap, Inventory inventory) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.ap = ap;
        this.inventory = inventory;
    }

    /**
     * 행동력 사용
     * 
     * @param amount 사용할 행동력
     */
    public boolean useAP(int amount) {
        if (ap >= amount) {
            ap -= amount;
            return true;
        }

        return false;
    }

    /**
     * 체력 업데이트
     * 
     * @param amount 변경할 체력량 (양수: 회복, 음수: 피해)
     */
    public void updateHP(int amount) {
        // 메소드 구현 부분
        hp += amount;
    }

    /**
     * 행동력 충분한지 확인
     * 
     * @param amount 확인할 행동력
     * @return 충분하면 true, 부족하면 false
     */
    public boolean hasAP(int amount) {
        return ap >= amount;
    }

    /**
     * 플레이어 상태 초기화
     */
    public void reset() {
        ap = 0;
        hp = maxHp;
        inventory = new Inventory();
    }

    /**
     * 보너스 행동력 추가
     * 
     * @param bonus 추가할 행동력
     */
    public void addBonusAP(int bonus) {
        ap += bonus;
    }

    // Getter/Setter 메소드
    public Inventory getInventory() {
        return inventory;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }
}
