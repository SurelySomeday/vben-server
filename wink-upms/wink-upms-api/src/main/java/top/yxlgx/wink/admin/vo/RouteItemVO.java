package top.yxlgx.wink.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author yanxin
 * @Description: 路由项
 */
@Getter
@Setter
public class RouteItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String path;

    private String component;

    private RouteMetaVO meta;

    private String name;

    private String redirect;

    private List<RouteItemVO> children;

}
