package top.yxlgx.wink.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import top.yxlgx.wink.admin.entity.base.BaseEntity;
import top.yxlgx.wink.core.util.enums.DataScopeEnum;

import java.util.Objects;

/**
 * @author yanxin
 * @Description: 角色
 */
@Getter
@Setter
public class RoleDTO {

    @NotNull(groups = {BaseEntity.Update.class})
    private Long roleId;

    /**
     * 角色名称
     */
    @NotNull(groups = {BaseEntity.Create.class})
    private String name;

    /**
     * 角色值
     */
    private String value;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private String dataScope = DataScopeEnum.SELF_DEPT_AND_LOWER.getValue();

    /**
     * 描述
     */
    private String description;

    /**
     * 是否启用
     */
    private Boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDTO role = (RoleDTO) o;
        return Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}
