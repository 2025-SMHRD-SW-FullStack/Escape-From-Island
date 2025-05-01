-- 생존 게임 프로젝트 초기 데이터 스크립트

-- 기본 업적 데이터
INSERT INTO ACHIEVEMENTS (title, description, condition, achievement_type) VALUES
('첫 생존자', '첫 게임을 시작했습니다', 'START_GAME', 'BEGINNER'), -- id 1
('탈출 성공', '섬에서 탈출했습니다', 'ESCAPE_ISLAND', 'MASTER'), -- id 2
('자원 수집가', '모든 종류의 자원을 수집했습니다', 'COLLECT_ALL_RESOURCES', 'EXPLORER'), -- id 3
('생존 전문가', '체력 80% 이상으로 탈출에 성공했습니다', 'SURVIVE_HEALTHY', 'SURVIVOR'), -- id 4

-- 테스트 계정 (개발용 - 실제 서비스에서는 제거)
-- 비밀번호는 'password'의 단순 해시값으로 가정
INSERT INTO USERS (username, password) VALUES
('test_user', '12345');

-- 테스트 사용자 통계 초기화
INSERT INTO USER_STATISTICS (user_id, games_played, victories, total_days_survived, resources_collected, items_crafted)
VALUES (1, 0, 0, 0, 0, 0); 