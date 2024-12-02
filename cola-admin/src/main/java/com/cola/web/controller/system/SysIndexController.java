package com.cola.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cola.common.config.RuoYiConfig;
import com.cola.common.utils.StringUtils;
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
     * 系统基础配置
     */
    @Autowired
    private RuoYiConfig ruoyiConfig;

    /**
     * 访问首页，提示语
     */
    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public RedirectView index(HttpServletRequest request) {


        // 获取请求的域名（通常通过请求URI和服务器信息）
        StringBuffer requestURL = request.getRequestURL();
        String domain = requestURL.toString().replace(request.getRequestURI(), "");
        if (domain.endsWith("/")) {
            domain = domain + "ui/home";
        } else {
            domain = domain + "/ui/home";
        }

        RedirectView redirectView = new RedirectView(domain);
//        // 301永久重定向，避免网络劫持
        redirectView.setStatusCode(HttpStatus.FOUND);
        return redirectView;
    }


}
