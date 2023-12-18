package top.yxlgx.wink.admin.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import top.yxlgx.wink.admin.entity.base.BaseEntity;
import top.yxlgx.wink.core.util.enums.DataScopeEnum;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author yanxin
 * @Description: 角色
 */
@Getter
@Setter
@Entity
@Table(name = "sys_role", indexes = {
        @Index(name = "sys_role_name",columnList = "name", unique = true)
}
)
@NamedEntityGraph(
        name = "role.all",
        attributeNodes =  {
                @NamedAttributeNode("users"),
                @NamedAttributeNode("menus"),
                @NamedAttributeNode("depts")
        }
)
@SQLDelete(sql = "update sys_role set deleted = 1 where role_id = ?")
@Where(clause = "deleted = 0")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")
public class Role extends BaseEntity implements Serializable {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键")
    private Long roleId;

    /**
     * 角色名称
     */
    @Comment("角色名称")
    private String name;

    /**
     * 角色值
     */
    @Comment("角色值")
    private String value;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @Comment("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope = DataScopeEnum.SELF_DEPT_AND_LOWER.getValue();

    /**
     * 描述
     */
    @Comment("描述")
    private String description;

    /**
     * 是否启用
     */
    @Comment("是否启用")
    @Column(columnDefinition = "bit(1) default 0")
    private Boolean enable;

    /**
     * 是否删除
     */
    @Comment("是否删除")
    private Integer deleted=0;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(cascade= CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinTable(name = "sys_roles_menus",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "menu_id")})
    private Set<Menu> menus;

    @JsonIgnore
    @ManyToMany(cascade= CascadeType.DETACH,mappedBy = "roles")
    private Set<Dept> depts;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}
