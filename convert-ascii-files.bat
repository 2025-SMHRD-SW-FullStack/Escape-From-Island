@echo off
echo ===== 아스키 아트 파일 인코딩 변환 도구 =====
echo.

REM 소스 클래스 먼저 컴파일
echo 파일 변환 유틸리티 클래스 컴파일 중...
javac -d target/classes -cp target/classes src/main/java/survival/util/FileEncodingConverter.java
if %ERRORLEVEL% NEQ 0 (
    echo 컴파일 실패! 엔터 키를 눌러 종료...
    pause
    exit /b %ERRORLEVEL%
)

echo 컴파일 성공!
echo.

REM 아스키 아트 파일 디렉터리 경로
set ASCII_DIR=src/main/resources/ascii

echo %ASCII_DIR% 디렉터리의 모든 아스키 아트 파일을 MS949로 변환합니다.
echo 경고: 기존 파일은 .bak 확장자로 백업됩니다.
echo.

echo 변환을 시작하려면 엔터 키를 누르세요...
pause > nul

REM 파일 변환 유틸리티 실행
java -cp target/classes survival.util.FileEncodingConverter %ASCII_DIR%

echo.
echo 변환 완료! 엔터 키를 눌러 종료...
pause 