package survival.view;

import survival.dto.GameEndDTO;
import survival.dto.InventoryDTO;
import survival.dto.PlayerDTO;

import java.util.List;

/**
 * 게임 화면 인터페이스
 */
public interface GameView {
    /**
     * 게임 메뉴 표시
     */
    void displayMenu();
    
    /**
     * 로그인 메뉴 표시
     */
    void displayLoginMenu();
    
    /**
     * 정수 입력 처리
     * @param min 최소값
     * @param max 최대값
     * @return 사용자 입력값
     */
    int getIntInput(int min, int max);
    
    /**
     * 문자열 입력 처리
     * @return 사용자 입력값
     */
    String getStringInput();
    
    /**
     * 메시지 표시
     * @param message 표시할 메시지
     */
    void showMessage(String message);
    
    /**
     * 플레이어 상태 표시
     * @param playerDTO 플레이어 DTO
     */
    void displayPlayerStatus(PlayerDTO playerDTO);
    
    /**
     * 현재 일차 표시
     * @param day 일차
     */
    void displayDay(int day);
    
    /**
     * 게임 종료 화면 표시 (기존 호환성 유지)
     * @param victory 승리 여부
     */
    void displayEnding(boolean victory);
    
    /**
     * 게임 종료 화면 표시
     * @param endDTO 게임 종료 DTO
     */
    void displayEnding(GameEndDTO endDTO);
    
    /**
     * 인벤토리 내용 표시
     * @param inventoryDTO 인벤토리 DTO
     */
    void displayInventory(InventoryDTO inventoryDTO);
    
    /**
     * 제작 메뉴 표시
     * @param items 제작 가능한 아이템 목록
     */
    void displayCraftingMenu(List<String> items);
    
    /**
     * 업적 목록 표시
     * @param achievements 달성한 업적 목록
     * @param totalAchievements 전체 업적 개수
     */
    void displayAchievements(List<String> achievements, int totalAchievements);
    
    /**
     * 시스템 오류 메시지 표시
     * @param message 오류 메시지
     */
    void displayError(String message);
} 
