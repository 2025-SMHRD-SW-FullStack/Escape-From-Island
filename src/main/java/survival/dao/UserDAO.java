package survival.dao;

import survival.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 사용자 데이터 접근을 위한 DAO 클래스
 */
public class UserDAO {
    // 필드
    private Connection connection = null;
    private PreparedStatement psmt = null;
	private ResultSet rs = null;
    
    /**
     * 기본 생성자
     */
    public UserDAO() {
        connection = DatabaseManager.getInstance().getConnection();
    }
    
    /**
     * 사용자명이 이미 존재하는지 확인
     * @param username 확인할 사용자명
     * @return 존재 여부 (true: 존재함, false: 존재하지 않음)
     */
    public boolean checkUserExists(String username) {
        boolean exists = false;
        try {
            String sql = "SELECT COUNT(*) FROM USERS WHERE USERNAME = ?";
            psmt = connection.prepareStatement(sql);
            psmt.setString(1, username);
            
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 리소스는 close하지 않음 (connection은 계속 사용)
        }
        return exists;
    }
    
    /**
     * 사용자 등록
     * @param username 사용자명
     * @param password 비밀번호
     * @return 등록 성공 여부
     */
    public boolean registerUser(String username, String password) {
    	int result = 0;
    	
    	try {
    		String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES(?,?)";
			psmt = connection.prepareStatement(sql);
			psmt.setString(1, username);
			psmt.setString(2, password);
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
		}
    	if(result>0) {
    		return true;
    	}
        return false; // 임시 반환값
    }
    
    /**
     * 사용자 인증
     * @param username 사용자명
     * @param password 비밀번호
     * @return 사용자 객체 (실패 시 null)
     */
    public User authenticateUser(String username, String password) {
        User user = null;
        
    	try {
			String sql = "SELECT * FROM USERS WHERE USERNAME =? AND PASSWORD =?";
			psmt = connection.prepareStatement(sql);
			psmt.setString(1, username);
			psmt.setString(2, password);

			rs = psmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("USERNAME"));
				user.setUserId(rs.getInt("USER_ID"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
    	
        return user; // 임시 반환값
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
    
}
