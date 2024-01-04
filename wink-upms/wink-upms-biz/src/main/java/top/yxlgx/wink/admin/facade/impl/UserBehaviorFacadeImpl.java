package top.yxlgx.wink.admin.facade.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.yxlgx.wink.admin.entity.Role;
import top.yxlgx.wink.admin.entity.User;
import top.yxlgx.wink.admin.facade.UserBehaviorFacade;
import top.yxlgx.wink.admin.service.UserService;
import top.yxlgx.wink.admin.vo.MenuVO;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yanxin
 * @description
 */
@Service
public class UserBehaviorFacadeImpl implements UserBehaviorFacade {

    @Resource
    UserService userService;

    @Override
    public Set<MenuVO> generateMenuByUserId(Long userId) {
        Optional<User> optionalUser = userService.findById(userId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Set<Role> roles = user.getRoles();
            return roles.stream().flatMap(item-> item.getMenus().stream())
                    .map(item->{
                        MenuVO menuVO = new MenuVO();
                        menuVO.buildMeta(item);
                        return menuVO;
                    })
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }
}
