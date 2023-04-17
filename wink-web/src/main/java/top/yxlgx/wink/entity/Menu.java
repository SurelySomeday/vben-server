package top.yxlgx.wink.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import top.yxlgx.wink.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author yanxin
 * @Description: 菜单
 */
@Getter
@Setter
@Entity
@Table(name = "sys_menu")
@NamedEntityGraph(
        name = "menu.all",
        attributeNodes =  {
                @NamedAttributeNode("children"),
                @NamedAttributeNode("roles")
        }
)
public class Menu extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @Comment("主键")
    @NotNull(groups = {Update.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单标题
     */
    @Comment("菜单标题")
    private String title;

    /**
     * 菜单组件名称
     */
    @Column(name = "name")
    @Comment("菜单组件名称")
    private String componentName;

    /**
     * 排序
     */
    @Comment("排序")
    private Integer menuSort = 999;

    /**
     * 组件路径
     */
    @Comment("组件路径")
    private String component;

    /**
     * 路由地址
     */
    @Comment("路由地址")
    private String path;

    /**
     * 菜单类型，1:目录 2:菜单 3:按钮
     */
    @Comment("菜单类型，1:目录 2:菜单 3:按钮")
    private Integer type;

    /**
     * 权限标识
     */
    @Comment("权限标识")
    private String permission;

    /**
     * 菜单图标
     */
    @Comment("菜单图标")
    private String icon;

    /**
     * 是否缓存
     */
    @Column(columnDefinition = "bit(1) default 0")
    @Comment("是否缓存")
    private Boolean cache;

    /**
     * 是否隐藏
     */
    @Column(columnDefinition = "bit(1) default 0")
    @Comment("是否隐藏")
    private Boolean hidden;

    /**
     * 上级菜单
     */
    @Comment("上级菜单")
    private Long pid;

    /**
     * 子节点数目
     */
    @Comment("子节点数目")
    private Integer subCount = 0;

    /**
     * 外链菜单
     */
    @Comment("外链菜单")
    private Boolean iFrame;

    /**
     * 子菜单列表
     */
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "pid", referencedColumnName = "id")
    private Set<Menu> children;

    /**
     * 拥有菜单的角色
     */
    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
