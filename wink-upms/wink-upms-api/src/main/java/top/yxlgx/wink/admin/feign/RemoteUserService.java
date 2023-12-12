/*
 * Copyright (c) 2020 learner4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.yxlgx.wink.admin.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.yxlgx.wink.admin.vo.UserVO;
import top.yxlgx.wink.core.util.Result;

/**
 * @author lengleng
 * @date 2019/2/1
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

