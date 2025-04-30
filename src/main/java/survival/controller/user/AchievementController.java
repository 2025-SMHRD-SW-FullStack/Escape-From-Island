package survival.controller.user;

import survival.model.user.User;
import survival.model.user.Achievement;
import survival.model.game.GameState;
import survival.controller.game.GameController;
import survival.dao.AchievementDAO;
import survival.view.GameView;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 업적 관리 및 처리를 담당하는 컨트롤러 클래스
 */
public class AchievementController {
    // 필드
    private AchievementDAO achievementDAO;
    private User currentUser;
    
    /**
     * 생성자
     * @param currentUser 현재 사용자
     */
    public AchievementController(User currentUser) {
        this.currentUser = currentUser;
        this.achievementDAO = new AchievementDAO();
    }
    
    /**
     * 기본 생성자
     */
    public AchievementController() {
        this.achievementDAO = new AchievementDAO();
    }
    
    /**
     * 현재 사용자 설정
     * @param user 사용자
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    /**
     * 게임 상태에 따른 업적 확인 및 업데이트
     * @param gameState 게임 상태
     */
    public void checkAndUpdateAchievements(GameState gameState) {
        // 메소드 구현 부분
    }
    
    /**
     * 사용자의 업적 목록 반환
     * @return 업적 목록
     */
    public List<Achievement> getUserAchievements() {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        
        return achievementDAO.getUserAchievements(currentUser.getUserId());
    }
    
    /**
     * 업적 제목 목록 반환
     * @return 업적 제목 목록
     */
    public List<String> getAchievementTitles() {
        List<Achievement> achievements = getUserAchievements();
        return achievements.stream()
                .map(Achievement::getTitle)
                .collect(Collectors.toList());
    }
    
    /**
     * 전체 업적 수 반환
     * @return 전체 업적 수
     */
    public int getTotalAchievementsCount() {
        return achievementDAO.getAllAchievements().size();
    }
    
    /**
     * 업적 데이터 처리 및 표시
     * @param view 게임 화면
     */
    public void processAchievements(GameView view) {
        // 사용자 업적 목록 조회
        List<Achievement> achievements = getUserAchievements();
        
        // 업적이 없는 경우
        if (achievements.isEmpty()) {
            view.showMessage("아직 획득한 업적이 없습니다.");
            return;
        }
        
        // 업적 목록 표시 (View에게 위임)
        view.showMessage("=== 획득한 업적 목록 ===");
        for (Achievement achievement : achievements) {
            view.showMessage(achievement.getTitle() + " - " + achievement.getDescription());
        }
        view.showMessage("======================");
    }
} 
