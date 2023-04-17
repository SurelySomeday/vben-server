package top.yxlgx.wink.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import top.yxlgx.wink.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author yanxin
 * @Description: 部门
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
    @Comment("主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 排序
     */
    @Comment("排序")
    private Integer deptSort;

    /**
     * 部门名称
     */
    @Comment("部门名称")
    private String name;

    /**
     * 是否启用
     */
    @Comment("是否启用")
    private Boolean enabled;

    /**
     * 上级部门
     */
    @Comment("上级部门")
    private Long pid;

    /**
     * 子节点数目
     */
    @Comment("子节点数目")
    private Integer subCount = 0;

    /**
     * 是否删除
     */
    @Comment("是否删除")
    private Integer deleted=0;

    /**
     * 子部门列表
     */
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "pid", referencedColumnName = "id")
    private Set<Dept> children;

    /**
     * 角色
     */
    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "depts")
    private Set<Role> roles;

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
