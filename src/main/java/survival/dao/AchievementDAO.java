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
    
    /**
     * 기본 생성자
     */
    public AchievementDAO() {
        connection = DatabaseManager.getInstance().getConnection();
    }
    
    /**
     * 모든 업적 목록 조회
     * @return 업적 목록
     */
    public List<Achievement> getAllAchievements() {
        // Oracle 문법에 맞는 쿼리 형태 준비
        String sql = "SELECT * FROM ACHIEVEMENTS ORDER BY achievement_id";
        return new ArrayList<>(); // 임시 반환값
    }
    
    /**
     * 사용자의 업적 목록 조회
     * @param userId 사용자 ID
     * @return 업적 목록
     */
    public List<Achievement> getUserAchievements(int userId) {
        // Oracle 문법에 맞는 쿼리 형태 준비
        String sql = "SELECT a.* FROM ACHIEVEMENTS a " +
                     "JOIN USER_ACHIEVEMENTS ua ON a.achievement_id = ua.achievement_id " +
                     "WHERE ua.user_id = ?";
        return new ArrayList<>(); // 임시 반환값
    }
    
    /**
     * 사용자 업적 해금
     * @param userId 사용자 ID
     * @param achievementId 업적 ID
     * @return 해금 성공 여부
     */
    public boolean unlockAchievement(int userId, int achievementId) {
        // Oracle 문법에 맞는 쿼리 형태 준비
        String sql = "INSERT INTO USER_ACHIEVEMENTS (user_id, achievement_id) VALUES (?, ?)";
        return false; // 임시 반환값
    }
    
    /**
     * 특정 조건의 업적 달성 확인
     * @param userId 사용자 ID
     * @param condition 업적 조건
     * @return 업적 달성 여부
     */
    public boolean checkAchievement(int userId, String condition) {
        // Oracle 문법에 맞는 쿼리 형태 준비
        String sql = "SELECT COUNT(*) FROM ACHIEVEMENTS a " +
                     "WHERE a.condition = ? " +
                     "AND NOT EXISTS (SELECT 1 FROM USER_ACHIEVEMENTS ua " +
                     "               WHERE ua.achievement_id = a.achievement_id " +
                     "               AND ua.user_id = ?)";
        return false; // 임시 반환값
    }
} 
