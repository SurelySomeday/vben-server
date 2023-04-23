package top.yxlgx.wink.controller;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.entity.Menu;
import top.yxlgx.wink.entity.base.BaseEntity;
import top.yxlgx.wink.entity.dto.MenuDTO;
import top.yxlgx.wink.entity.query.MenuQueryDTO;
import top.yxlgx.wink.repository.MenuRepository;
import top.yxlgx.wink.util.QueryHelp;
import top.yxlgx.wink.util.Result;

import java.util.List;

/**
 * @author yanxin
 * @Description: 菜单管理
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;

    /**
     * 菜单查询
     * @param pageable
     * @param menuQueryDTO
     * @return
     */
    @GetMapping
    public Result<Page<Menu>> list(Pageable pageable, MenuQueryDTO menuQueryDTO){
                                                                                                                                  Page<Menu> menuPage = menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = QueryHelp.getPredicate(root, menuQueryDTO, criteriaBuilder);
            return criteriaQuery.where(predicate).getRestriction();
        }, pageable);
        return (new Result<Page<Menu>>()).success(menuPage);
    }


    /**
     * 菜单新增
     * @param menuDTO
     * @return
     */
    @PutMapping
    public Result<Void> save(@RequestBody @Validated({BaseEntity.Create.class}) MenuDTO menuDTO){
        Menu menu=new Menu();
        BeanUtils.copyProperties(menuDTO,menu);
        menuRepository.save(menu);
        return (new Result<Void>()).success();
    }

    /**
     * 菜单更新
     * @param menuDTO
     * @return
     */
    @PostMapping
    public Result<Void> update(@RequestBody @Validated({BaseEntity.Update.class}) MenuDTO menuDTO){
        Menu menu=new Menu();
        BeanUtils.copyProperties(menuDTO,menu);
        menuRepository.save(menu);
        return (new Result<Void>()).success();
    }

    /**
     * 菜单删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Void> delete(List<Long> ids){
       menuRepository.deleteAllById(ids);
        return (new Result<Void>()).success();
    }

}