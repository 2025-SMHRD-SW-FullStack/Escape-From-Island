-- 시퀀스 생성
CREATE SEQUENCE USER_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE SEQUENCE ACHIEVEMENT_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- 사용자 테이블
CREATE TABLE USERS (
    user_id NUMBER(10) PRIMARY KEY,
    username VARCHAR2(50) NOT NULL UNIQUE,
    password VARCHAR2(100) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    last_login TIMESTAMP DEFAULT SYSTIMESTAMP
);

-- 업적 테이블
CREATE TABLE ACHIEVEMENTS (
    achievement_id NUMBER(10) PRIMARY KEY,
    title VARCHAR2(100) NOT NULL,
    description VARCHAR2(255) NOT NULL,
    condition VARCHAR2(100) NOT NULL,
    achievement_type VARCHAR2(50) NOT NULL
);

-- 사용자-업적 매핑 테이블
CREATE TABLE USER_ACHIEVEMENTS (
    user_id NUMBER(10) NOT NULL,
    achievement_id NUMBER(10) NOT NULL,
    achieved_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (user_id, achievement_id),
    CONSTRAINT fk_user_ach FOREIGN KEY (user_id) REFERENCES USERS(user_id),
    CONSTRAINT fk_ach_user FOREIGN KEY (achievement_id) REFERENCES ACHIEVEMENTS(achievement_id)
);

-- 트리거 생성 (자동 증가 기능 구현)
CREATE OR REPLACE TRIGGER USER_BI_TRG
BEFORE INSERT ON USERS
FOR EACH ROW
BEGIN
    SELECT USER_SEQ.NEXTVAL INTO :NEW.user_id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER ACHIEVEMENT_BI_TRG
BEFORE INSERT ON ACHIEVEMENTS
FOR EACH ROW
BEGIN
    SELECT ACHIEVEMENT_SEQ.NEXTVAL INTO :NEW.achievement_id FROM DUAL;
END;
/

-- 기본 업적 데이터
INSERT INTO ACHIEVEMENTS (title, description, condition, achievement_type) VALUES
('첫 생존자', '첫 게임을 시작했습니다', 'START_GAME', 'BEGINNER');
INSERT INTO ACHIEVEMENTS (title, description, condition, achievement_type) VALUES
('탈출 성공', '섬에서 탈출했습니다', 'ESCAPE_ISLAND', 'MASTER');
INSERT INTO ACHIEVEMENTS (title, description, condition, achievement_type) VALUES
('자원 수집가', '모든 종류의 자원을 수집했습니다', 'COLLECT_ALL_RESOURCES', 'EXPLORER');
INSERT INTO ACHIEVEMENTS (title, description, condition, achievement_type) VALUES
('제작의 달인', '모든 아이템을 제작했습니다', 'CRAFT_ALL_ITEMS', 'CRAFTER');
INSERT INTO ACHIEVEMENTS (title, description, condition, achievement_type) VALUES
('생존 전문가', '체력 80% 이상으로 7일을 버텼습니다', 'SURVIVE_HEALTHY', 'SURVIVOR');