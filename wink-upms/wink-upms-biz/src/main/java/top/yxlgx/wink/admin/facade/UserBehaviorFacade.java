package top.yxlgx.wink.admin.facade;

import top.yxlgx.wink.admin.vo.MenuVO;

import java.util.Set;

/**
 * @author yanxin
 * @description
 */
public interface UserBehaviorFacade {

    Set<MenuVO> generateMenuByUserId(Long userId);

}
