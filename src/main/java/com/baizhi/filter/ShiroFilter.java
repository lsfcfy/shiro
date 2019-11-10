package com.baizhi.filter;

import com.baizhi.realm.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroFilter {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        HashMap<String, String> map = new HashMap<>();
        //AnonymousFilter   匿名拦截器  anon
        //FormAuthenticationFilter  认证拦截器  authc
        map.put("/user/login","anon");
        map.put("/main.jsp","anon");
        map.put("/**","authc");
        shiroFilterFactoryBean.setLoginUrl("/user/login.jsp");


        //声明一个map设置拦截器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);


        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager getSecurityManager(ShiroRealm shiroRealm){
        //DefaultWebSecurityManager为SecurityManager实现类
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    //因为需要将创建对象的权利交给spring工厂管理,所以上一步方法的类由此获取,上下方式一样
    @Bean
    public ShiroRealm getShiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        ShiroRealm shiroRealm = new ShiroRealm();
        //设置凭证匹配器
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroRealm;
    }


    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

}






















