package com.liger.ambition.shiro;

import com.liger.ambition.global.UserConfig;
import com.liger.ambition.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roc_peng on 2017/8/16.
 * Email 18817353729@163.com
 * Url https://github.com/RocPeng/
 * Description 这个世界每天都有太多遗憾,所以你好,再见!
 */

public class AuthRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //身份认证方法：AuthRealm.doGetAuthenticationInfo()
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Map<String, Object> map = new HashMap<>();
        map.put("username", token.getUsername());
        map.put("password", token.getPassword());
        // 模拟从数据库获取对应用户名密码的用户
        User user = new User(token.getUsername(),String.valueOf(token.getPassword()));
        /*if (null == user) {
            throw new AccountException("帐号或密码不正确！");
        }*/
        SecurityUtils.getSubject().getSession().setAttribute(UserConfig.LOGIN_USER,user);
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
