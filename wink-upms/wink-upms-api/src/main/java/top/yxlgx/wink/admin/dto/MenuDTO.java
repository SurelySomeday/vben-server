package top.yxlgx.wink.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import top.yxlgx.wink.admin.entity.base.BaseEntity;

import java.util.Objects;

/**
 * @author yanxin
 * @Description: 菜单
 */
@Getter
@Setter
public class MenuDTO{


    @NotNull(groups = {BaseEntity.Update.class})
    private Long menuId;

    /**
     * 菜单标题
     */
    @NotNull(groups = {BaseEntity.Create.class})
    private String title;

    /**
     * 菜单名称
     */
    @NotNull(groups = {BaseEntity.Create.class})
    private String menuName;

    /**
     * 菜单组件名称
     */
    @NotNull(groups = {BaseEntity.Create.class})
    private String componentName;

    /**
     * 排序
     */
    private Integer menuSort = 999;

    /**
     * 组件路径
     */
    private String componentPath;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单类型，1:目录 2:菜单 3:按钮
     */
    @NotNull(groups = {BaseEntity.Create.class})
    private Integer type;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否缓存
     */
    private Boolean cache;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 上级菜单
     */
    private Long pid;

    /**
     * 外链菜单
     */
    private Boolean iFrame;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuDTO menu = (MenuDTO) o;
        return Objects.equals(menuId, menu.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
