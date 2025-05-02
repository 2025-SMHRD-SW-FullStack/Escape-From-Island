package survival.util;

import java.util.ArrayList;
import java.util.List;

import survival.dto.AchievementDTO;
import survival.dto.UserDTO;
import survival.model.user.Achievement;
import survival.model.user.User;

/**
 * 모델과 DTO 간 변환을 담당하는 유틸리티 클래스
 */
public class DTOConverter {
    
    /**
     * User 모델을 UserDTO로 변환
     * @param user 사용자 모델
     * @return 사용자 DTO
     */
    public static UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user);
    }
    
    /**
     * UserDTO를 User 모델로 변환
     * @param userDTO 사용자 DTO
     * @return 사용자 모델
     */
    public static User convertToModel(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return userDTO.toModel();
    }
    
    /**
     * Achievement 모델을 AchievementDTO로 변환
     * @param achievement 업적 모델
     * @param unlocked 해금 여부
     * @return 업적 DTO
     */
    public static AchievementDTO convertToDTO(Achievement achievement, boolean unlocked) {
        if (achievement == null) {
            return null;
        }
        return new AchievementDTO(achievement, unlocked);
    }
    
    /**
     * AchievementDTO를 Achievement 모델로 변환
     * @param achievementDTO 업적 DTO
     * @return 업적 모델
     */
    public static Achievement convertToModel(AchievementDTO achievementDTO) {
        if (achievementDTO == null) {
            return null;
        }
        return achievementDTO.toModel();
    }
    
    /**
     * Achievement 모델 리스트를 AchievementDTO 리스트로 변환
     * @param achievements 업적 모델 리스트
     * @param allUnlocked 모든 업적이 해금되었는지 여부
     * @return 업적 DTO 리스트
     */
    public static List<AchievementDTO> convertToDTOList(List<Achievement> achievements, boolean allUnlocked) {
        if (achievements == null) {
            return new ArrayList<>();
        }
        
        List<AchievementDTO> dtoList = new ArrayList<>();
        for (Achievement achievement : achievements) {
            dtoList.add(new AchievementDTO(achievement, allUnlocked));
        }
        return dtoList;
    }
    
    /**
     * 사용자가 획득한 업적과 전체 업적을 기반으로 업적 DTO 리스트 생성
     * @param userAchievements 사용자가 획득한 업적 목록
     * @param allAchievements 전체 업적 목록
     * @return 업적 DTO 리스트 (해금 여부 포함)
     */
    public static List<AchievementDTO> createAchievementDTOList(List<Achievement> userAchievements, List<Achievement> allAchievements) {
        if (allAchievements == null) {
            return new ArrayList<>();
        }
        
        List<AchievementDTO> dtoList = new ArrayList<>();
        for (Achievement achievement : allAchievements) {
            boolean unlocked = isAchievementUnlocked(achievement, userAchievements);
            dtoList.add(new AchievementDTO(achievement, unlocked));
        }
        return dtoList;
    }
    
    /**
     * 특정 업적이 사용자의 업적 목록에 있는지 확인
     * @param achievement 확인할 업적
     * @param userAchievements 사용자의 업적 목록
     * @return 해금 여부
     */
    private static boolean isAchievementUnlocked(Achievement achievement, List<Achievement> userAchievements) {
        if (userAchievements == null || achievement == null) {
            return false;
        }
        
        for (Achievement userAchievement : userAchievements) {
            if (userAchievement.getAchievementId() == achievement.getAchievementId()) {
                return true;
            }
        }
        return false;
    }
} 