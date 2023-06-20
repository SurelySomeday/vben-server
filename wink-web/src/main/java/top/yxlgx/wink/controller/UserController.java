package top.yxlgx.wink.controller;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.entity.User;
import top.yxlgx.wink.entity.base.BaseEntity;
import top.yxlgx.wink.entity.dto.UserDTO;
import top.yxlgx.wink.entity.query.UserQueryDTO;
import top.yxlgx.wink.entity.vo.UserLoginResult;
import top.yxlgx.wink.repository.UserRepository;
import top.yxlgx.wink.util.QueryHelp;
import top.yxlgx.wink.util.Result;
import top.yxlgx.common.security.util.SecurityUtils;

import java.util.List;
import java.util.Optional;

/**
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
        String currentUsername = SecurityUtils.getCurrentUsername();
        Optional<User> optionalUser = userRepository.findByUsername(currentUsername);
        User user = optionalUser.get();
        UserLoginResult userLoginResult=new UserLoginResult();
        BeanUtils.copyProperties(user,userLoginResult);
        return (new Result<UserLoginResult>()).success(userLoginResult);
    }

    /**
     * 用户查询
     * @param pageable
     * @param userQueryDTO
     * @return
     */
    @GetMapping
    public Result<Page<User>> list(Pageable pageable, UserQueryDTO userQueryDTO){
        Page<User>  userPage = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = QueryHelp.getPredicate(root, userQueryDTO, criteriaBuilder);
            return criteriaQuery.where(predicate).getRestriction();
        }, pageable);
        return (new Result<Page<User>>()).success(userPage);
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
        return (new Result<Void>()).success();
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
        return (new Result<Void>()).success();
    }

    /**
     * 用户删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Void> delete(List<Long> ids){
        userRepository.deleteAllById(ids);
        return (new Result<Void>()).success();
    }
}
