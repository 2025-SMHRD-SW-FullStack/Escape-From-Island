-- MySQL 데이터베이스 스키마 생성 스크립트
-- 생존 게임 프로젝트를 위한 테이블 구조

-- 사용자 테이블
CREATE TABLE IF NOT EXISTS USERS (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- 업적 테이블
CREATE TABLE IF NOT EXISTS ACHIEVEMENTS (
    achievement_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    condition VARCHAR(100) NOT NULL,
    achievement_type VARCHAR(50) NOT NULL
);

-- 사용자-업적 매핑 테이블
CREATE TABLE IF NOT EXISTS USER_ACHIEVEMENTS (
    user_id INT NOT NULL,
    achievement_id INT NOT NULL,
    achieved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, achievement_id),
    FOREIGN KEY (user_id) REFERENCES USERS(user_id),
    FOREIGN KEY (achievement_id) REFERENCES ACHIEVEMENTS(achievement_id)
);

-- 통계 테이블
CREATE TABLE IF NOT EXISTS USER_STATISTICS (
    user_id INT PRIMARY KEY,
    games_played INT DEFAULT 0,
    victories INT DEFAULT 0,
    total_days_survived INT DEFAULT 0,
    resources_collected INT DEFAULT 0,
    items_crafted INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES USERS(user_id)
); 