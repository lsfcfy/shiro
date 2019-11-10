package com.baizhi.realm;

import com.baizhi.dao.RoleDao;
import com.baizhi.dao.UserDao;
import com.baizhi.dao.UserRoleDao;
import com.baizhi.entity.Role;
import com.baizhi.entity.User;
import com.baizhi.entity.UserRole;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//自定义继承AuthorizingRealm
public class ShiroRealm extends AuthorizingRealm {
        @Autowired
        private UserDao userDao;

        @Autowired
        private RoleDao roleDao;

        @Autowired
        private UserRoleDao userRoleDao;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 根据身份集合获取用户的主身份
        String name = (String) principals.getPrimaryPrincipal();
        //根据名字查询user
        User user = userDao.selectOne(new User(null, name, null, null));
        //根据user查询对应的角色
        List<UserRole> list = userRoleDao.select(new UserRole(null, user.getId(), null));
        //可能有多个角色所以用集合接收
        ArrayList<String> list1 = new ArrayList<>();
        //遍历UserRole集合，根据userRole里的RoleId获取role对象
        for (UserRole userRole : list) {
            Role role = roleDao.selectOne(new Role(userRole.getRoleId(), null));
            //把获取到的role对象放入list集合
            list1.add(role.getName());
        }
        //获取授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(list1);

        for (UserRole userRole : list) {
            Role role = roleDao.selectOne(new Role(userRole.getRoleId(), null));
            //增删改权限控制
            if (role.getName().equals("admin")) {
               /*
                数据库里所存字符串若为select,这里就为select;
                同理若为user:select，这为user:select   推荐
                info.addStringPermission("select");
                */
                info.addStringPermission("user:select");
            }
            if (role.getName().equals("super")) {
                //info.addStringPermission("*");
                info.addStringPermission("user:*");
            }
        }


        //与数据库
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //强转为用户名 密码 认证
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //此处由于是用户查找，所以要求用户名唯一
        String name = token.getUsername();

        User user = new User();
        user.setName(name);

        User loginUser = userDao.selectOne(user);
        if(loginUser == null) {
            return null;
        } else {                                                                                            //this.getName 即当前类
            SimpleAccount account = new SimpleAccount(loginUser.getName(),
                    loginUser.getPassword(), ByteSource.Util.bytes(loginUser.getSalt()), this.getName());
            return account;
        }
    }
}






















