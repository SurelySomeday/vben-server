package top.yxlgx.wink.admin.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.yxlgx.wink.admin.vo.UserVO;
import top.yxlgx.wink.core.util.Result;

/**
 * @author yanxin
 * @description
 */
@FeignClient(name = "remoteUserService", url = "http://127.0.0.1:7019/")
public interface RemoteUserService {

	/**
	 * 通过用户名查询用户、角色信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	@GetMapping(value = "/user/getUserByName")
	Result<UserVO> info(@RequestParam("username") String username);
}

