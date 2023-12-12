package top.yxlgx.wink.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import top.yxlgx.wink.admin.entity.base.BaseEntity;

import java.util.Objects;

/**
 * @author yanxin
 * @Description: 部门
 */
@Getter
@Setter
public class DeptDTO {

    @NotNull(groups = BaseEntity.Update.class)
    private Long deptId;
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
     * 是否删除
     */
    private Integer deleted=0;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeptDTO dept = (DeptDTO) o;
        return Objects.equals(deptId, dept.deptId) &&
                Objects.equals(name, dept.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId, name);
    }
}
