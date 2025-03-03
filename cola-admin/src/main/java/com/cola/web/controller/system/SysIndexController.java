package com.cola.web.controller.system;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;

/**
 * 首页
 *
 * @author ruoyi
 */
@RestController
public class SysIndexController {



    /**
     * 访问首页
     */
    @RequestMapping("/")
    public RedirectView index(HttpServletRequest request) {
        // 尝试从 X-Forwarded-Host 请求头中获取域名
        String serverName = request.getHeader("X-Forwarded-Host");
        if (serverName == null || serverName.isEmpty()) {
            // 如果 X-Forwarded-Host 不存在，则使用 request.getServerName()
            serverName = request.getServerName();
        }
        // 获取协议
        String scheme = request.getScheme();
        // 获取服务器端口
        int serverPort = request.getServerPort();
        // 拼接基础域名
        StringBuilder domain = new StringBuilder(scheme).append("://").append(serverName);

        // 仅当端口不是默认端口时才添加端口信息
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            domain.append(":").append(serverPort);
        }
        // 拼接最终重定向路径
        domain.append("/ui/home");
        RedirectView redirectView = new RedirectView(domain.toString());
        // 302 临时重定向
        redirectView.setStatusCode(HttpStatus.FOUND);
        return redirectView;
    }


}
