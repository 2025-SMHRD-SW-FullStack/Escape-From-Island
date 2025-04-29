package survival.dto;

/**
 * 게임 종료 상태 전달용 DTO 클래스
 */
public class GameEndDTO {
    private boolean victory;
    private String message;
    
    public GameEndDTO(boolean victory, String message) {
        this.victory = victory;
        this.message = message;
    }
    
    /**
     * 승리 여부 확인
     */
    public boolean isVictory() {
        return victory;
    }
    
    /**
     * 종료 메시지 반환
     */
    public String getMessage() {
        return message;
    }
} 