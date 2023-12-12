package top.yxlgx.wink.admin.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yxlgx.wink.common.jpa.annotation.Query;

/**
 * @author yanxin
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQueryDTO extends BaseQuery {
    @Query
    Long roleId;

    @Query(type = Query.Type.INNER_LIKE)
    String name;

}
