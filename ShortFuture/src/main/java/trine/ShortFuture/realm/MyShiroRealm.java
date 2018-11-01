package trine.ShortFuture.realm;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;

import trine.ShortFuture.SpringUtil;
import trine.ShortFuture.entity.User;
import trine.ShortFuture.repository.UserRepository;
import trine.ShortFuture.tools.MD5Utils;

@Singleton
public class MyShiroRealm extends AuthorizingRealm {
    @Inject
	@Lazy
    private UserRepository userRepository;

    /**
     * 授权用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        //获取用户
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        //获取用户角色
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("100002");
        info.setRoles(roleSet);

        //获取用户权限
        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add("权限添加");
        permissionSet.add("权限删除");
        info.setStringPermissions(permissionSet);

        return info;
    }

    /**
     * 验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
    	String password = MD5Utils.getMD5(String.valueOf(token.getPassword())).toLowerCase();
//    	User user = userRepository.checkUser(username, password);

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
//        SpringUtil.getApplicationContext().getBean(UserRepository.class).checkUser(username, password);

        if(SpringUtil.getApplicationContext().getBean(UserRepository.class).checkUser(username, password) != null){
        	return new SimpleAuthenticationInfo(user, token.getPassword(), getName());
        }else{
        	throw new AuthenticationException("wrong password");
        }
    }

}
