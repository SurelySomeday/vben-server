package top.yxlgx.wink.admin.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import top.yxlgx.wink.admin.entity.base.BaseEntity;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "menuId")
public class Menu extends BaseEntity implements Serializable {

    @Id
    @Comment("主键")
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /**
     * 菜单标题
     */
    @Comment("菜单标题")
    private String title;

    /**
     * 菜单名称
     */
    @Comment("菜单名称")
    private String menuName;

    /**
     * 菜单组件名称
     */
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
    private String componentPath;

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
     * 是否外链
     */
    @Comment("是否外链")
    private Boolean isExt;

    /**
     * 是否缓存
     */
    @Comment("是否缓存")
    private Boolean keepalive;

    /**
     * 是否启用
     */
    @Comment("是否启用")
    private Boolean status;

    /**
     * 子菜单列表
     */
    @OneToMany(cascade= CascadeType.DETACH,fetch=FetchType.EAGER)
    @JoinColumn(name = "pid", referencedColumnName = "menu_id")
    private Set<Menu> children;

    /**
     * 拥有菜单的角色
     */
    @JsonIgnore
    @ManyToMany(cascade= CascadeType.DETACH,mappedBy = "menus")
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
        return Objects.equals(menuId, menu.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
