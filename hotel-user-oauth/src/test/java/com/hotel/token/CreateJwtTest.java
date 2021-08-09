package com.hotel.token;

import com.alibaba.fastjson.JSON;
import com.hotel.OAuthApplication;
import entity.BCrypt;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/***
 * 令牌创建和解析
 */
@SpringBootTest(classes = OAuthApplication.class)
public class CreateJwtTest {
    @Autowired
    private RedisTemplate redisTemplate;

    //创建令牌
    @Test
    public void testCreateToken(){
        //加载证书 读取证书路径
        ClassPathResource classPathResource = new ClassPathResource("hoteloau.jks");

        //读取证书 加载证书数据
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,"hoteloau".toCharArray());

        //获取公钥 私钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("hoteloau","hoteloau".toCharArray());

        //获取私钥->RSA算法
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        //创建令牌，需要私钥加盐【RSA算法】
        Map<String,Object> payload = new HashMap<String, Object>();
        payload.put("address","sz");
        payload.put("email","123");
        payload.put("name","tom");
        payload.put("authorities",new String[]{"admin","oauth"});

        Jwt encode = JwtHelper.encode(JSON.toJSONString(payload), new RsaSigner(privateKey));

        String token = encode.getEncoded();
        System.out.println(token);
    }

    //解析令牌（校验令牌)
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiZXhwIjoxNjI2MzI5MTIzLCJhdXRob3JpdGllcyI6WyJzeXM6cm9sZTpyZW1vdmUiLCJzeXM6cm9sZTpkYXRhIiwic3lzOnBvd2VyOmRhdGEiLCJzeXM6cG93ZXI6ZWRpdCIsInN5czp1c2VyOmFkZCIsInN5czpwb3dlcjpyZW1vdmUiLCJzeXM6cm9sZTplZGl0Iiwic3lzOmZpbGU6YWRkIiwic3lzOnBvd2VyOm1haW4iLCJzeXM6dXNlcjpkYXRhIiwic3lzOnVzZXI6ZWRpdCIsInN5czpmaWxlOmRhdGEiLCJzeXM6cm9sZTpwb3dlciIsInN5czpwb3dlcjphZGQiLCJzeXM6dXNlcjptYWluIiwic3lzOnVzZXI6cmVtb3ZlIiwic3lzOmZpbGU6bWFpbiIsInN5czpmaWxlOnJlbW92ZSIsInN5czpyb2xlOm1haW4iLCJzeXM6cm9sZTphZGQiXSwianRpIjoiN2MzNWVlOWYtNjhkNC00ZTYxLThlODMtYjZkZThmMDRjY2I1IiwiY2xpZW50X2lkIjoiaG90ZWxvYXUifQ.hjA2oMSzSxDBBlJKJHcC50edb0gpFzkgyg_-efPAMvO_XoUljQVt0w6k6rn-fUc5MvHVE5myFhtc2c_8QU2nTv2qyfhp3ZqSvDkQKpelFJk0ppIrgi_aR79OE0Rlw-GG5WPwgRW4ImvC_r2pZSgFizFT15kwPoUoQ7TRflS7eB_27eZ6I8VnYJjIdvVfvCRLo2SkYMr7I147ZjgH1WrjMx9WkFOskyB4ed7z-xUSuJcOaPyTPNpNdh_fJD_5R190e1nLyqUyzSmbm-vTV-YO2w9tvcuH_ix-9RAgwPcXHtuFCvBHSkBDjPr4ahwiuufpi9OA23yqiP9Niyegq8UpPQ";
        String token1 = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiZXhwIjoxNjI2MzUxMjg3LCJhdXRob3JpdGllcyI6WyJzeXM6cm9sZTpyZW1vdmUiLCJzeXM6cm9sZTpkYXRhIiwic3lzOnBvd2VyOmRhdGEiLCJzeXM6cG93ZXI6ZWRpdCIsInN5czp1c2VyOmFkZCIsInN5czpwb3dlcjpyZW1vdmUiLCJzeXM6cm9sZTplZGl0Iiwic3lzOmZpbGU6YWRkIiwic3lzOnBvd2VyOm1haW4iLCJzeXM6dXNlcjpkYXRhIiwic3lzOnVzZXI6ZWRpdCIsInN5czpmaWxlOmRhdGEiLCJzeXM6cm9sZTpwb3dlciIsInN5czpwb3dlcjphZGQiLCJzeXM6dXNlcjptYWluIiwic3lzOnVzZXI6cmVtb3ZlIiwic3lzOmZpbGU6bWFpbiIsInN5czpmaWxlOnJlbW92ZSIsInN5czpyb2xlOm1haW4iLCJzeXM6cm9sZTphZGQiXSwianRpIjoiMDI2NGQ3ZDQtNDk2Yi00NmMzLWJjNGEtMDA3NjAyZTA4NzQ3IiwiY2xpZW50X2lkIjoiaG90ZWxvYXUifQ.d6EiKSUJhO1ABZEkLok6fWCsadcV8GBR1qiCQgiHN8N9ecOTlbwjVEnE9ji7wHEZAER-l8fERa8C-Lnp37UShIpqPGryXj5NlTGv-7fWMuzQlTDfoQht0_eUvbUHs2joRvHLwU6IHq9WLQLHorLrM1ngBMnbYL5e0nLY4IRAmL3NN1EfEpQLeCe25l3WbWcn4szP5sQVo-dIs0mUnalWjraB85SjpAXZuDuw9D4_0lSLTntm-ItQrjv5-nFWZw8bik-uV7QjVpeQ6VBkbGOqNCjZ6sXdtaqLrfMAXe-9vcatteeeSUPHIA09yeoFhHquFAQGM4eyFfom7tP8V1vVHw";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAirRJlfe+CBSvU0oaWxku4zJzF9zA3csS2WUuJahB48QJNJb2mIvwVKSRDN6bxqscVuPItzK2E7WmtiYGFGadPItJ8wTxigwif5UAXuMFksLX/LO8T2MVZECKxgReaWU4QWWLkoJad6tiRj0dEE0YwfEvNo5Yyroon+x+zHmgbItjpJMrir0UbN+hVF/EBe7AYz4qJWLiwt1OU3P8uPfhh1GwIsQNp+vZpUiCikIxAgIe06KPzscU4PduoKv8UKWTOK7TAPSWf44ylg8qZRWug7ye69i4CUfxvkpC3h94OIBL4CtwFT9O4rIi/jV6yq5JEXOB49kgriHnwAkzJLRTbQIDAQAB-----END PUBLIC KEY-----";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
        Jwt jwt1 = JwtHelper.decodeAndVerify(token1, new RsaVerifier(publickey));
        System.out.println(jwt1.getClaims());

        //加密盐
        String claims = jwt.getClaims();
        System.out.println(claims);

        //jwt令牌
//        String encoded = jwt.getEncoded();
//        System.out.println(encoded);

    }

    @Test
    public void BCyin(){
//        String id = "hoteloau";
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//        boolean changgou = BCrypt.checkpw("hoteloau", "$2a$10$pAYRz5YHAVDtCyrDxMpzDu3f6m0ZbvbxgVJdiQZg7x6pURAu8hmt6");
//        System.out.println(changgou);
//        redisTemplate.opsForValue().set("name","tom");
//        System.out.println(eee);
    }

}
