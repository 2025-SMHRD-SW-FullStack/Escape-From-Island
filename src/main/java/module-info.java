module survival {
    // Java 표준 모듈
    requires java.sql;
    requires java.desktop;
    
    // 모든 패키지 공개
    exports survival;
    opens survival;
} 