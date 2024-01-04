package top.yxlgx.wink.admin.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.yxlgx.wink.admin.vo.MenuVO;
import top.yxlgx.wink.core.util.Result;

import java.util.Set;

/**
 * @author yanxin
 * @description
 */
@FeignClient(name = "remoteMenuService", url = "http://127.0.0.1:7019/")
public interface RemoteMenuService {

	/**
	 * 通过用户id获取用户菜单
	 *
	 * @param userId 用户id
	 * @return R
	 */
	@GetMapping(value = "/menu/getMenuByUserId")
	Result<Set<MenuVO>> getMenuByUserId(@RequestParam("userId") Long userId);
}

