package top.yxlgx.wink.controller;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.entity.Role;
import top.yxlgx.wink.entity.base.BaseEntity;
import top.yxlgx.wink.entity.dto.RoleDTO;
import top.yxlgx.wink.entity.query.RoleQueryDTO;
import top.yxlgx.wink.repository.RoleRepository;
import top.yxlgx.wink.util.QueryHelp;
import top.yxlgx.wink.util.Result;

import java.util.List;

/**
 * @author yanxin
 * @Description:  角色管理
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    /**
     * 角色查询
     * @param pageable
     * @param roleQueryDTO
     * @return
     */
    @GetMapping
    public Result<Page<Role>> list(Pageable pageable, RoleQueryDTO roleQueryDTO){
        Page<Role>  rolePage = roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = QueryHelp.getPredicate(root, roleQueryDTO, criteriaBuilder);
            return criteriaQuery.where(predicate).getRestriction();
        }, pageable);
        return (new Result<Page<Role>>()).success(rolePage);
    }

    /**
     * 角色新增
     * @param roleDTO
     * @return
     */
    @PutMapping
    public Result<Void> save(@RequestBody @Validated({BaseEntity.Create.class}) RoleDTO roleDTO){
        Role role=new Role();
        BeanUtils.copyProperties(roleDTO,role);
        roleRepository.save(role);
        return (new Result<Void>()).success();
    }

    /**
     * 角色更新
     * @param roleDTO
     * @return
     */
    @PostMapping
    public Result<Void> update(@RequestBody @Validated({BaseEntity.Update.class}) RoleDTO roleDTO){
        Role role=new Role();
        BeanUtils.copyProperties(roleDTO,role);
        roleRepository.save(role);
        return (new Result<Void>()).success();
    }

    /**
     * 角色删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Void> delete(List<Long> ids){
        roleRepository.deleteAllById(ids);
        return (new Result<Void>()).success();
    }
}
