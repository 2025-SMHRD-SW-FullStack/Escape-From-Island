# Maven 프로젝트 구조 채택 이유 및 사용법

## 목차
- [Maven 소개](#maven-소개)
- [Maven 주요 장점](#maven-주요-장점)
- [Git 원격 저장소에서 Maven 프로젝트 가져오기](#git-원격-저장소에서-maven-프로젝트-가져오기)
- [이클립스에서의 Maven 사용법](#이클립스에서의-maven-사용법)
- [VS Code에서의 Maven 사용법](#vs-code에서의-maven-사용법)
- [주요 Maven 명령어](#주요-maven-명령어)
- [문제 해결 팁](#문제-해결-팁)

## Maven 소개

Maven은 Java 기반 프로젝트의 빌드, 의존성 관리, 문서화 등을 자동화하는 프로젝트 관리 도구입니다. Maven은 프로젝트의 구조와 내용을 기술하는 XML 파일인 POM(Project Object Model)을 기반으로 작동합니다. 본 프로젝트에서 Maven을 채택함으로써 표준화된 구조, 효율적인 의존성 관리, 그리고 간소화된 빌드 프로세스를 구현할 수 있었습니다.

## Maven 주요 장점

### 1. 표준화된 프로젝트 구조

Maven은 표준화된 디렉토리 구조를 제공하여 모든 개발자가 일관된 방식으로 코드를 관리할 수 있게 합니다.

```
project-root/
├── src/
│   ├── main/
│   │   ├── java/         # 소스 코드
│   │   ├── resources/    # 설정 파일, 리소스
│   │   └── webapp/       # 웹 애플리케이션 리소스
│   └── test/
│       ├── java/         # 테스트 코드
│       └── resources/    # 테스트용 리소스
├── target/               # 빌드 결과물
└── pom.xml               # Maven 설정 파일
```

### 2. 중앙화된 의존성 관리

라이브러리 의존성을 pom.xml에 선언하면 Maven이 자동으로 다운로드하고 관리합니다. 이는 개발자가 직접 라이브러리 파일을 관리하는 번거로움을 덜어줍니다.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>5.3.10</version>
    </dependency>
</dependencies>
```

### 3. 생명주기 기반 빌드 관리

Maven은 프로젝트 생명주기 개념을 도입하여 빌드 단계를 표준화합니다 :

- **validate** : 프로젝트 정보 검증
- **compile** : 소스 코드 컴파일
- **test** : 테스트 실행
- **package** : 컴파일된 코드를 JAR/WAR 파일로 패키징
- **verify** : 패키지 검증
- **install** : 로컬 저장소에 패키지 설치
- **deploy** : 원격 저장소에 패키지 배포

### 4. IDE 통합 지원

대부분의 현대적인 Java IDE(Eclipse, IntelliJ IDEA, NetBeans, VS Code)는 Maven을 기본적으로 지원합니다.

### 5. 플러그인 시스템

다양한 플러그인을 통해 추가 기능을 쉽게 확장할 수 있습니다.

## Git 원격 저장소에서 Maven 프로젝트 가져오기

### Git 저장소 클론하기

1. **명령줄에서 Git 클론** :
   ```bash
   git clone https://github.com/example/survival-game.git
   cd survival-game
   ```

2. **Git 클론 후 Maven 프로젝트 초기화** :
   ```bash
   mvn clean install
   ```
   이 명령은 필요한 의존성을 다운로드하고 프로젝트를 빌드합니다.

### 팀 프로젝트 Maven 워크플로우

1. **저장소 클론 후 처음 빌드** :
   ```bash
   git clone [repository-url]
   cd [project-folder]
   mvn clean install
   ```

2. **새로운 변경사항 가져오기 및 빌드** :
   ```bash
   git pull
   mvn clean install
   ```

3. **의존성 변경이 있을 경우** :
   - pom.xml 파일이 변경되었다면 다음 명령 실행
   ```bash
   mvn dependency:purge-local-repository
   mvn clean install
   ```

## 이클립스에서의 Maven 사용법

### Git 저장소에서 클론한 Maven 프로젝트 가져오기

1. **Git Perspective 사용** :
   - `Window > Perspective > Open Perspective > Other...` 선택
   - `Git`을 선택하고 `OK` 클릭
   - `Clone a Git repository` 클릭
   - 저장소 URL, 사용자 인증 정보 입력 후 `Next` 클릭
   - 브랜치 선택 후 `Next` 클릭
   - 로컬 저장 위치 지정 후 `Finish` 클릭

2. **클론한 Maven 프로젝트 임포트** :
   - `File > Import...` 선택
   - `Maven > Existing Maven Projects` 선택
   - 클론한 Git 저장소 위치를 루트 디렉토리로 선택
   - pom.xml 파일이 자동으로 감지됨
   - `Finish` 클릭하여 프로젝트 임포트

### POM 파일 편집

1. 이클립스의 프로젝트 탐색기에서 `pom.xml` 파일을 더블 클릭
2. 기본적으로 XML 편집기가 열리지만, 하단의 탭에서 `Overview`, `Dependencies`, `Effective POM` 등을 선택하여 다양한 방식으로 편집 가능

### 의존성 추가

1. `pom.xml` 파일 열기
2. `Dependencies` 탭 선택
3. `Add...` 버튼 클릭
4. 의존성 정보 입력 (Group Id, Artifact Id, Version) 후 `OK` 클릭

### Maven 명령어 실행

1. 프로젝트 우클릭
2. `Run As` 선택
3. Maven 명령어 선택 (`Maven install`, `Maven clean`, `Maven test` 등)

### 자주 사용하는 이클립스 Maven 단축키

- **Alt+F5** : Maven 프로젝트 업데이트
- **Alt+Shift+X, M** : Maven 빌드 실행
- **Alt+Shift+D, M** : Maven 빌드 디버그 모드 실행

## VS Code에서의 Maven 사용법

### 사전 준비

1. **Java Extension Pack 설치** :
   - Extensions 탭(Ctrl+Shift+X) 열기
   - "Java Extension Pack" 검색 후 설치

2. **Maven for Java 설치** :
   - Extensions 탭에서 "Maven for Java" 검색 후 설치

### Git 저장소에서 클론한 Maven 프로젝트 열기

1. **Git 저장소 클론** :
   - 명령 팔레트 열기 (Ctrl+Shift+P)
   - "Git: Clone" 입력 후 선택
   - 저장소 URL 입력
   - 클론할 로컬 위치 선택

2. **클론한 프로젝트 열기** :
   - `File > Open Folder...` 선택
   - 클론한 Git 저장소 폴더 선택
   - VS Code가 자동으로 pom.xml 파일을 감지하고 Maven 도구를 활성화

### Maven 패널 사용

1. VS Code 좌측 탐색기 패널에서 "MAVEN" 섹션 확장
2. 프로젝트의 POM 파일과 생명주기, 플러그인 등이 트리 구조로 표시됨
3. 각 항목을 우클릭하여 Maven 명령 실행 가능

### 터미널에서 Maven 명령어 사용

1. 터미널 열기 (Ctrl+`)
2. Maven 명령어 직접 실행 :
   ```bash
   mvn clean install
   mvn test
   mvn package
   ```

### VS Code Maven 작업 구성

1. `.vscode/tasks.json` 파일 생성 또는 편집
2. Maven 작업 구성 예시 :
   ```json
   {
     "version" : "2.0.0",
     "tasks" : [
       {
         "label" : "Maven Build",
         "type" : "shell",
         "command" : "mvn clean install",
         "group" : {
           "kind" : "build",
           "isDefault" : true
         }
       },
       {
         "label" : "Maven Test",
         "type" : "shell",
         "command" : "mvn test",
         "group" : "test"
       }
     ]
   }
   ```
3. 작업 실행 : Ctrl+Shift+B (기본 빌드 작업) 또는 명령 팔레트에서 "Tasks: Run Task" 선택

## 주요 Maven 명령어

Maven의 가장 유용한 명령어들은 다음과 같습니다 :

### 기본 명령어

- **`mvn clean`** : 빌드 결과물 삭제
- **`mvn compile`** : 소스 코드 컴파일
- **`mvn test`** : 단위 테스트 실행
- **`mvn package`** : 컴파일된 코드를 JAR/WAR 파일로 패키징
- **`mvn install`** : 패키지를 로컬 저장소에 설치
- **`mvn deploy`** : 패키지를 원격 저장소에 배포

### 복합 명령어

- **`mvn clean install`** : 이전 빌드 삭제 후 새로 빌드하여 로컬 저장소에 설치
- **`mvn clean test`** : 이전 빌드 삭제 후 소스 코드 컴파일 및 테스트 실행

### 유용한 옵션

- **`-DskipTests`** : 테스트 스킵 (예 : `mvn install -DskipTests`)
- **`-X` 또는 `--debug`** : 디버그 정보 출력
- **`-P`** : 프로필 활성화 (예 : `mvn install -Pdev`)
- **`-U`** : 스냅샷 의존성 강제 업데이트

## 문제 해결 팁

### Git 및 Maven 통합 관련 문제

1. **Git에서 받아온 프로젝트 빌드 실패** :
   ```bash
   mvn clean install -U
   ```
   이 명령은 모든 의존성을 강제로 업데이트합니다.

2. **팀원 간 의존성 충돌** :
   - pom.xml 병합 충돌 확인
   - 로컬 Maven 저장소 정리
   ```bash
   mvn dependency:purge-local-repository
   ```

3. **Git에 커밋하지 말아야 할 Maven 파일들** :
   - `/target/` 디렉토리 (빌드 결과물)
   - `.classpath`, `.project` (IDE 설정 파일)
   - `.settings/` 디렉토리 (IDE 설정)
   - 이 파일들은 `.gitignore`에 추가하여 관리

### 의존성 문제 해결

1. **의존성 트리 확인** :
   ```bash
   mvn dependency:tree
   ```

2. **의존성 충돌 분석** :
   ```bash
   mvn dependency:analyze
   ```

3. **로컬 저장소 정리** :
   ```bash
   mvn dependency:purge-local-repository
   ```

### 빌드 문제 해결

1. **메이븐 캐시 삭제** :
   - Windows : `%USERPROFILE%\.m2\repository` 폴더
   - Linux/Mac : `~/.m2/repository` 폴더

2. **강제 업데이트** :
   ```bash
   mvn clean install -U
   ```

3. **오프라인 모드에서 빌드 시도** :
   ```bash
   mvn clean install -o
   ```

### 기타 문제 해결

1. **Maven 버전 확인** :
   ```bash
   mvn --version
   ```

2. **설정 디버깅** :
   ```bash
   mvn help:effective-pom
   mvn help:effective-settings
   ```

3. **플러그인 정보 보기** :
   ```bash
   mvn help:describe -Dplugin=org.apache.maven.plugins:maven-compiler-plugin
   ```