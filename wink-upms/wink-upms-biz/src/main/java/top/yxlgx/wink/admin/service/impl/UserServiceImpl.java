package top.yxlgx.wink.admin.service.impl;

import org.springframework.stereotype.Service;
import top.yxlgx.wink.admin.entity.Menu;
import top.yxlgx.wink.admin.entity.User;
import top.yxlgx.wink.admin.repository.MenuRepository;
import top.yxlgx.wink.admin.repository.UserRepository;
import top.yxlgx.wink.admin.service.MenuService;
import top.yxlgx.wink.admin.service.UserService;
import top.yxlgx.wink.common.jpa.service.BaseServiceImpl;

import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserRepository, User, Long> implements UserService {


    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
