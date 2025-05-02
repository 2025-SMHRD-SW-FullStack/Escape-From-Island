package survival.dto;

import survival.model.user.User;

/**
 * 사용자 정보 전송을 위한 DTO 클래스
 */
public class UserDTO {
    // 필드
    private int userId;
    private String username;
    private String password;  // 비밀번호는 DTO에만 포함 (회원가입/로그인 시 필요)
    private int achievementCount;  // 업적 획득 개수 (선택적 정보)

    /**
     * 기본 생성자
     */
    public UserDTO() {
        // 기본 생성자
    }

    /**
     * 모든 필드를 초기화하는 생성자
     * @param userId 사용자 ID
     * @param username 사용자명
     * @param password 비밀번호
     * @param achievementCount 업적 획득 개수
     */
    public UserDTO(int userId, String username, String password, int achievementCount) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.achievementCount = achievementCount;
    }

    /**
     * 로그인/회원가입용 생성자
     * @param username 사용자명
     * @param password 비밀번호
     */
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * User 모델 객체로부터 DTO 생성
     * @param user 사용자 모델 객체
     */
    public UserDTO(User user) {
        if (user != null) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
        }
    }

    // Getter/Setter 메서드
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAchievementCount() {
        return achievementCount;
    }

    public void setAchievementCount(int achievementCount) {
        this.achievementCount = achievementCount;
    }

    /**
     * DTO를 모델 객체로 변환
     * @return User 모델 객체
     */
    public User toModel() {
        User user = new User();
        user.setUserId(this.userId);
        user.setUsername(this.username);
        return user;
    }
} 