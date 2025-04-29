package survival.model.game;

import java.util.function.Consumer;

/**
 * 게임 이벤트를 관리하는 클래스
 */
public class Event {
    // 필드
    private EventType type;
    private String description;
    private Consumer<Player> effect;
    
    /**
     * 생성자
     * @param type 이벤트 타입
     * @param description 이벤트 설명
     * @param effect 플레이어에게 적용될 효과
     */
    public Event(EventType type, String description, Consumer<Player> effect) {
        this.type = type;
        this.description = description;
        this.effect = effect;
    }
    
    /**
     * 이벤트 효과 실행
     * @param player 대상 플레이어
     */
    public void execute(Player player) {
        // 메소드 구현 부분
    }
    
    /**
     * 이벤트 타입 반환
     * @return 이벤트 타입
     */
    public EventType getType() {
        return type;
    }
    
    /**
     * 이벤트 설명 반환
     * @return 이벤트 설명
     */
    public String getDescription() {
        return description;
    }
} 
