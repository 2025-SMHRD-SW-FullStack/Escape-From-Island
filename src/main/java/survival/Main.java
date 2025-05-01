package survival;

import survival.view.ConsoleUI;
import survival.view.GameView;
import survival.view.UIConstants;

import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * 애플리케이션 진입점 클래스
 */
public class Main {
    
    // 게임 객체
    private static SurvivalGame game;
    
    /**
     * 메인 메소드
     */
    public static void main(String[] args) {
        try {             
            
            // 시작 헤더 출력
            System.out.println(UIConstants.MENU_HEADER);
            
            // UI 생성
            GameView view = new ConsoleUI();
            
            // 게임 객체 생성 및 실행
            game = new SurvivalGame(view);
            game.run();
            
            // 프로그램 종료 시 리소스 정리
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (game != null) {
                    game.cleanup();
                }
            }));
            
        } catch (Exception e) {
            // 에러 스트림에 스택 트레이스 출력
            e.printStackTrace();
        }
    }
} 
