package survival.model.game;

/**
 * 게임 종료 상태 열거형
 */
public enum GameEndState {
    VICTORY("탈출에 성공했습니다!"),
    DEATH("생존에 실패했습니다!"),
    GIVE_UP("게임을 포기했습니다."); 
	// ctrl + c 클릭 시 에러 문구 말고 GIVE_UP의 message 나오게
    
    private final String message;
    
    GameEndState(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
} 