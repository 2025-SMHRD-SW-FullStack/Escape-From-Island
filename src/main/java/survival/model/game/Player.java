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
    
    /**
     * 기본 생성자
     */
    public Player() {
        // 생성자 구현 부분
    }
    
    /**
     * 행동력 사용
     * @param amount 사용할 행동력
     */
    public void useAP(int amount) {
        // 메소드 구현 부분
    }
    
    /**
     * 체력 업데이트
     * @param amount 변경할 체력량 (양수: 회복, 음수: 피해)
     */
    public void updateHP(int amount) {
        // 메소드 구현 부분
    }
    
    /**
     * 행동력 충분한지 확인
     * @param amount 확인할 행동력
     * @return 충분하면 true, 부족하면 false
     */
    public boolean hasAP(int amount) {
        return false; // 임시 반환값
    }
    
    /**
     * 플레이어 상태 초기화
     */
    public void reset() {
        // 메소드 구현 부분
    }
    
    /**
     * 보너스 행동력 추가
     * @param bonus 추가할 행동력
     */
    public void addBonusAP(int bonus) {
        // 메소드 구현 부분
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
