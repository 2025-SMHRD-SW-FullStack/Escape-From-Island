package survival.model.user;

import java.util.Date;

/**
 * 사용자 정보를 관리하는 클래스
 */
public class User {
    // 필드
    private int userId;
    private String username;
    private Date createdAt;
    private Date lastLogin;
    
    /**
     * 기본 생성자
     */
    public User() {
        // 생성자 구현 부분
    }
    
    /**
     * 모든 필드를 초기화하는 생성자
     * @param userId 사용자 ID
     * @param username 사용자명
     * @param createdAt 계정 생성일
     * @param lastLogin 마지막 로그인 일시
     */
    public User(int userId, String username, Date createdAt, Date lastLogin) {
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }
    
    // Getter/Setter 메소드
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
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
} 
