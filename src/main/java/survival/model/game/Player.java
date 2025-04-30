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
     * 행동력 사용 (양수: 소모, 음수: 충당)
     * 
     * @param amount 사용할 행동력
     * @return 사용 성공 여부
     */
    public boolean useAP(int amount) {
        int tempAp = ap - amount;

        if (tempAp > 0) {
            ap = tempAp;
            return true;
        }
        return false;
    }

    /**
     * 체력 업데이트
     * 
     * @param amount 변경할 체력량 (양수: 회복, 음수: 피해)
     * @return 체력 변경 성공 여부
     */
    public boolean healHP(int amount) {
        int tempHp = hp + amount;

        if (tempHp > maxHp) {
            hp = maxHp;
            return true;
        }

        hp = tempHp;
        return false;
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
     * 행동력 추가
     * 
     * @param amount 추가할 행동력
     */
    public void addAP(int amount) {
        ap += amount;
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

    public boolean hasAP(int apCost) {
        return ap >= apCost;
    }
}
