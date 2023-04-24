package top.yxlgx.wink.controller;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.config.security.service.JwtService;
import top.yxlgx.wink.entity.Menu;
import top.yxlgx.wink.entity.User;
import top.yxlgx.wink.entity.dto.LoginDTO;
import top.yxlgx.wink.entity.vo.UserLoginResult;
import top.yxlgx.wink.repository.UserRepository;
import top.yxlgx.wink.util.QueryHelp;
import top.yxlgx.wink.util.Result;
import top.yxlgx.wink.util.SecurityUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
     * 获取用户菜单
     * @return
     */
    @GetMapping("/getMenuList")
    public Result<Set<Menu>> getMenuList(){
        String currentUsername = SecurityUtils.getCurrentUsername();
        Optional<User> optionalUser = userRepository.findByUsername(currentUsername);
        User user = optionalUser.get();
        Set<Menu> menuSet=new HashSet<>();
        user.getRoles().stream().forEach(item->menuSet.addAll(item.getMenus()));
        return (new Result<Set<Menu>>()).success(menuSet);
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
