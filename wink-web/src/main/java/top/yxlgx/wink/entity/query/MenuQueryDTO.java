package top.yxlgx.wink.entity.query;

import lombok.Data;
import top.yxlgx.wink.annotation.Query;

/**
 * @author yanxin
 * @Description:
 */
@Data
public class MenuQueryDTO {
    @Query
    Long menuId;

    @Query(type = Query.Type.INNER_LIKE)
    String name;
}
