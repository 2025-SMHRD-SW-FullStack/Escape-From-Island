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
     */
    public boolean register(String username, String password) {
        return userDAO.registerUser(username, password);
    }
    
    /**
     * 로그인 처리
     * @param username 사용자명
     * @param password 비밀번호
     * @return 로그인 성공 여부
     */
    public boolean login(String username, String password) {
    	currentUser = userDAO.authenticateUser(username, password);
        return currentUser.getUsername().equals(username);
    }
    
    /**
     * 현재 로그인한 사용자 반환
     * @return 사용자 객체
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
} 
