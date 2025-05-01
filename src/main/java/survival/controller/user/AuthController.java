package survival.controller.user;

import survival.model.user.User;
import survival.dao.UserDAO;

/**
 * 인증 관련 기능을 처리하는 컨트롤러 클래스
 */
public class AuthController {
    // 필드
    private UserDAO userDAO;
    private User currentUser;
    
    /**
     * 기본 생성자
     */
    public AuthController() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * 회원가입 처리
     * @param username 사용자명
     * @param password 비밀번호
     * @return 회원가입 성공 여부
     * @throws IllegalArgumentException 이미 존재하는 사용자명인 경우
     */
    public boolean register(String username, String password) throws IllegalArgumentException {
        // 사용자명 중복 확인
        if (userDAO.checkUserExists(username)) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }
    	
        return userDAO.registerUser(username, password);
    }
    
    /**
     * 로그인 처리
     * @param username 사용자명
     * @param password 비밀번호
     * @return 로그인 성공 여부
     * @throws IllegalArgumentException 존재하지 않는 계정이거나 비밀번호가 일치하지 않는 경우
     */
    public boolean login(String username, String password) throws IllegalArgumentException {
    	currentUser = userDAO.authenticateUser(username, password);
        
        // 사용자가 존재하지 않는 경우
        if (currentUser == null) {
            throw new IllegalArgumentException("사용자명 또는 비밀번호가 일치하지 않습니다.");
        }
        
        return true;
    }
    
    /**
     * 현재 로그인한 사용자 반환
     * @return 사용자 객체
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
} 
