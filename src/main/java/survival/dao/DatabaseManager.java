package survival.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 데이터베이스 연결 관리를 위한 싱글톤 클래스
 */
public class DatabaseManager {
    // 필드
    private Connection connection;
    private static DatabaseManager instance;
    private String url;
    private String user;
    private String password;
    private String driver;

    /**
     * 기본 생성자 싱글톤 패턴이니 private으로
     */
    private DatabaseManager() {
        loadConfig();
    }
    
    /**
     * 싱글톤 인스턴스 반환
     * @return DatabaseManager 인스턴스
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /**
     * 데이터베이스 연결 반환
     * @return DB 연결 객체
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException e) {
                    System.err.println("Oracle JDBC 드라이버를 찾을 수 없습니다: " + e.getMessage());
                    e.printStackTrace();
                }
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            System.err.println("데이터베이스 연결 오류: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * 데이터베이스 초기화
     * @return 초기화 성공 여부
     */
    public boolean initDatabase() {
        Connection conn = null;
        try {
            // 데이터베이스 연결 시도
            conn = getConnection();
            
            // 연결이 null이면 초기화 실패
            if (conn == null) {
                System.err.println("데이터베이스 연결 실패: 초기화할 수 없습니다.");
                return false;
            }
            
            // 여기에 테이블 생성 등의 초기화 로직 추가 가능
            // SQL 스크립트 실행 또는 직접 쿼리 실행
            
            System.out.println("데이터베이스 초기화 완료");
            return true;
            
        } catch (Exception e) {
            System.err.println("데이터베이스 초기화 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 설정 파일 로드
     */
    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("config.properties 파일을 찾을 수 없습니다.");
                // Oracle 기본값 설정
                driver = "oracle.jdbc.driver.OracleDriver";
                url = "jdbc:oracle:thin:@project-db-campus.smhrd.com:1524:XE";
                user = "campus_25SW_FS_p1_6";
                password = "smhrd6";
                return;
            }
            
            Properties prop = new Properties();
            prop.load(input);
            
            driver = prop.getProperty("db.driver");
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 데이터베이스 연결 종료
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 
