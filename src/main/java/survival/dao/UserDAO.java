package survival.dao;

import survival.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 사용자 데이터 접근을 위한 DAO 클래스
 */
public class UserDAO {
    // 필드
    private Connection connection;
    
    /**
     * 기본 생성자
     */
    public UserDAO() {
        connection = DatabaseManager.getInstance().getConnection();
    }
    
    /**
     * 사용자 등록
     * @param username 사용자명
     * @param password 비밀번호
     * @return 등록 성공 여부
     */
    public boolean registerUser(String username, String password) {
        
        return false; // 임시 반환값
    }
    
    /**
     * 사용자 인증
     * @param username 사용자명
     * @param password 비밀번호
     * @return 사용자 객체 (실패 시 null)
     */
    public User authenticateUser(String username, String password) {
        
        return null; // 임시 반환값
    }
    
    /**
     * ID로 사용자 조회
     * @param userId 사용자 ID
     * @return 사용자 객체 (없으면 null)
     */
    public User getUserById(int userId) {
        // Oracle 문법에 맞는 쿼리 형태 준비
        String sql = "SELECT * FROM USERS WHERE user_id = ?";
        return null; // 임시 반환값
    }
    
    /**
     * 마지막 로그인 시간 업데이트
     * @param userId 사용자 ID
     */
    public void updateLastLogin(int userId) {
        // Oracle 문법에 맞는 쿼리 형태 준비
        String sql = "UPDATE USERS SET last_login = SYSTIMESTAMP WHERE user_id = ?";
        // 메소드 구현 부분
    }
    
    /**
     * 비밀번호 해싱
     * @param password 원본 비밀번호
     * @return 해싱된 비밀번호
     */
    private String hashPassword(String password) {
        return password; // 임시 구현 (실제로는 보안 해싱 적용)
    }
    
    /**
     * 비밀번호 검증
     * @param input 입력된 비밀번호
     * @param stored 저장된 비밀번호
     * @return 일치 여부
     */
    private boolean verifyPassword(String input, String stored) {
        return input.equals(stored); // 임시 구현 (실제로는 보안 해싱 검증)
    }
}
