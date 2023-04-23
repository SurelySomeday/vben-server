package top.yxlgx.wink.entity.query;

import lombok.Data;
import top.yxlgx.wink.annotation.Query;

import java.util.List;

/**
 * @author yanxin
 * @Description:
 */
@Data
public class UserQueryDTO {
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
}
