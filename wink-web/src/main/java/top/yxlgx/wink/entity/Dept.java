package top.yxlgx.wink.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import top.yxlgx.wink.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author yanxin
 * @Description:
 */
@Getter
@Setter
@Entity
@Table(name = "sys_dept")
@NamedEntityGraph(
        name = "dept.all",
        attributeNodes =  {
                @NamedAttributeNode("children")
        }
)
public class Dept extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色
     */
    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "depts")
    private Set<Role> roles;

    /**
     * 排序
     */
    private Integer deptSort;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 上级部门
     */
    private Long pid;

    /**
     * 子节点数目
     */
    private Integer subCount = 0;

    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "pid", referencedColumnName = "id")
    private Set<Dept> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dept dept = (Dept) o;
        return Objects.equals(id, dept.id) &&
                Objects.equals(name, dept.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
