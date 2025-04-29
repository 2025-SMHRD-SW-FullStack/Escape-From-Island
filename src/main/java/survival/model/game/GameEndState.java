package survival.model.game;

/**
 * 게임 종료 상태 열거형
 */
public enum GameEndState {
    VICTORY("탈출에 성공했습니다!"),
    DEATH("체력이 0이 되어 사망했습니다..."),
    GIVE_UP("게임을 포기했습니다.");
    
    private final String message;
    
    GameEndState(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
} 