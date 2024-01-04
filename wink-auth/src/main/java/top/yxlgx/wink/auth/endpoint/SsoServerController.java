package top.yxlgx.wink.auth.endpoint;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yxlgx.wink.admin.feign.RemoteUserService;
import top.yxlgx.wink.admin.vo.UserVO;
import top.yxlgx.wink.core.util.Result;


/**
 * @author yanxin
 * @description Sa-Token-SSO Server端 Controller
 */
@RestController
public class SsoServerController {

    @Resource
    RemoteUserService remoteUserService;

    /*
     * SSO-Server端：处理所有SSO相关请求 (下面的章节我们会详细列出开放的接口)
     */
    @RequestMapping("/sso/*")
    public Object ssoRequest() {
        return SaSsoProcessor.instance.serverDister();
    }

    /**
     * 配置SSO相关参数
     */
    @Autowired
    private void configSso(SaSsoConfig sso) {
        // 配置：未登录时返回的View
        sso.setNotLoginView(() -> {
            String msg = "当前会话在SSO-Server端尚未登录，请先访问"
                         + "<a href='/sso/doLogin?name=sa&pwd=123456' target='_blank'> doLogin登录 </a>"
                         + "进行登录之后，刷新页面开始授权";
            return msg;
        });
        // 配置：登录处理函数
        sso.setDoLoginHandle((name, pwd) -> {
            if(name.equals("sa") && pwd.equals("123456")){
                StpUtil.login(10001);
                return SaResult.ok("登录成功！").setData(StpUtil.getTokenValue());
            }
            Result<UserVO> info = remoteUserService.info(name);
            if(info !=null && info.getCode() == Result.success().getCode()){
                UserVO result = info.getResult();
                if(result!=null && result.getPassword().equals(pwd)){
                    StpUtil.login(10001);
                    return SaResult.ok("登录成功！").setData(StpUtil.getTokenValue());
                }else{
                    return SaResult.error("登录失败！");
                }
            }else{
                return SaResult.error("登录异常!");
            }
        });
    }

}