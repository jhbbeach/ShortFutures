package trine.ShortFuture.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
 
/**
 * Shiro 配置
 *
Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 *
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
@Configuration
public class ShiroConfiguration {
      
    @Lazy
    private SessionDAO sessionDao = null;
    
    @Bean(name = "securityManager")
    public SecurityManager securityManager() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        //根据配置文件拿到对应的realm
        Realm frameworkRealm = new MyShiroRealm();

        DefaultSecurityManager sm = new DefaultSecurityManager(frameworkRealm);
        SecurityUtils.setSecurityManager(sm);

        DefaultSessionManager ssm = new DefaultSessionManager();        
        ssm.setSessionDAO(sessionDao);
        sm.setSessionManager(ssm);
        sm.setCacheManager(new MemoryConstrainedCacheManager());        
        String configvalue = "local";
        if ("local".equals(configvalue)) {
            //设置session过期时间
            long timeout = Long.valueOf("1800000");
            ssm.setGlobalSessionTimeout(timeout);           
        }
        return sm;
    }

}
