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
public class DeptQueryDTO extends BaseQuery{
    @Query
    Long deptId;

    @Query(type = Query.Type.INNER_LIKE)
    String name;
}
