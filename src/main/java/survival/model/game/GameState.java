package survival.model.game;

import survival.util.Constants;

/**
 * 게임 상태를 관리하는 클래스
 */
public class GameState {
    // 필드
    private int day;
    private Player player;
    private boolean gameOver;
    private boolean victory;
    private GameEndState endState;

    /**
     * 기본 생성자
     */
    public GameState() {

    }

    /**
     * 
     */
    public GameState(int day, Player player, boolean gameOver, boolean victory, GameEndState endState) {
        this.day = day;
        this.player = player;
        this.gameOver = gameOver;
        this.victory = victory;
        this.endState = endState;
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
     * 게임 종료 처리
     * 
     * @param endState 게임 종료 상태
     */
    public void endGame(GameEndState endState) {
        this.gameOver = true;
        this.victory = (endState == GameEndState.VICTORY);
        this.endState = endState;
    }

    /**
     * 게임 종료 처리 (기존 함수와의 호환성 유지)
     * 
     * @param victory 승리 여부
     */
    public void endGame(boolean victory) {
        endGame(victory ? GameEndState.VICTORY : GameEndState.DEATH);
    }

    /**
     * 게임 종료 여부 확인
     * 
     * @return 게임 종료 여부
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * 승리 여부 확인
     * 
     * @return 승리 여부
     */
    public boolean isVictory() {
        return victory;
    }

    /**
     * 게임 종료 상태 반환
     * 
     * @return 게임 종료 상태
     */
    public GameEndState getEndState() {
        return endState;
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

    /**
     * 승리 조건 확인
     * 
     * @return 승리 조건 충족 여부
     */
    public boolean checkVictoryCondition() {
        return false; // 임시 반환값
    }
}
