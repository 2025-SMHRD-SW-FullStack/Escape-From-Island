package survival.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 파일 인코딩 변환 유틸리티
 */
public class FileEncodingConverter {
    
    /**
     * 디렉터리 내 모든 .txt 파일을 MS949로 변환
     * 
     * @param directoryPath 변환할 디렉터리 경로
     * @return 성공적으로 변환된 파일 수
     * @throws IOException 파일 처리 중 오류 발생 시
     */
    public static int convertDirectoryToMS949(String directoryPath) throws IOException {
        int convertedCount = 0;
        File dir = new File(directoryPath);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("유효한 디렉터리가 아닙니다: " + directoryPath);
            return 0;
        }
        
        System.out.println("디렉터리 내 파일 변환 중: " + directoryPath);
        
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("변환할 .txt 파일이 없습니다.");
            return 0;
        }
        
        for (File file : files) {
            if (convertFileToMS949(file.getPath())) {
                convertedCount++;
            }
        }
        
        System.out.printf("총 %d개 파일 변환 완료\n", convertedCount);
        return convertedCount;
    }
    
    /**
     * 단일 파일을 MS949로 변환
     * 
     * @param filePath 변환할 파일 경로
     * @return 변환 성공 여부
     */
    public static boolean convertFileToMS949(String filePath) {
        try {
            System.out.println("파일 변환 중: " + filePath);
            
            // 먼저 UTF-8로 읽기 시도
            String content = readFileWithEncoding(filePath, StandardCharsets.UTF_8);
            if (content == null) {
                // UTF-8 실패 시 다른 인코딩 시도
                content = readFileWithEncoding(filePath, Charset.forName("EUC-KR"));
            }
            
            if (content == null) {
                System.err.println("파일 읽기 실패: " + filePath);
                return false;
            }
            
            // 백업 파일 생성
            String backupPath = filePath + ".bak";
            Files.copy(Paths.get(filePath), Paths.get(backupPath));
            System.out.println("백업 파일 생성: " + backupPath);
            
            // MS949로 다시 저장
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(content.getBytes("MS949"));
            }
            
            System.out.println("파일 변환 성공: " + filePath);
            return true;
            
        } catch (Exception e) {
            System.err.println("파일 변환 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 특정 인코딩으로 파일 읽기 시도
     * 
     * @param filePath 읽을 파일 경로
     * @param charset 사용할 인코딩
     * @return 파일 내용 또는 실패 시 null
     */
    private static String readFileWithEncoding(String filePath, Charset charset) {
        try {
            Path path = Paths.get(filePath);
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, charset);
        } catch (Exception e) {
            System.out.println("인코딩으로 읽기 실패: " + charset.displayName());
            return null;
        }
    }
    
    /**
     * 콘솔에서 직접 실행 가능한 메인 메서드
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("사용법: java FileEncodingConverter <디렉터리 또는 파일 경로>");
            return;
        }
        
        String path = args[0];
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                convertDirectoryToMS949(path);
            } else {
                convertFileToMS949(path);
            }
        } catch (Exception e) {
            System.err.println("오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 