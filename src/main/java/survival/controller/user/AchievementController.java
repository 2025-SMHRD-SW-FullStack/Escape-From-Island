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
    private List<Achievement> earnedAchievements; // 게임 중 획득한 업적 목록
    private boolean isLastHealthy; // 마지막 체력 상태가 80% 이상이었는지 여부
    
    /**
     * 생성자
     * @param currentUser 현재 사용자
     */
    public AchievementController(User currentUser) {
        this.currentUser = currentUser;
        this.achievementDAO = new AchievementDAO();
        this.earnedAchievements = new ArrayList<>();
        this.isLastHealthy = false;
    }
    
    /**
     * 기본 생성자
     */
    public AchievementController() {
        this.achievementDAO = new AchievementDAO();
        this.earnedAchievements = new ArrayList<>();
        this.isLastHealthy = false;
    }
    
    /**
     * 현재 사용자 설정
     * @param user 사용자
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    /**
     * 게임 세션 시작 시 획득 업적 목록 초기화
     */
    public void resetEarnedAchievements() {
        this.earnedAchievements.clear();
        this.isLastHealthy = false;
    }
    
    /**
     * 게임 상태에 따른 업적 확인 및 업데이트
     * @param gameState 게임 상태
     */
    public void checkAndUpdateAchievements(GameState gameState) {
        if (currentUser == null) {
            return;
        }
        
        // 게임이 종료되었을 경우(gameState == null)에도 업적 체크는 진행
        
        // 1. 첫 생존자 업적 (게임 시작)
        unlockAchievementByCondition("START_GAME");
        
        // 게임 상태가 null(게임 종료)인 경우
        if (gameState == null) {
            // 2. 탈출 성공 업적 (섬에서 탈출)
            unlockAchievementByCondition("ESCAPE_ISLAND");
            
            // 3. 생존 전문가 업적 - 체력 80% 이상으로 탈출 성공했을 때만 획득
            if (isLastHealthy) {
                unlockAchievementByCondition("SURVIVE_HEALTHY");
            }
            
            return;
        }
        
        // 현재 체력 상태 저장 (게임 종료 시 사용)
        updatePlayerHealthStatus(gameState);
        
        // 4. 자원 수집가 업적 (모든 종류의 자원 수집)
        if (hasCollectedAllResources(gameState)) {
            unlockAchievementByCondition("COLLECT_ALL_RESOURCES");
        }
    }
    
    /**
     * 플레이어의 체력 상태 업데이트
     * @param gameState 게임 상태
     */
    private void updatePlayerHealthStatus(GameState gameState) {
        if (gameState == null || gameState.getPlayer() == null) {
            return;
        }
        
        // 체력이 80% 이상인지 확인
        int currentHp = gameState.getPlayer().getHp();
        int maxHp = gameState.getPlayer().getMaxHp();
        
        // 현재 체력 상태 저장
        isLastHealthy = currentHp >= (maxHp * 0.8);
    }
    
    /**
     * 조건에 맞는 업적 해금
     * @param condition 업적 조건
     */
    private void unlockAchievementByCondition(String condition) {
        try {
            if (currentUser == null) {
                return;
            }
            
            // 아직 달성하지 않은 업적 ID 확인
            int achievementId = achievementDAO.checkAchievement(currentUser.getUserId(), condition);
            
            // 업적 ID가 유효하면 해금
            if (achievementId > 0) {
                // 업적 해금
                boolean success = achievementDAO.unlockAchievement(currentUser.getUserId(), achievementId);
                
                // 성공적으로 해금된 경우 획득 업적 목록에 추가
                if (success) {
                    // 해당 업적 정보 가져오기
                    Achievement achievement = getAchievementById(achievementId);
                    if (achievement != null) {
                        earnedAchievements.add(achievement);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ID로 업적 정보 조회
     * @param achievementId 업적 ID
     * @return 업적 정보
     */
    private Achievement getAchievementById(int achievementId) {
        List<Achievement> allAchievements = achievementDAO.getAllAchievements();
        for (Achievement achievement : allAchievements) {
            if (achievement.getAchievementId() == achievementId) {
                return achievement;
            }
        }
        return null;
    }
    
    /**
     * 플레이어가 탈출했는지 확인
     * @param gameState 게임 상태
     * @return 탈출 여부
     */
    private boolean isPlayerEscaped(GameState gameState) {
        // 뗏목 제작 완료 여부로 판단
        // 실제 로직은 게임 상태에 따라 다르게 구현
        return gameState == null; // 게임 종료 시 탈출 성공으로 판단
    }
    
    /**
     * 모든 자원을 수집했는지 확인
     * @param gameState 게임 상태
     * @return 수집 여부
     */
    private boolean hasCollectedAllResources(GameState gameState) {
        // 인벤토리에 모든 필수 자원이 있는지 확인
        if (gameState == null || gameState.getPlayer() == null) {
            return false;
        }
        
        // 실제 구현에서는 필요한 모든 자원 종류를 체크
        return gameState.getPlayer().getInventory().hasAllResourceTypes();
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
     * 게임 중 새로 획득한 업적 목록 반환
     * @return 새로 획득한 업적 목록
     */
    public List<Achievement> getEarnedAchievements() {
        return earnedAchievements;
    }
    
    /**
     * 게임 중 새로 획득한 업적이 있는지 확인
     * @return 새로운 업적 획득 여부
     */
    public boolean hasEarnedNewAchievements() {
        return !earnedAchievements.isEmpty();
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
    
    /**
     * 게임 중 획득한 업적 표시
     * @param view 게임 화면
     */
    public void displayEarnedAchievements(GameView view) {
        if (earnedAchievements.isEmpty()) {
            return;
        }
        
        view.showMessage("\n=== 새로 획득한 업적 ===");
        for (Achievement achievement : earnedAchievements) {
            view.showMessage("★ " + achievement.getTitle() + " - " + achievement.getDescription());
        }
        view.showMessage("=====================");
    }
} 
