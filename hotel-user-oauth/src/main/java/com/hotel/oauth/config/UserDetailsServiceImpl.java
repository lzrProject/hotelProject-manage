package com.hotel.oauth.config;

import com.google.common.base.Joiner;
import com.hotel.index.feign.PowerFeign;
import com.hotel.oauth.util.UserJwt;
import com.hotel.user.feign.UserFeign;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*****
 * 自定义授权认证类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private PowerFeign powerFeign;

    @Autowired
    ClientDetailsService clientDetailsService;

    /****
     * 自定义授权认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /***
         * 客户端信息认证
         */

        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //秘钥
                String clientSecret = clientDetails.getClientSecret();
                //静态方式
                //return new User(username,new BCryptPasswordEncoder().encode(clientSecret), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
                //数据库查找方式
                return new User(username,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }


        /***
         * 用户信息认证
         */

        if (StringUtils.isEmpty(username)) {
            return null;
        }

        List<com.hotel.user.pojo.User> result = userFeign.findByUsername(username);

        if(result.size() == 0){
            return null;
        }

        //根据用户名查询用户信息
//        String pwd = new BCryptPasswordEncoder().encode("itheima");
        String password = result.get(0).getPassword();


        //查询权限信息
        ArrayList powerIds = null;
        try {
            powerIds = powerFeign.findPowerId(result.get(0).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String join = Joiner.on(",").join(powerIds);

        //创建User对象 权限
        String permissions = join;     //指定用户权限角色信息
        UserJwt userDetails = new UserJwt(username,password,AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
        userDetails.setId(result.get(0).getId().toString());
        userDetails.setAvatar(result.get(0).getAvatar());
        return userDetails;
    }
}
