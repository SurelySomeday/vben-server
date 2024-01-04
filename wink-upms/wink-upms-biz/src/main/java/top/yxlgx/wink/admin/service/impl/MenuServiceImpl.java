package top.yxlgx.wink.admin.service.impl;

import org.springframework.stereotype.Service;
import top.yxlgx.wink.admin.entity.Menu;
import top.yxlgx.wink.admin.repository.MenuRepository;
import top.yxlgx.wink.common.jpa.service.BaseServiceImpl;
import top.yxlgx.wink.admin.service.MenuService;

/**
 * @author yanxin
 * @Description:
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuRepository, Menu, Long> implements MenuService {

}
