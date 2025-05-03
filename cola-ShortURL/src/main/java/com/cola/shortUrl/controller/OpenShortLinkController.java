package com.cola.shortUrl.controller;


import com.cola.common.annotation.Anonymous;
import com.cola.common.core.controller.BaseController;
import com.cola.shortUrl.domain.LinkAccessStatist;
import com.cola.shortUrl.mapper.ArtQRCodeMapper;
import com.cola.shortUrl.service.ShortLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
public class OpenShortLinkController extends BaseController {


    @Resource
    private ShortLinkService shortLinkService;


    /**
     * 解码重定向
     *
     * @param urlKey 原始链接的编码
     * @return 重定向
     */
    @RequestMapping("/{urlKey}")
    @Anonymous
    public RedirectView redirectToLongLink(@PathVariable("urlKey") String urlKey, HttpServletRequest request) {

        LinkAccessStatist linkAccessStatist = new LinkAccessStatist();
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress != null && ipAddress.contains(",")) {
            int i = ipAddress.indexOf(",");
            ipAddress = ipAddress.substring(0, i);
        }

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }


        linkAccessStatist.setIp(ipAddress);

        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && userAgent.contains("Android")) {
            linkAccessStatist.setUserAgent(request.getHeader("User-Agent"));
            linkAccessStatist.setTerminal(1);
        } else if (userAgent != null & userAgent.contains("iPhone")) {
            linkAccessStatist.setUserAgent(request.getHeader("User-Agent"));
            linkAccessStatist.setTerminal(2);
        } else if (userAgent != null & userAgent.contains("Windows")) {
            linkAccessStatist.setUserAgent(request.getHeader("User-Agent"));
            linkAccessStatist.setTerminal(3);
        } else if (userAgent != null & userAgent.contains("Macintosh")) {
            linkAccessStatist.setUserAgent(request.getHeader("User-Agent"));
            linkAccessStatist.setTerminal(4);
        } else if (userAgent != null) {
            linkAccessStatist.setUserAgent(request.getHeader("User-Agent"));
            linkAccessStatist.setTerminal(5);
        } else {
            linkAccessStatist.setTerminal(5);
        }


        String longLink = shortLinkService.findByShortLink(urlKey, linkAccessStatist);
        RedirectView redirectView = new RedirectView(longLink);
//        // 301永久重定向，避免网络劫持
        redirectView.setStatusCode(HttpStatus.FOUND);
        return redirectView;

    }





}
