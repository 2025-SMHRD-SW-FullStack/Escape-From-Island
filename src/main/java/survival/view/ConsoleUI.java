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
    
    // ASCII 아트 파일 경로 상수
    private static final String ASCII_PATH = "ascii/";
    private static final String ASCII_BERRY = ASCII_PATH + "berry.txt";
    private static final String ASCII_CLOTH = ASCII_PATH + "cloth.txt";
    private static final String ASCII_KUMA = ASCII_PATH + "kuma.txt";
    private static final String ASCII_LOSE = ASCII_PATH + "lose.txt";
    private static final String ASCII_RAIN = ASCII_PATH + "rain.txt";
    private static final String ASCII_SHIP = ASCII_PATH + "ship.txt";
    private static final String ASCII_STONE = ASCII_PATH + "stone.txt";
    private static final String ASCII_SUN = ASCII_PATH + "sun.txt";
    private static final String ASCII_TREE = ASCII_PATH + "tree.txt";
    
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
                
                // 자원 보유 시 해당 자원의 아스키 아트 표시
                displayResourceAsciiArt(entry.getKey());
            }
        }
        
        System.out.println("=======================\n");
    }
    
    /**
     * 자원 유형에 따른 아스키 아트 표시
     * 
     * @param resourceType 자원 유형
     */
    private void displayResourceAsciiArt(ResourceType resourceType) {
        if (resourceType == null) return;
        
        System.out.println("디버깅: 자원 아스키 아트 표시 - " + resourceType.getLabel());
        
        // 자원 유형에 따라 적절한 아스키 아트 파일 선택
        String asciiFile = null;
        
        switch (resourceType) {
            case WOOD:
                asciiFile = ASCII_TREE;
                break;
            case CLOTH:
                asciiFile = ASCII_CLOTH;
                break;
            case STONE:
                asciiFile = ASCII_STONE;
                break;
            default:
                // 기타 유형의 자원은 berry 아스키 아트로 표시 (임시 대체)
                asciiFile = ASCII_BERRY;
                break;
        }
        
        // 아스키 아트 표시
        if (asciiFile != null) {
            try {
                printAsciiArt(asciiFile);
            } catch (Exception e) {
                System.out.println("디버깅: 자원 아스키 아트 표시 예외 - " + e.getMessage());
                System.out.println("자원 아스키 아트 로드 실패: " + resourceType.getLabel());
                // 기본 아스키 아트 직접 출력
                System.out.println(getDefaultAsciiArt(asciiFile));
            }
        }
    }
    
    @Override
    public void displayDay(int day) {
        System.out.println("\n===================================");
        System.out.printf("           %d일차\n", day);
        System.out.println("===================================\n");
        
        // 날짜에 따라 랜덤하게 날씨 아스키 아트 표시
        displayWeatherAsciiArt(day);
    }
    
    /**
     * 날짜에 따른 날씨 아스키 아트 표시
     * 
     * @param day 게임 진행 일차
     */
    private void displayWeatherAsciiArt(int day) {
        // 홀수일은 맑은 날, 짝수일은 비오는 날로 가정
        boolean isSunny = day % 2 == 1;
        
        System.out.println("디버깅: 날씨 아스키 아트 표시 - " + (isSunny ? "맑음" : "비"));
        
        try {
            String asciiFile;
            if (isSunny) {
                asciiFile = ASCII_SUN;
                printAsciiArt(asciiFile);
                System.out.println("오늘은 맑은 날씨입니다.");
            } else {
                asciiFile = ASCII_RAIN;
                printAsciiArt(asciiFile);
                System.out.println("오늘은 비가 내립니다.");
            }
        } catch (Exception e) {
            System.out.println("디버깅: 날씨 아스키 아트 표시 예외 - " + e.getMessage());
            
            // 아스키 아트 로드 실패 시 기본 텍스트로 대체
            if (isSunny) {
                System.out.println(getDefaultAsciiArt(ASCII_SUN));
                System.out.println("오늘은 맑은 날씨입니다.");
            } else {
                System.out.println(getDefaultAsciiArt(ASCII_RAIN));
                System.out.println("오늘은 비가 내립니다.");
            }
        }
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
        
        System.out.println("디버깅: 게임 종료 아스키 아트 표시 - " + endState);
        
        // 게임 결과에 따른 아스키 아트 표시
        if (endState == GameEndState.VICTORY) {
            try {
                printAsciiArt(ASCII_SHIP);
                System.out.println("구조되었습니다! 축하합니다!");
            } catch (Exception e) {
                System.out.println("디버깅: 승리 아스키 아트 표시 예외 - " + e.getMessage());
                System.out.println(getDefaultAsciiArt(ASCII_SHIP));
                System.out.println("구조되었습니다! 축하합니다!");
            }
        } else if (endState == GameEndState.DEATH) {
            try {
                printAsciiArt(ASCII_LOSE);
                System.out.println("생존에 실패했습니다...");
            } catch (Exception e) {
                System.out.println("디버깅: 패배 아스키 아트 표시 예외 - " + e.getMessage());
                System.out.println(getDefaultAsciiArt(ASCII_LOSE));
                System.out.println("생존에 실패했습니다...");
            }
        } else if (endState == GameEndState.GIVE_UP) {
            try {
                printAsciiArt(ASCII_KUMA);
                System.out.println("게임을 포기했습니다...");
            } catch (Exception e) {
                System.out.println("디버깅: 포기 아스키 아트 표시 예외 - " + e.getMessage());
                System.out.println(getDefaultAsciiArt(ASCII_KUMA));
                System.out.println("게임을 포기했습니다...");
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
                
                // 각 자원에 대한 아스키 아트 표시
                displayResourceAsciiArt(entry.getKey());
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
    
    /**
     * ASCII 아트 파일을 로드
     * 
     * @param fileName 로드할 ASCII 아트 파일 이름
     * @return ASCII 아트 내용 문자열
     */
    public String loadAsciiArt(String fileName) {
        String defaultArt = getDefaultAsciiArt(fileName);
        String userDir = System.getProperty("user.dir");
        
        // Windows 환경 여부 확인 및 우선 인코딩 설정
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        
        // 가능한 파일 경로들
        Path[] pathsToTry = {
            Paths.get(userDir, "target/classes", fileName),
            Paths.get(userDir, "src/main/resources", fileName),
            Paths.get(fileName)
        };
        
        // 각 가능한 경로에 대해 시도
        for (Path path : pathsToTry) {
            if (Files.exists(path)) {
                try {
                    // Windows 환경에서는 MS949 인코딩 우선 시도
                    if (isWindows) {
                        try {
                            byte[] bytes = Files.readAllBytes(path);
                            String content = new String(bytes, "MS949");
                            if (!content.trim().isEmpty()) {
                                return content;
                            }
                        } catch (Exception e) {
                            // MS949 로드 실패 시 다른 인코딩 시도
                        }
                    }
                    
                    // 다른 인코딩 시도
                    String content = null;
                    
                    // UTF-8 시도
                    try {
                        content = Files.readString(path, StandardCharsets.UTF_8);
                        if (content != null && !content.trim().isEmpty()) {
                            return content;
                        }
                    } catch (Exception e) {
                        // UTF-8 로드 실패
                    }
                    
                    // EUC-KR 시도
                    try {
                        byte[] bytes = Files.readAllBytes(path);
                        content = new String(bytes, "EUC-KR");
                        if (content != null && !content.trim().isEmpty()) {
                            return content;
                        }
                    } catch (Exception e) {
                        // EUC-KR 로드 실패
                    }
                    
                    // MS949가 우선 시도되지 않았을 경우 여기서 시도
                    if (!isWindows) {
                        try {
                            byte[] bytes = Files.readAllBytes(path);
                            content = new String(bytes, "MS949");
                            if (content != null && !content.trim().isEmpty()) {
                                return content;
                            }
                        } catch (Exception e) {
                            // MS949 로드 실패
                        }
                    }
                    
                    // 여기까지 왔다면 모든 인코딩 시도 실패
                } catch (Exception e) {
                    // 예외 발생 시 다음 경로 시도
                }
            }
        }
        
        // 클래스패스에서 리소스 로드 시도
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(fileName);
            if (resourceUrl != null) {
                try {
                    URI uri = resourceUrl.toURI();
                    Path path = Paths.get(uri);
                    
                    // Windows 우선 인코딩 시도
                    if (isWindows) {
                        try {
                            byte[] bytes = Files.readAllBytes(path);
                            String content = new String(bytes, "MS949");
                            if (content != null && !content.trim().isEmpty()) {
                                return content;
                            }
                        } catch (Exception e) {
                            // MS949 로드 실패
                        }
                    }
                    
                    // UTF-8 시도
                    try {
                        String content = Files.readString(path, StandardCharsets.UTF_8);
                        if (content != null && !content.trim().isEmpty()) {
                            return content;
                        }
                    } catch (Exception e) {
                        // UTF-8 로드 실패
                    }
                    
                    // 추가 인코딩 시도
                    String[] encodings = {"EUC-KR", "MS949", "US-ASCII"};
                    for (String encoding : encodings) {
                        try {
                            byte[] bytes = Files.readAllBytes(path);
                            String content = new String(bytes, encoding);
                            if (content != null && !content.trim().isEmpty()) {
                                return content;
                            }
                        } catch (Exception e) {
                            // 해당 인코딩 로드 실패
                        }
                    }
                } catch (Exception e) {
                    // URI 처리 실패
                }
            }
        } catch (Exception e) {
            // 클래스패스 로드 실패
        }
        
        // 모든 시도 실패 시 기본 아스키 아트 반환
        return defaultArt;
    }
    
    /**
     * ASCII 아트 파일을 로드하여 콘솔에 출력
     * 
     * @param fileName 출력할 ASCII 아트 파일 이름
     */
    public void printAsciiArt(String fileName) {
        try {
            System.out.println("디버깅: ASCII 아트 파일 출력 시도 - " + fileName);
            
            // 아스키 아트 로드 (파일 또는 기본 아스키 아트)
            String asciiArt = loadAsciiArt(fileName);
            
            // 아스키 아트가 비어있는지 확인
            if (asciiArt == null || asciiArt.trim().isEmpty()) {
                System.out.println("디버깅: 로드된 ASCII 아트가 비어있음, 기본 아트 사용");
                asciiArt = getDefaultAsciiArt(fileName);
            }
            
            // 아스키 아트 출력
            System.out.println(asciiArt);
            
        } catch (Exception e) {
            System.out.println("디버깅: ASCII 아트 출력 중 예외 발생 - " + e.getMessage());
            e.printStackTrace();
            
            // 오류 발생 시 기본 아스키 아트 출력
            String defaultArt = getDefaultAsciiArt(fileName);
            System.out.println(defaultArt);
        }
    }
    
    /**
     * 기본 ASCII 아트 생성 (로딩 실패 시 대체용)
     * 
     * @param fileName 원래 로드하려 했던 파일 이름
     * @return 기본 ASCII 아트 문자열
     */
    private String getDefaultAsciiArt(String fileName) {
        // 파일명에서 경로와 확장자 제거하여 기본 이름 추출
        String baseName = fileName;
        if (fileName.contains("/")) {
            baseName = fileName.substring(fileName.lastIndexOf('/') + 1);
        }
        if (baseName.contains(".")) {
            baseName = baseName.substring(0, baseName.lastIndexOf('.'));
        }
        
        System.out.println("디버깅: 기본 ASCII 아트 생성 - " + baseName);
        
        // 파일명에 따라 간단한 아스키 아트 반환 (표준 ASCII 문자만 사용)
        StringBuilder sb = new StringBuilder();
        sb.append("====== ").append(baseName).append(" ======\n");
        
        switch (baseName) {
            case "sun":
                sb.append("    \\   /    \n");
                sb.append("     .-.     \n");
                sb.append("  -- (   ) --\n");
                sb.append("     `-'     \n");
                sb.append("    /   \\    \n");
                break;
            case "rain":
                sb.append("     .-.     \n");
                sb.append("    (   ).   \n");
                sb.append("   (___(__)  \n");
                sb.append("    ' ' ' '  \n");
                sb.append("   ' ' ' '   \n");
                break;
            case "stone":
                sb.append("   _____     \n");
                sb.append("  /     \\    \n");
                sb.append(" |  Stone   |\n");
                sb.append("  \\_____/    \n");
                break;
            case "tree":
                sb.append("      /\\     \n");
                sb.append("     /  \\    \n");
                sb.append("    /    \\   \n");
                sb.append("   /------\\  \n");
                sb.append("      ||     \n");
                sb.append("      ||     \n");
                break;
            case "cloth":
                sb.append("  _______    \n");
                sb.append(" |       |   \n");
                sb.append(" |  Cloth |  \n");
                sb.append(" |_______|   \n");
                break;
            case "berry":
                sb.append("     __      \n");
                sb.append("    (  )     \n");
                sb.append("   (    )    \n");
                sb.append("  ( Berry )  \n");
                sb.append("   (    )    \n");
                break;
            case "ship":
                sb.append("      |\\     \n");
                sb.append("     /|\\\\    \n");
                sb.append("    /_|_\\\\   \n");
                sb.append("   |_____|   \n");
                sb.append("  \\_______/  \n");
                break;
            case "lose":
                sb.append(" Game Over   \n");
                sb.append(" ---------   \n");
                sb.append("   Failed... \n");
                break;
            case "kuma":
                sb.append("  /\\_/\\     \n");
                sb.append(" ( o.o )    \n");
                sb.append("  > ^ <     \n");
                sb.append(" Bear Alert! \n");
                break;
            default:
                sb.append("[Default ASCII Art]\n");
                break;
        }
        
        return sb.toString();
    }
} 
