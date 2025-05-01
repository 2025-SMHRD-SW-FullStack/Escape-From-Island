package survival.util;

/**
 * 게임 상수 및 설정 정의 클래스
 */
public class Constants {
    // 플레이어 관련 상수
    public static final int INITIAL_HP = 100;
    public static final int INITIAL_AP = 3;
    public static final int AP_PER_DAY = 3;
    public static final int REST_HP_GAIN = 20;
    
    // 게임 설정
    public static final int DAYS_TO_ESCAPE = 7;
    public static final int EXPLORE_AP_COST = 1;
    public static final int CRAFT_AP_COST = 1;
    public static final int REST_AP_COST = 1;
    public static final int INITIAL_DAY = 1;
    
    // 자원 관련 상수
    // ResourceType enum으로 이동
    public static final int MIN_RESOURCE_AMOUNT = 1;
    public static final int MAX_RESOURCE_AMOUNT = 5;
    
    // 게임 이벤트 확률
    // 탐험 0.7 이벤트 0.3
    public static final double EVENT_CHANCE = 0.3;
    public static final double DAMAGE_EVENT_CHANCE = 0.4;
    public static final double RESOURCE_EVENT_CHANCE = 0.7; // 
    public static final double HEAL_EVENT_CHANCE = 0.1;
    
    // 아이템 제작 관련 상수
    public static final int MIN_CRAFT_RESOURCE = 2;
    public static final int MAX_CRAFT_RESOURCE = 5;
    
    // 게임 종료 조건
    public static final int GAME_OVER_HP = 0;
    public static final String RAFT_ITEM_NAME = "뗏목";
} 
