package top.yxlgx.wink.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yxlgx.wink.config.security.service.JwtService;
import top.yxlgx.wink.entity.User;
import top.yxlgx.wink.entity.dto.LoginDTO;
import top.yxlgx.wink.entity.vo.UserLoginResult;
import top.yxlgx.wink.repository.UserRepository;
import top.yxlgx.wink.util.Result;

import java.util.Optional;

/**
 * @author yanxin
 * @Description: 授权相关
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginResult> login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(loginDTO.getUsername());
        Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getUsername());
        User user = optionalUser.get();
        UserLoginResult userLoginResult=new UserLoginResult();
        userLoginResult.setToken(token);
        BeanUtils.copyProperties(user,userLoginResult);

        return (new Result<UserLoginResult>()).success(userLoginResult);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public Result<Void> logout(){

        return (new Result<Void>()).success();
    }
}
