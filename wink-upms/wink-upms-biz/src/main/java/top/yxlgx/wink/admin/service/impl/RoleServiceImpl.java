package top.yxlgx.wink.admin.service.impl;

import org.springframework.stereotype.Service;
import top.yxlgx.wink.admin.entity.Role;
import top.yxlgx.wink.admin.entity.User;
import top.yxlgx.wink.admin.repository.RoleRepository;
import top.yxlgx.wink.admin.repository.UserRepository;
import top.yxlgx.wink.admin.service.RoleService;
import top.yxlgx.wink.admin.service.UserService;
import top.yxlgx.wink.common.jpa.service.BaseServiceImpl;

import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleRepository, Role, Long> implements RoleService {

}
