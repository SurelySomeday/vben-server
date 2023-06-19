package top.yxlgx.wink.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.config.security.service.JwtService;
import top.yxlgx.wink.entity.Menu;
import top.yxlgx.wink.entity.User;
import top.yxlgx.wink.entity.dto.LoginDTO;
import top.yxlgx.wink.entity.vo.RouteItemVO;
import top.yxlgx.wink.entity.vo.RouteMetaVO;
import top.yxlgx.wink.entity.vo.UserLoginResult;
import top.yxlgx.wink.repository.UserRepository;
import top.yxlgx.wink.util.QueryHelp;
import top.yxlgx.wink.util.Result;
import top.yxlgx.wink.util.SecurityUtils;

import java.util.*;
import java.util.stream.Collectors;

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
     * 获取用户路由
     * @return
     */
    @GetMapping("/getRoute")
    public Result<List<RouteItemVO>> getRoute(){
        String currentUsername = SecurityUtils.getCurrentUsername();
        Optional<User> optionalUser = userRepository.findByUsername(currentUsername);
        User user = optionalUser.get();
        Set<Menu> menuSet=new HashSet<>();
        user.getRoles().stream().forEach(item->menuSet.addAll(item.getMenus()));
        List<RouteItemVO> routeItemVOList = menuSet.stream()
                .filter(item -> item.getPid().intValue() == 0)
                .sorted(Comparator.comparing(Menu::getMenuSort))
                .map(item -> {
                    RouteItemVO node = convertToRoute(item);
                    node.setChildren(getChildrenList(item, menuSet));
                    return node;
                }).collect(Collectors.toList());
        return (new Result<List<RouteItemVO>>()).success(routeItemVOList);
    }



    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public Result<Void> logout(){

        return (new Result<Void>()).success();
    }

    private RouteItemVO convertToRoute(Menu item){
        RouteItemVO node = new RouteItemVO();
        node.setName(StringUtils.capitalize(item.getPath()));
        node.setPath(item.getComponentPath());
        node.setComponent(item.getComponentName());
        // 一级目录
        if (Objects.equals(item.getType(), 0) && item.getPid().intValue() == 0) {
            node.setPath("/" + item.getPath());
            node.setComponent("LAYOUT");
        }
        // 外部链接
        if (Objects.equals(item.getType(), 1) && Objects.equals(item.getIsExt(), true)) {
            node.setComponent("IFrame");
        }
        RouteMetaVO routeMetaVO = new RouteMetaVO();
        routeMetaVO.setTitle(item.getMenuName());
        routeMetaVO.setIcon(item.getIcon());
        routeMetaVO.setHideMenu(item.getHidden()!=null && item.getHidden().equals(true));
        // 菜单
        if (Objects.equals(item.getType(), 0)) {
            routeMetaVO.setIgnoreKeepAlive(item.getKeepalive().equals(true));
        }
        // 外部链接
        if (Objects.equals(item.getType(), 1) && Objects.equals(item.getIFrame(), true)) {
            // 内嵌
            if (Objects.equals(item.getIFrame(), true)) {
                routeMetaVO.setFrameSrc(item.getPath());
            }
            // 外嵌
            if (item.getIFrame().equals(false)) {
                node.setPath(item.getPath());
            }
        }
        node.setMeta(routeMetaVO);
        return node;
    }

    private List<RouteItemVO> getChildrenList(Menu root, Set<Menu> list) {
        List<RouteItemVO> childrenList = list.stream()
                .filter(item -> item.getPid().equals(root.getMenuId()))
                .sorted(Comparator.comparing(Menu::getMenuSort))
                .map(item -> {
                    RouteItemVO node = convertToRoute(item);
                    node.setChildren(getChildrenList(item, list));
                    return node;
                }).collect(Collectors.toList());
        return childrenList;
    }
}
