package top.yxlgx.wink.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import top.yxlgx.wink.entity.base.BaseEntity;
import top.yxlgx.wink.util.enums.DataScopeEnum;

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
                @NamedAttributeNode("menus")
        }
)
@SQLDelete(sql = "update sys_role set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
public class Role extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("id")
    private Long id;

    /**
     * 名称
     */
    @Comment("名称")
    private String name;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @Comment("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope = DataScopeEnum.SELF_DEPT_AND_LOWER.getValue();

    /**
     * 级别，数值越小，级别越大
     */
    @Column(name = "level")
    @Comment("级别，数值越小，级别越大")
    private Integer level = 3;

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
