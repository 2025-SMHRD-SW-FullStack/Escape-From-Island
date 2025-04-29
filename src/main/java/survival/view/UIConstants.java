package survival.view;

/**
 * UI 관련 상수 정의 클래스
 */
public class UIConstants {
    // 메뉴 상수
    public static final String MENU_HEADER = "===== 무인도 생존 게임 =====";
    public static final String MENU_PLAY = "1. 게임 시작";
    public static final String MENU_ACHIEVEMENTS = "2. 업적 확인";
    public static final String MENU_QUIT = "3. 종료";
    
    // 로그인 메뉴 상수
    public static final String LOGIN_HEADER = "===== 로그인/회원가입 =====";
    public static final String LOGIN_OPTION = "1. 로그인";
    public static final String REGISTER_OPTION = "2. 회원가입";
    public static final String BACK_OPTION = "3. 돌아가기";
    
    // 게임 화면 상수
    public static final String GAME_HEADER = "===== %d일차 =====";
    public static final String PLAYER_STATUS = "체력: %d/%d | 행동력: %d";
    
    // 행동 메뉴 상수
    public static final String ACTION_EXPLORE = "1. 탐색하기";
    public static final String ACTION_CRAFT = "2. 제작하기";
    public static final String ACTION_REST = "3. 휴식하기";
    public static final String ACTION_END_DAY = "4. 하루 종료";
    
    // 결과 메시지 상수
    public static final String SUCCESS_CRAFT = "%s 제작 성공!";
    public static final String FAIL_CRAFT = "자원이 부족합니다.";
    public static final String SUCCESS_EXPLORE = "%s 발견!";
    public static final String SUCCESS_REST = "체력이 회복되었습니다.";
    
    // 로그인/회원가입 관련 상수
    public static final String REGISTER_HEADER = "===== 회원가입 =====";
    public static final String INPUT_USERNAME = "사용자명: ";
    public static final String INPUT_PASSWORD = "비밀번호: ";
    
    // 게임 종료 메시지
    public static final String VICTORY_MESSAGE = "축하합니다! 섬에서 탈출했습니다!";
    public static final String DEFEAT_MESSAGE = "생존에 실패했습니다... 다음에 다시 도전하세요.";
    
    // 기타 메시지
    public static final String INVALID_INPUT = "잘못된 입력입니다. 다시 시도해주세요.";
    public static final String LOGIN_SUCCESS = "로그인 성공!";
    public static final String LOGIN_FAIL = "로그인 실패. 사용자명 또는 비밀번호를 확인해주세요.";
    public static final String REGISTER_SUCCESS = "회원가입 성공!";
    public static final String REGISTER_FAIL = "회원가입 실패. 이미 존재하는 사용자명입니다.";
} 
