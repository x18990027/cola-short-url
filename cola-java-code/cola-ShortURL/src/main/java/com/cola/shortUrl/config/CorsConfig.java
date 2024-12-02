package com.cola.shortUrl.config;



import java.util.ArrayList;

/**
 * @Author wf
 * @Date 2023-10-16 9:29
 * @Description: TODO 描述一下
 */
//@Configuration
public class CorsConfig {
//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**",buidConfig());//对接口配置跨域设置
//        return new CorsFilter(source);
//    }
//
//    private CorsConfiguration buidConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        //sessionId 多次访问一致
//        corsConfiguration.setAllowCredentials(true);
//        //允许访问的客户端域名
//        ArrayList<String> allowedOriginPatterns = new ArrayList<>();
//        allowedOriginPatterns.add("*");
//        corsConfiguration.setAllowedOriginPatterns(allowedOriginPatterns);
//        //允许任何头
//        corsConfiguration.addAllowedHeader("*");
//        //允许任何方法(post,get等)
//        corsConfiguration.addAllowedMethod("*");
//        return corsConfiguration;
//    }
}