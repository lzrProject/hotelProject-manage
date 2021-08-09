package com.hotel.jwt;

import entity.BCrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JwtTest {
    //创建令牌
    @Test
    public void testCreateJwt(){
        JwtBuilder builder = Jwts.builder()
                .setId("999")
                .setSubject("登录")
                .setIssuer("hotel")         //颁发者
                .setIssuedAt(new Date())    //设置签发时间
                .setExpiration(new Date(System.currentTimeMillis()+3600000));    //设置过期时间 10s过期

        //自定义载荷信息
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("company","酒店管理系统");
        userInfo.put("address","北极");
        builder.addClaims(userInfo);        //添加载荷

        builder.signWith(SignatureAlgorithm.HS256,"itcast");
        //构建，创建一个字符串
        String result = builder.compact();
        System.out.println(result);
    }

    //解析令牌
    @Test
    public void testParseJwt(){
        String compactJwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5OTkiLCJzdWIiOiLnmbvlvZUiLCJpc3MiOiJob3RlbCIsImlhdCI6MTYxOTE0MjQ3MiwiZXhwIjoxNjE5MTQ2MDcyfQ.vtz2vW_Ww95AKP3Wybpoy357tWHSdFKuOHA7PKreVOs";
        Claims claims = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws(compactJwt)
                .getBody();
        System.out.println(claims);
    }

    @Test
    public void testBCrypt(){
        String value = "$2a$10$1CbFDKqCa18qemy/YK9Ig.1LUgOD8On3mNlk4SIt/KTGqEmZBDmhm";
        String key = "adminss";
//        BCrypt bCrypt = new BCrypt();
        String encode = new BCryptPasswordEncoder().encode(key);
        boolean result = BCrypt.checkpw(key, encode);
        System.out.println(encode);
        System.out.println(result);
    }

    @Test
    public void json(){
        boolean szitheima = BCrypt.checkpw("szitheima", "$2a$10$zaCZL4nISO729aXTen4/LurHJKCrzpXRgOglUYfBbSUnXGSb/NGa6");
        System.out.println(szitheima);
    }

    @Test
    public void pattern() {
//        String str = "成都市(成华区)(武侯区)(高新区)";
//        Pattern pattern = Pattern.compile(".*?(?=\\()");
//        Matcher matcher = pattern.matcher(str);
//        if(matcher.find()){
//            System.out.println(matcher.group());
//        }
//        String url = "rtsp://admin:admin@192.168.30.98:554/media/video1";
//        Pattern compile = Pattern.compile("(\\d+.\\d+.\\d+.\\d+):(\\d+)");
//        Matcher matcher = compile.matcher(url);
//        if (matcher.find()) {
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(2));
//
//        }

//        String str = "<a href=\"http://hi.baidu.com/mianshiti/blog/category/微软面试题\"> 微软面试题 </a>";
//        Pattern compile = Pattern.compile("<a\\s*.*?href=\"(.*)\">.*</a>");
//        Matcher matcher = compile.matcher(str);
//        if (matcher.find()){
//            System.out.println(matcher.group(1));
//        }
        String str = "<a href=\"goods.php?id=25\">" +
                "<img src=\"images/222.jpg\" alt=\"摩托罗拉M80\"/></a><br />" +
                "<a href=\"goods.php?id=26\">" +
                "<img src=\"images/333.jpg\" alt=\"三星A50\"/></a><br />" +
                "<a href=\"goods.php?id=27\">" +
                "<img src=\"images/444.jpg\" alt=\"诺基亚N97\"/></a><br />" +
                "<a href=\"goods.php?id=28\">" +
                "<img src=\"images/555.jpg\" alt=\"LG5280\"/></a><br />" +
                "<a href=\"goods.php?id=29\">" +
                "<img src=\"images/666.jpg\" alt=\"lenovoA38\"/></a><br />" +
                "</div>";
        Pattern compile = Pattern.compile("<img src=\"(.*?)\" alt=\"(.*?)\"/>");
        Matcher matcher = compile.matcher(str);
        ArrayList arrayList = new ArrayList();
        while (matcher.find()){

//            Matcher matcher1 = Pattern.compile("<img\\d+src=\"(.*)\"\\d+alt=\"(.*)\"/>").matcher(matcher.group());
            matcher.group();
            arrayList.add("src="+matcher.group(1)+" alt="+matcher.group(2));
        }
        System.out.println(arrayList);
    }

    @Test
    public void test(){
        String a = "";
        System.out.println(StringUtils.isBlank(a));
        System.out.println(StringUtils.isEmpty(a));
    }
}

