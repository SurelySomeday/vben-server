package top.yxlgx.wink.config.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yanxin
 * @Description:
 */
//@Component
public class JwtLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write("logout");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
