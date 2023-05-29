package swu.musling.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //로그인 api
    @PostMapping("/users/login")
    void createDiary(@RequestBody LoginVo loginVo) {    //JSON 형식으로 요청
        loginService.login(loginVo);
    }
}
