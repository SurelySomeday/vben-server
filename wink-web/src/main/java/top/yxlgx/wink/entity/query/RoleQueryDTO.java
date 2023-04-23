package top.yxlgx.wink.entity.query;

import lombok.Data;
import top.yxlgx.wink.annotation.Query;

import java.util.List;

/**
 * @author yanxin
 * @Description:
 */
@Data
public class RoleQueryDTO {
    @Query
    Long roleId;

    @Query(type = Query.Type.INNER_LIKE)
    String name;

}
