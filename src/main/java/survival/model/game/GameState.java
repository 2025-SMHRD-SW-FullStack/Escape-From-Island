package survival.model.game;

import survival.util.Constants;

/**
 * 게임 상태를 관리하는 클래스
 */
public class GameState {
    // 필드
    private int day; // 일차
    private Player player; // 플레이어)


    /**
     * 
     */
    public GameState(int day, Player player) {
        this.day = day;
        this.player = player;
    }

    /**
     * 다음 날로 진행
     * - day 필드 1 증가
     * - 날짜 변경에 따른 플레이어 상태 업데이트 (피로도 등)
     * - 게임 상태 관련 로직 처리
     */
    public void nextDay() {
        day++;
        player.addAP(Constants.AP_PER_DAY);
        // 기타 로직 추가 필요시 추가해야 함
    }

    /**
     * 현재 일차 반환
     * 
     * @return 일차
     */
    public int getDay() {
        return day;
    }

    /**
     * 플레이어 반환
     * 
     * @return 플레이어
     */
    public Player getPlayer() {
        return player;
    }
}
