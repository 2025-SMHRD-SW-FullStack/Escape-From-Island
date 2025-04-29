package survival.model.user;

/**
 * 업적 정보를 관리하는 클래스
 */
public class Achievement {
    // 필드
    private int achievementId;
    private String title;
    private String description;
    private String condition;
    private String type;
    
    /**
     * 기본 생성자
     */
    public Achievement() {
        // 생성자 구현 부분
    }
    
    /**
     * 모든 필드를 초기화하는 생성자
     * @param id 업적 ID
     * @param title 업적 제목
     * @param desc 업적 설명
     * @param cond 달성 조건
     * @param type 업적 타입
     */
    public Achievement(int id, String title, String desc, String cond, String type) {
        this.achievementId = id;
        this.title = title;
        this.description = desc;
        this.condition = cond;
        this.type = type;
    }
    
    // Getter/Setter 메소드
    public int getAchievementId() {
        return achievementId;
    }
    
    public void setAchievementId(int id) {
        this.achievementId = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String desc) {
        this.description = desc;
    }
    
    public String getCondition() {
        return condition;
    }
    
    public void setCondition(String cond) {
        this.condition = cond;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
} 
