package top.yxlgx.wink.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yanxin
 * @Description: 路由meta信息
 */
@Getter
@Setter
public class RouteMetaVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private Boolean ignoreAuth;

    private String roles;

    private Boolean ignoreKeepAlive;

    private Boolean affix;

    private String icon;

    private String frameSrc;

    private String transitionName;

    private Boolean hideBreadcrumb;

    private Boolean hideChildrenInMenu;

    private Boolean carryParam;

    private Boolean single;

    private String currentActiveMenu;

    private Boolean hideTab;

    private Boolean hideMenu;

    private Boolean isLink;

}
