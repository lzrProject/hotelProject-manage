package com.hotel.filter;

/***
 * 不需要认证就可以访问的路径过滤
 */
public class URLFilter {
    //  静态资源/admin,/component,/system/captcha/generate,
    private static final String allUrl = "/oauth/login,/oauth/user,/system/captcha/generate," ;
//                                    "/index,/power/user/menu";
    private static final String allStatic = ".css,.svg,.jpg,.png,.js,.gif,.eot,.ttf,.woff,.woff2,.config.json,.config.yml,403,";

    public static boolean hasAuthorize(String url){
        String[] urls = allUrl.split(",");
        String[] urlss = allStatic.split(",");

        for (String str : urlss){
            if(url.indexOf(str) != -1){
                return true;
            }
        }

        for (String uri : urls){
//            if(url.equals(uri) || url.substring(0,6).equals(uri) || url.substring(0,10).equals(uri)){
            if(url.equals(uri)){
                return true;
            }
        }
        return false;
    }
}
