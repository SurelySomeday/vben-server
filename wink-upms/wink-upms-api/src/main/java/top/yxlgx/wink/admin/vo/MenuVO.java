package top.yxlgx.wink.admin.vo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import top.yxlgx.wink.admin.entity.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author yanxin
 * @date 2023/6/16
 */
@Data
public class MenuVO {

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 元信息
     */
    private Map<String,Object> meta;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单组件名称
     */
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
     * 子节点数目
     */
    private Integer subCount = 0;

    /**
     * 外链菜单
     */
    private Boolean iFrame;


    /**
     * 是否外链
     */
    private Boolean isExt;

    /**
     * 是否缓存
     */
    private Boolean keepalive;

    /**
     * 是否启用
     */
    private Boolean status;

    /**
     * 子菜单列表
     */
    private Set<Menu> children;


    /**
     * 原始菜单数据构造
     * @param menu
     */
    public void buildMeta(Menu menu){
        if(this.meta == null){
            this.meta = new HashMap<>();
        }
        this.meta.put("icon", menu.getIcon());
        this.meta.put("path", menu.getPath());
        this.meta.put("type", menu.getType());
        this.meta.put("permission", menu.getPermission());
        this.meta.put("label", menu.getMenuName());
        this.meta.put("sortOrder", menu.getMenuSort());
        this.meta.put("keepAlive", menu.getKeepalive());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuVO menuVO = (MenuVO) o;
        return Objects.equals(menuId, menuVO.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
