package trine.ShortFuture.realm;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * shiro 配置类
 *
 * @author wufan
 */
@Configuration
public class ShiroConfigure {
	
    /**
     * shiro SecurityManager
     */
    @Inject
    @Lazy
    private SecurityManager securityManager = null;
    
    @Inject
    @Lazy
    private SessionDAO sessionDao = null;

    /**
     * Shiro SecurityManager
     *
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Bean(name = "securityManager")
    @Singleton
    public SecurityManager getSecurityManager() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        //根据配置文件拿到对应的realm
        Realm frameworkRealm = new MyShiroRealm();

        DefaultSecurityManager sm = new DefaultSecurityManager(frameworkRealm);
        SecurityUtils.setSecurityManager(sm);

        DefaultSessionManager ssm = new DefaultSessionManager();        
        ssm.setSessionDAO(sessionDao);
        sm.setSessionManager(ssm);
        sm.setCacheManager(new MemoryConstrainedCacheManager());        
        //设置session过期时间
        long timeout = Long.valueOf("1800000");
        ssm.setGlobalSessionTimeout(timeout);           
        return sm;
    }
    
    /**
     * session dao
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    @Bean
    @Singleton
    public SessionDAO getSessionDAO() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    	SessionDAO result = null;
    	String configvalue = "local";
    	long timeout = Long.valueOf("1800000");
    	result =  new EnterpriseCacheSessionDAO();
    	return result; 
    }

    /**
     * Shiro AOP
     *
     * @return
     */
    @Bean
    @Singleton
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {

        AuthorizationAttributeSourceAdvisor aasd = new AuthorizationAttributeSourceAdvisor();
        aasd.setSecurityManager(securityManager);

        return aasd;
    }

}
