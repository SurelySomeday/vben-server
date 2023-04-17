package top.yxlgx.wink.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import top.yxlgx.wink.entity.base.BaseEntity;
import top.yxlgx.wink.util.enums.DataScopeEnum;

import java.io.Serializable;
import java.security.Permission;
import java.util.Objects;
import java.util.Set;

/**
 * @author yanxin
 * @Description:
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
                @NamedAttributeNode("menus")
        }
)
public class Role extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 数据权限，全部 、 本级 、 自定义
     */
    private String dataScope = DataScopeEnum.THIS_LEVEL.getValue();

    /**
     * 级别，数值越小，级别越大
     */
    @Column(name = "level")
    private Integer level = 3;

    /**
     * 描述
     */
    private String description;

    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_roles_menus",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id")})
    private Set<Menu> menus;

    @JSONField(serialize = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_roles_depts",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "dept_id",referencedColumnName = "id")})
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
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
