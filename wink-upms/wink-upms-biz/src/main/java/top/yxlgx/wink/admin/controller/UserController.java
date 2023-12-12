package top.yxlgx.wink.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.admin.dto.UserDTO;
import top.yxlgx.wink.admin.entity.User;
import top.yxlgx.wink.admin.entity.base.BaseEntity;
import top.yxlgx.wink.admin.query.UserQueryDTO;
import top.yxlgx.wink.admin.repository.UserRepository;
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

    private final UserRepository userRepository;

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result<UserLoginResult> getUserInfo(){
        String currentUsername = "";
        Optional<User> optionalUser = userRepository.findByUsername(currentUsername);
        User user = optionalUser.get();
        UserLoginResult userLoginResult=new UserLoginResult();
        BeanUtils.copyProperties(user,userLoginResult);
        return Result.success(userLoginResult);
    }

    /**
     * 获取用户信息
     * @return
     */
    @SaIgnore
    @GetMapping("/getUserByName")
    public Result<UserVO> getUserInfo(@RequestParam("username") String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
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
    public Result<Page<User>> list(Pageable pageable, UserQueryDTO userQueryDTO){
        Page<User>  userPage = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = QueryHelp.getPredicate(root, userQueryDTO,criteriaQuery, criteriaBuilder);
            return criteriaQuery.where(predicate).getRestriction();
        }, pageable);
        return Result.success(userPage);
    }

    /**
     * 用户新增
     * @param userDTO
     * @return
     */
    @PutMapping
    public Result<Void> save(@RequestBody @Validated({BaseEntity.Create.class}) UserDTO userDTO){
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        userRepository.save(user);
        return Result.success();
    }

    /**
     * 用户更新
     * @param userDTO
     * @return
     */
    @PostMapping
    public Result<Void> update(@RequestBody @Validated({BaseEntity.Update.class}) UserDTO userDTO){
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        userRepository.save(user);
        return Result.success();
    }

    /**
     * 用户删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Void> delete(List<Long> ids){
        userRepository.deleteAllById(ids);
        return Result.success();
    }
}
