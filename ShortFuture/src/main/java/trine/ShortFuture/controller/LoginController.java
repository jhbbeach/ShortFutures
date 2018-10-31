package trine.ShortFuture.controller;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "login")
public class LoginController {
	
    @ResponseBody
    @PostMapping(path = "login")
    public String login(String userNo,String password) throws IOException {
        UsernamePasswordToken token = new UsernamePasswordToken(userNo, password,"");
        SecurityUtils.getSubject().login(token);
    	return "success";
    }
}