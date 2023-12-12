package top.yxlgx.wink.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import top.yxlgx.wink.admin.entity.base.BaseEntity;

import java.util.Objects;

/**
 * @author yanxin
 * @Description: 用户
 */
@Getter
@Setter
public class UserDTO {
    @NotNull(groups = {BaseEntity.Update.class})
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户名
     */
    @NotNull(groups = {BaseEntity.Update.class,BaseEntity.Create.class})
    String username;

    /**
     * 头像
     */
    private String avatar;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDTO user = (UserDTO) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
