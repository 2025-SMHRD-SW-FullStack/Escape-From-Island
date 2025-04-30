package survival.dao;

import survival.model.user.Achievement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * 업적 데이터 접근을 위한 DAO 클래스
 */
public class AchievementDAO {
	// 필드
	private Connection connection;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;

	/**
	 * 기본 생성자
	 */
	public AchievementDAO() {
		connection = DatabaseManager.getInstance().getConnection();
	}

	/**
	 * 모든 업적 목록 조회
	 * 
	 * @return 업적 목록
	 */
	public List<Achievement> getAllAchievements() {
		// Oracle 문법에 맞는 쿼리 형태 준비
		List<Achievement> allAchi = new ArrayList<>();
		try {
			String sql = "SELECT * FROM ACHIEVEMENTS ORDER BY achievement_id";
			psmt = connection.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				Achievement achievement = new Achievement();
				achievement.setAchievementId(rs.getInt("ACHIEVEMENT_ID"));
				achievement.setTitle(rs.getString("TITLE"));
				achievement.setDescription(rs.getString("DESCRIPTION"));
				achievement.setCondition(rs.getString("CONDITION"));
				achievement.setType("ACHIEVEMENT_TYPE");

				allAchi.add(achievement);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // 필요시 로깅 처리
		} finally {
			DatabaseManager.getInstance().closeConnection();
		}

		return allAchi;
	}

	/**
	 * 사용자의 업적 목록 조회
	 * 
	 * @param userId 사용자 ID
	 * @return 업적 목록
	 */
	public List<Achievement> getUserAchievements(int userId) {
		// Oracle 문법에 맞는 쿼리 형태 준비
		List<Achievement> userAchi = new ArrayList<>();
		try {
			String sql = "SELECT a.* FROM ACHIEVEMENTS a "
		               + "JOIN USER_ACHIEVEMENTS ua ON a.achievement_id = ua.achievement_id "
		               + "WHERE ua.user_id = ?";
			psmt = connection.prepareStatement(sql);
			psmt.setInt(1, userId);
			rs = psmt.executeQuery();

			while (rs.next()) {
				Achievement achievement = new Achievement();
				achievement.setAchievementId(rs.getInt("ACHIEVEMENT_ID"));
				achievement.setTitle(rs.getString("TITLE"));
				achievement.setDescription(rs.getString("DESCRIPTION"));
				achievement.setCondition(rs.getString("CONDITION"));
				achievement.setType(rs.getString("ACHIEVEMENT_TYPE"));

				userAchi.add(achievement);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // 필요시 로깅 처리
		} finally {
			DatabaseManager.getInstance().closeConnection();
		}

		return userAchi;
	}

	/**
	 * 사용자 업적 해금
	 * 
	 * @param userId        사용자 ID
	 * @param achievementId 업적 ID
	 * @return 해금 성공 여부
	 */
	public boolean unlockAchievement(int userId, int achievementId) {
		// Oracle 문법에 맞는 쿼리 형태 준비
	    try {
	    	String sql = "INSERT INTO USER_ACHIEVEMENTS (user_id, achievement_id) VALUES (?, ?)";
	        psmt = connection.prepareStatement(sql);
	        psmt.setInt(1, userId);
	        psmt.setInt(2, achievementId);
	        int result = psmt.executeUpdate();

	        return result > 0; // 
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	        return false;
	    } finally {
	        DatabaseManager.getInstance().closeConnection();
	    }
	}

	/**
	 * 특정 조건의 업적 달성 확인
	 * 
	 * @param userId    사용자 ID
	 * @param condition 업적 조건
	 * @return 업적 달성 여부
	 */
	public boolean checkAchievement(int userId, String condition) {
		// Oracle 문법에 맞는 쿼리 형태 준비
		try {
			String sql = "SELECT COUNT(*) FROM ACHIEVEMENTS a " +
		             "WHERE a.condition = ? " +
		             "AND NOT EXISTS (SELECT 1 FROM USER_ACHIEVEMENTS ua " +
		             "                WHERE ua.achievement_id = a.achievement_id " +
		             "                  AND ua.user_id = ?)";
	        psmt = connection.prepareStatement(sql);
	        psmt.setString(1, condition);
	        psmt.setInt(2, userId);
	        int result = psmt.executeUpdate();

	        return result > 0; // 
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	        return false;
	    } finally {
	        DatabaseManager.getInstance().closeConnection();
	    }
	}
}
