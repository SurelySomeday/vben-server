package top.yxlgx.wink.admin.controller;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.admin.dto.DeptDTO;
import top.yxlgx.wink.admin.entity.Dept;
import top.yxlgx.wink.admin.entity.base.BaseEntity;
import top.yxlgx.wink.admin.query.DeptQueryDTO;
import top.yxlgx.wink.admin.repository.DeptRepository;
import top.yxlgx.wink.common.jpa.util.QueryHelp;
import top.yxlgx.wink.core.util.Result;

import java.util.List;

/**
 * 部门管理
 *
 * @author yanxin
 * @Description: 部门管理
 */
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DeptController {

    private final DeptRepository deptRepository;

    /**
     * 部门查询
     * @param pageable
     * @param deptQueryDTO
     * @return
     */
    @GetMapping
    public Result<Page<Dept>> list(Pageable pageable, DeptQueryDTO deptQueryDTO){
        Page<Dept> menuPage = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = QueryHelp.getPredicate(root, deptQueryDTO, criteriaQuery,criteriaBuilder);
            return criteriaQuery.where(predicate).getRestriction();
        }, pageable);
        return Result.success(menuPage);
    }

    /**
     * 部门新增
     * @param deptDTO
     * @return
     */
    @PutMapping
    public Result<Void> save(@RequestBody @Validated({BaseEntity.Create.class}) DeptDTO deptDTO){
        Dept dept=new Dept();
        BeanUtils.copyProperties(deptDTO, dept);
        deptRepository.save(dept);
        return Result.success();
    }


    /**
     * 部门更新
     * @param deptDTO
     * @return
     */
    @PostMapping
    public Result<Void> update(@RequestBody @Validated({BaseEntity.Update.class}) DeptDTO deptDTO){
        Dept dept=new Dept();
        BeanUtils.copyProperties(deptDTO, dept);
        deptRepository.save(dept);
        return Result.success();
    }

    /**
     * 部门删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Void> delete(List<Long> ids){
        deptRepository.deleteAllById(ids);
        return Result.success();
    }

}
