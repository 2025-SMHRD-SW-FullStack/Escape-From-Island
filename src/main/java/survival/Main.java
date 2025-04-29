package survival;

import survival.view.ConsoleUI;
import survival.view.GameView;
import survival.view.UIConstants;

/**
 * 애플리케이션 진입점 클래스
 */
public class Main {
    /**
     * 메인 메소드
     */
    public static void main(String[] args) {
        // 시작 헤더 출력
        System.out.println(UIConstants.MENU_HEADER);
        
        // UI 생성
        GameView view = new ConsoleUI();
        
        // 게임 객체 생성 및 실행
        SurvivalGame game = new SurvivalGame(view);
        game.run();
        
        // 리소스 정리
        game.cleanup();
    }
} 
