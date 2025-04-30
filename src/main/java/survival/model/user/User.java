package survival.model.user;


/**
 * 사용자 정보를 관리하는 클래스
 */
public class User {
    // 필드
    private int userId;
    private String username;
    
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
     */
    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
        
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
    
} 
