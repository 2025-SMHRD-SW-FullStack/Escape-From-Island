-- 생존 게임 자주 사용되는 SQL 쿼리 모음

-- 1. 사용자 인증 관련 쿼리
-- 로그인 인증
SELECT user_id, username, password FROM USERS WHERE username = ?;

-- 사용자 등록
INSERT INTO USERS (username, password) VALUES (?, ?);

-- 마지막 로그인 시간 업데이트
UPDATE USERS SET last_login = CURRENT_TIMESTAMP WHERE user_id = ?;

-- 2. 업적 관련 쿼리
-- 모든 업적 조회
SELECT * FROM ACHIEVEMENTS;

-- 사용자의 업적 조회
SELECT a.* FROM ACHIEVEMENTS a
JOIN USER_ACHIEVEMENTS ua ON a.achievement_id = ua.achievement_id
WHERE ua.user_id = ?;

-- 업적 달성 기록
INSERT INTO USER_ACHIEVEMENTS (user_id, achievement_id) VALUES (?, ?);

-- 특정 조건의 업적 확인
SELECT achievement_id FROM ACHIEVEMENTS WHERE condition = ?;

-- 3. 통계 관련 쿼리
-- 사용자 통계 생성
INSERT INTO USER_STATISTICS (user_id) VALUES (?);

-- 통계 업데이트 (게임 플레이)
UPDATE USER_STATISTICS 
SET games_played = games_played + 1 
WHERE user_id = ?;

-- 승리 시 통계 업데이트
UPDATE USER_STATISTICS 
SET victories = victories + 1,
    total_days_survived = total_days_survived + ?
WHERE user_id = ?;

-- 생존 일수 업데이트
UPDATE USER_STATISTICS 
SET total_days_survived = total_days_survived + ? 
WHERE user_id = ?;

-- 수집한 자원 업데이트
UPDATE USER_STATISTICS 
SET resources_collected = resources_collected + ? 
WHERE user_id = ?;

-- 제작한 아이템 업데이트
UPDATE USER_STATISTICS 
SET items_crafted = items_crafted + 1 
WHERE user_id = ?;

-- 4. 기타 유용한 쿼리
-- 모든 플레이어 랭킹 (승리 횟수 기준)
SELECT u.username, s.victories, s.games_played, s.total_days_survived
FROM USERS u
JOIN USER_STATISTICS s ON u.user_id = s.user_id
ORDER BY s.victories DESC, s.total_days_survived DESC; 