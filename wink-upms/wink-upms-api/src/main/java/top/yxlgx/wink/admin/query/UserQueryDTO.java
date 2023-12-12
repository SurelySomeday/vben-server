package top.yxlgx.wink.admin.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yxlgx.wink.common.jpa.annotation.Query;

import java.util.List;

/**
 * @author yanxin
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDTO extends BaseQuery {
    @Query
    Long userId;

    @Query(type = Query.Type.INNER_LIKE)
    String name;

    @Query(type = Query.Type.BETWEEN)
    List<Integer> age;

    @Query(propName = "address",joinName="address",type = Query.Type.INNER_LIKE)
    String addressName;
    @Query(propName = "id",joinName="address",type = Query.Type.EQUAL)
    Long addressId;

    @Query(propName = "name",joinName="roles",type = Query.Type.INNER_LIKE)
    String roleName;
}
