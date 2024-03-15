package top.yxlgx.wink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yxlgx.wink.admin.dto.MenuDTO;
import top.yxlgx.wink.admin.entity.Menu;
import top.yxlgx.wink.admin.entity.base.BaseEntity;
import top.yxlgx.wink.admin.facade.UserBehaviorFacade;
import top.yxlgx.wink.admin.query.MenuQueryDTO;
import top.yxlgx.wink.admin.service.MenuService;
import top.yxlgx.wink.admin.vo.MenuVO;
import top.yxlgx.wink.core.util.Result;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author yanxin
 * @Description: 菜单管理
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    MenuService menuService;

    @Resource
    UserBehaviorFacade userBehaviorFacade;

    @GetMapping("getMenuByUserId")
    public Result<Set<MenuVO>> getMenuByUserId(Long userId){
        return Result.success(userBehaviorFacade.generateMenuByUserId(userId));
    }


    /**
     * 菜单查询
     * @param pageable
     * @param menuQueryDTO
     * @return
     */
    @GetMapping
    public Result<Page<Menu>> list(@ParameterObject Pageable pageable, @ParameterObject MenuQueryDTO menuQueryDTO){
        return Result.success(menuService.findAll(menuQueryDTO,pageable));
    }


    /**
     * 菜单新增
     * @param menuDTO
     * @return
     */
    @PutMapping
    public Result<Void> save(@RequestBody @Validated({BaseEntity.Create.class}) MenuDTO menuDTO){
        Menu menu = BeanUtil.copyProperties(menuDTO,Menu.class);
        menuService.save(menu);
        return Result.success();
    }

    /**
     * 菜单更新
     * @param menuDTO
     * @return
     */
    @PostMapping
    public Result<Void> update(@RequestBody @Validated({BaseEntity.Update.class}) MenuDTO menuDTO){
        Menu menu = BeanUtil.copyProperties(menuDTO,Menu.class);
        menuService.save(menu);
        return Result.success();
    }

    /**
     * 菜单删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Void> delete(List<Long> ids){
       menuService.deleteAllById(ids);
        return Result.success();
    }

}
