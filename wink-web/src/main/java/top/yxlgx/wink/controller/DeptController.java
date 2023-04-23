package top.yxlgx.wink.controller;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.entity.Dept;
import top.yxlgx.wink.entity.base.BaseEntity;
import top.yxlgx.wink.entity.dto.DeptDTO;
import top.yxlgx.wink.entity.query.DeptQueryDTO;
import top.yxlgx.wink.repository.DeptRepository;
import top.yxlgx.wink.util.QueryHelp;
import top.yxlgx.wink.util.Result;

import java.util.List;

/**
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
            Predicate predicate = QueryHelp.getPredicate(root, deptQueryDTO, criteriaBuilder);
            return criteriaQuery.where(predicate).getRestriction();
        }, pageable);
        return (new Result<Page<Dept>>()).success(menuPage);
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
        return (new Result<Void>()).success();
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
        return (new Result<Void>()).success();
    }

    /**
     * 部门删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Void> delete(List<Long> ids){
        deptRepository.deleteAllById(ids);
        return (new Result<Void>()).success();
    }

}
