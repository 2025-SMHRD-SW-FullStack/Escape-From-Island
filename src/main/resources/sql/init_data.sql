-- 생존 게임 프로젝트 초기 데이터 스크립트

-- 기본 업적 데이터
INSERT INTO ACHIEVEMENTS (title, description, condition, achievement_type) VALUES
('첫 생존자', '첫 게임을 시작했습니다', 'START_GAME', 'BEGINNER'),
('탈출 성공', '섬에서 탈출했습니다', 'ESCAPE_ISLAND', 'MASTER'),
('자원 수집가', '모든 종류의 자원을 수집했습니다', 'COLLECT_ALL_RESOURCES', 'EXPLORER'),
('제작의 달인', '모든 아이템을 제작했습니다', 'CRAFT_ALL_ITEMS', 'CRAFTER'),
('생존 전문가', '체력 80% 이상으로 7일을 버텼습니다', 'SURVIVE_HEALTHY', 'SURVIVOR'),
('맨손 생존', '도구 없이 3일을 버텼습니다', 'SURVIVE_NO_TOOLS', 'CHALLENGE'),
('식량 비축가', '한 번에 식량 10개 이상을 보유했습니다', 'FOOD_HOARDER', 'PROVIDER'),
('장인 정신', '모든 도구를 제작했습니다', 'CRAFT_ALL_TOOLS', 'BUILDER'),
('자급자족', '하루 동안 각 자원을 5개 이상 수집했습니다', 'DAILY_COLLECTOR', 'GATHERER'),
('생존 본능', '체력이 10% 이하에서 회복했습니다', 'SURVIVE_LOW_HEALTH', 'SURVIVOR');

-- 테스트 계정 (개발용 - 실제 서비스에서는 제거)
-- 비밀번호는 'password'의 단순 해시값으로 가정
INSERT INTO USERS (username, password) VALUES
('test_user', 'e7cf3ef4f17c3999a94f2c6f612e8a888e5b1026878e4e19398b23bd38ec221a');

-- 테스트 사용자 통계 초기화
INSERT INTO USER_STATISTICS (user_id, games_played, victories, total_days_survived, resources_collected, items_crafted)
VALUES (1, 0, 0, 0, 0, 0); 