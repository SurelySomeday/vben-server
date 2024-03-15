package top.yxlgx.wink.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.admin.dto.UserDTO;
import top.yxlgx.wink.admin.entity.Role;
import top.yxlgx.wink.admin.entity.User;
import top.yxlgx.wink.admin.entity.base.BaseEntity;
import top.yxlgx.wink.admin.query.UserQueryDTO;
import top.yxlgx.wink.admin.repository.UserRepository;
import top.yxlgx.wink.admin.service.UserService;
import top.yxlgx.wink.admin.vo.UserLoginResult;
import top.yxlgx.wink.admin.vo.UserVO;
import top.yxlgx.wink.common.jpa.util.QueryHelp;
import top.yxlgx.wink.core.util.Result;

import java.util.List;
import java.util.Optional;

/**
 * 用户管理
 *
 * @author yanxin
 * @Description:  用户管理
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result<UserLoginResult> getUserInfo(){
        long loginId = StpUtil.getLoginIdAsLong();
        Optional<User> optionalUser = userService.findById(loginId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserLoginResult userLoginResult = new UserLoginResult();
            BeanUtils.copyProperties(user, userLoginResult);
            return Result.success(userLoginResult);
        }
        return Result.failed();
    }

    /**
     * 获取用户信息
     * @return
     */
    @SaIgnore
    @GetMapping("/getUserByName")
    public Result<UserVO> getUserInfo(@RequestParam("username") String username){
        Optional<User> optionalUser = userService.findByUsername(username);
        return optionalUser.map(user -> Result.success(BeanUtil.copyProperties(user, UserVO.class))).orElseGet(() -> Result.success(null));
    }

    /**
     * 用户查询
     * @param pageable
     * @param userQueryDTO
     * @return
     */
    @SaIgnore
    @GetMapping
    public Result<Page<User>> list(@ParameterObject Pageable pageable, @ParameterObject UserQueryDTO userQueryDTO){
        return Result.success(userService.findAll(userQueryDTO,pageable));
    }

    /**
     * 用户新增
     * @param userDTO
     * @return
     */
    @PutMapping
    public Result<Void> save(@RequestBody @Validated({BaseEntity.Create.class}) UserDTO userDTO){
        User user = BeanUtil.copyProperties(userDTO, User.class);
        userService.save(user);
        return Result.success();
    }

    /**
     * 用户更新
     * @param userDTO
     * @return
     */
    @PostMapping
    public Result<Void> update(@RequestBody @Validated({BaseEntity.Update.class}) UserDTO userDTO){
        User user = BeanUtil.copyProperties(userDTO, User.class);
        userService.save(user);
        return Result.success();
    }

    /**
     * 用户删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Void> delete(List<Long> ids){
        userService.deleteAllById(ids);
        return Result.success();
    }
}
