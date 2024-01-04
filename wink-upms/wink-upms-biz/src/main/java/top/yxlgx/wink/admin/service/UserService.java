package top.yxlgx.wink.admin.service;

import top.yxlgx.wink.admin.entity.Menu;
import top.yxlgx.wink.admin.entity.User;
import top.yxlgx.wink.common.jpa.service.BaseService;

import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
public interface UserService extends BaseService<User, Long> {
    Optional<User> findByUsername(String username);
}
