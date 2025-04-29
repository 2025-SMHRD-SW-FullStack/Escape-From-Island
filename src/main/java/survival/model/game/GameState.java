package survival.model.game;

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
        // 생성자 구현 부분
    }
    
    /**
     * 다음 날로 진행
     */
    public void nextDay() {
        // 메소드 구현 부분
    }
    
    /**
     * 게임 종료 처리
     * @param endState 게임 종료 상태
     */
    public void endGame(GameEndState endState) {
        this.gameOver = true;
        this.victory = (endState == GameEndState.VICTORY);
        this.endState = endState;
    }
    
    /**
     * 게임 종료 처리 (기존 함수와의 호환성 유지)
     * @param victory 승리 여부
     */
    public void endGame(boolean victory) {
        endGame(victory ? GameEndState.VICTORY : GameEndState.DEATH);
    }
    
    /**
     * 게임 종료 여부 확인
     * @return 게임 종료 여부
     */
    public boolean isGameOver() {
        return gameOver;
    }
    
    /**
     * 승리 여부 확인
     * @return 승리 여부
     */
    public boolean isVictory() {
        return victory;
    }
    
    /**
     * 게임 종료 상태 반환
     * @return 게임 종료 상태
     */
    public GameEndState getEndState() {
        return endState;
    }
    
    /**
     * 현재 일차 반환
     * @return 일차
     */
    public int getDay() {
        return day;
    }
    
    /**
     * 플레이어 반환
     * @return 플레이어
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * 승리 조건 확인
     * @return 승리 조건 충족 여부
     */
    public boolean checkVictoryCondition() {
        return false; // 임시 반환값
    }
} 
