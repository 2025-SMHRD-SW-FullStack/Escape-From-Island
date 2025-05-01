# 무인도 탈출 게임

## 소개
이 프로젝트는 무인도에 불시착한 주인공이 생존하고 탈출하는 과정을 담은 게임입니다.

## 개발 환경
- Java JDK 17
- JavaFX 11
- Maven

## 프로젝트 구조
- `src/main/java/survival`: 소스코드
- `src/main/resources`: 리소스 파일 (이미지, 오디오, CSS 등)

## 게임 실행 방법

### 방법 1: Maven 명령어 사용 (개발 중)
1. 프로젝트 루트 디렉토리에서 아래 명령어 실행:
   ```bash
   mvn clean javafx:run
   ```

### 방법 2: 빌드된 EXE 실행 (Windows)
1. 아래 '빌드 및 패키징' 방법으로 EXE 파일을 생성합니다.
2. `target/dist/무인도탈출` 디렉토리의 `무인도탈출.exe` 파일을 실행합니다.

### 방법 3: IDE에서 실행
1. 프로젝트를 IDE(IntelliJ, Eclipse 등)에서 열기
2. Main 클래스(`src/main/java/survival/Main.java`)를 실행

## 게임 음악
게임 내 배경음악과 효과음을 위해 다음 파일들이 필요합니다:

- `src/main/resources/audio/menu_bgm.mp3`: 메인 메뉴 배경음악
- `src/main/resources/audio/game_bgm.mp3`: 게임 플레이 배경음악
- `src/main/resources/audio/victory_bgm.mp3`: 승리 시 배경음악
- `src/main/resources/audio/defeat_bgm.mp3`: 패배 시 배경음악

## 빌드 및 패키징 (EXE 생성)
프로젝트를 Windows용 독립 실행형 EXE 파일로 빌드하려면:

1. 프로젝트 루트 디렉토리에서 아래 Maven 명령어 실행:
   ```bash
   mvn clean package
   ```
2. 빌드가 성공하면 `target/dist` 디렉토리에 `무인도탈출` 폴더가 생성되고, 그 안에 `무인도탈출.exe` 파일 및 관련 파일들이 포함됩니다.

## 주의사항
- Java 17 JDK가 설치되어 있고, `jpackage` 도구가 환경 변수에 설정되어 있어야 합니다.
- 오라클 데이터베이스 연결을 위한 설정이 필요합니다.
- `pom.xml`의 `jpackage-maven-plugin` 설정에서 `<winUpgradeUuid>`에 고유한 GUID 값을 설정해야 합니다.

