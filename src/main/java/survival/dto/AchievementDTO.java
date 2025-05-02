package survival.dto;

import survival.model.user.Achievement;

/**
 * 업적 정보 전송을 위한 DTO 클래스
 */
public class AchievementDTO {
    // 필드
    private int achievementId;
    private String title;
    private String description;
    private String condition;
    private String type;
    private boolean unlocked;

    /**
     * 기본 생성자
     */
    public AchievementDTO() {
        // 기본 생성자
    }

    /**
     * 모든 필드를 초기화하는 생성자
     * @param achievementId 업적 ID
     * @param title 업적 제목
     * @param description 업적 설명
     * @param condition 달성 조건
     * @param type 업적 타입
     * @param unlocked 해금 여부
     */
    public AchievementDTO(int achievementId, String title, String description, 
                          String condition, String type, boolean unlocked) {
        this.achievementId = achievementId;
        this.title = title;
        this.description = description;
        this.condition = condition;
        this.type = type;
        this.unlocked = unlocked;
    }

    /**
     * Achievement 모델 객체로부터 DTO 생성
     * @param achievement 업적 모델 객체
     * @param unlocked 해금 여부
     */
    public AchievementDTO(Achievement achievement, boolean unlocked) {
        if (achievement != null) {
            this.achievementId = achievement.getAchievementId();
            this.title = achievement.getTitle();
            this.description = achievement.getDescription();
            this.condition = achievement.getCondition();
            this.type = achievement.getType();
        }
        this.unlocked = unlocked;
    }

    // Getter/Setter 메서드
    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    /**
     * DTO를 모델 객체로 변환
     * @return Achievement 모델 객체
     */
    public Achievement toModel() {
        Achievement achievement = new Achievement();
        achievement.setAchievementId(this.achievementId);
        achievement.setTitle(this.title);
        achievement.setDescription(this.description);
        achievement.setCondition(this.condition);
        achievement.setType(this.type);
        return achievement;
    }
} 