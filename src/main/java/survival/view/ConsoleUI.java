package survival.view;

import survival.model.game.GameEndState;
import survival.model.game.Inventory;
import survival.model.game.Player;
import survival.model.game.ResourceType;
import survival.util.Constants;
import survival.util.AudioManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 콘솔 기반 사용자 인터페이스 구현 클래스
 */
public class ConsoleUI implements GameView {
    // 필드
    private Scanner scanner;

    /**
     * 기본 생성자
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {
        System.out.println(UIConstants.MENU_HEADER);
        System.out.println(UIConstants.MENU_PLAY);
        System.out.println(UIConstants.MENU_ACHIEVEMENTS);
        System.out.println(UIConstants.MENU_QUIT);
        System.out.print("메뉴를 선택하세요 >> ");
    }

    @Override
    public void displayLoginMenu() {
        System.out.println(UIConstants.LOGIN_HEADER);
        System.out.println(UIConstants.LOGIN_OPTION);
        System.out.println(UIConstants.REGISTER_OPTION);
        System.out.print("메뉴를 선택하세요 >> ");
    }

    @Override
    public int getIntInput(int min, int max) {
        int input = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    validInput = true;
                } else {
                    System.out.println(UIConstants.INVALID_INPUT);
                    System.out.printf("%d-%d 사이의 숫자를 입력하세요: ", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println(UIConstants.INVALID_INPUT);
                System.out.printf("%d-%d 사이의 숫자를 입력하세요: ", min, max);
            }
        }

        return input;
    }

    @Override
    public String getStringInput() {
        return scanner.nextLine().trim();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayPlayerStatus(Player player) {
        if (player == null) {
            System.out.println("플레이어 정보가 없습니다.");
            return;
        }

        System.out.println("\n===== 플레이어 상태 =====");
        System.out.printf("체력: %d/%d\n", player.getHp(), player.getMaxHp());
        System.out.printf("행동력: %d/%d\n", player.getAp(), Constants.INITIAL_AP);

        // 자원 보유 현황 추가
        System.out.println("\n===== 자원 보유 현황 =====");
        Map<ResourceType, Integer> resources = player.getInventory().getResources();
        if (resources.isEmpty()) {
            System.out.println("보유한 자원이 없습니다.");
        } else {
            for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
                System.out.printf("%s: %d개\n", entry.getKey().getLabel(), entry.getValue());
            }
        }

        System.out.println("=======================\n");
    }

    @Override
    public void displayDay(int day) {
        System.out.println("\n===================================");
        System.out.printf("           %d일차\n", day);
        System.out.println("===================================\n");

    }


    @Override
    public void displayEnding(boolean victory) {
        GameEndState endState = victory ? GameEndState.VICTORY : GameEndState.DEATH;
        displayEnding(endState);
    }

    @Override
    public void displayEnding(GameEndState endState) {
        if (endState == null) {
            System.out.println("\n게임 종료 정보가 없습니다.");
            return;
        }

        System.out.println("\n===================================");
        System.out.println("          게임 종료");
        System.out.println("===================================");

 
        if (endState == GameEndState.VICTORY) {
            try {

                System.out.println("구조되었습니다! 축하합니다!");
            } catch (Exception e) {

                System.out.println("구조되었습니다! 축하합니다!");
            }
        } 

        System.out.println(endState.getMessage());
        System.out.println("===================================\n");

        // 엔터 키를 눌러 계속하도록 프롬프트 표시
        System.out.println("엔터 키를 눌러 메인 메뉴로 돌아가기...");
        scanner.nextLine(); // 엔터 키 입력 대기

        // 엔터 키를 누른 후 메뉴 배경음악 재생
        AudioManager.getInstance().playBgm(AudioManager.BGM_MENU);
    }

    @Override
    public void displayInventory(Inventory inventory) {
        if (inventory == null) {
            System.out.println("인벤토리 정보가 없습니다.");
            return;
        }

        System.out.println("\n===== 인벤토리 =====");
        Map<ResourceType, Integer> resources = inventory.getResources();

        if (resources.isEmpty()) {
            System.out.println("아이템 없음");
        } else {
            for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
                System.out.printf("%s: %d개\n", entry.getKey().getLabel(), entry.getValue());
            }
        }
        System.out.println("===================\n");
    }

    @Override
    public void displayCraftingMenu(List<String> items) {
        System.out.println("\n===== 제작 가능 아이템 =====");
        if (items == null || items.isEmpty()) {
            System.out.println("제작 가능한 아이템이 없습니다.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, items.get(i));
            }
        }
        System.out.println("==========================\n");
    }

    @Override
    public void displayAchievements(List<String> achievements, int totalAchievements) {
        System.out.println("\n===== 업적 =====");
        System.out.printf("달성한 업적: %d/%d\n", achievements.size(), totalAchievements);

        if (achievements == null || achievements.isEmpty()) {
            System.out.println("달성한 업적이 없습니다.");
        } else {
            for (String achievement : achievements) {
                System.out.println("- " + achievement);
            }
        }
        System.out.println("===============\n");
    }

    @Override
    public void displayError(String message) {
        System.err.println("\n===== 오류 =====");
        System.err.println(message);
        System.err.println("===============\n");
    }

    @Override
    public void showMessageNoln(String message) {
        System.out.print(message);
    }
}
