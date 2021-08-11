package pw.ewen.WLPT.configs.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;

/**
 * Created by wenliang on 17-4-19.
 * 系统缓存配置
 */
@Configuration
public class EhcacheConfig {

    @Bean
//    EhCacheBasedAclCache getAclCache(Ehcache ehcache, PermissionGrantingStrategy permissionGrantingStrategy,
//                                     AclAuthorizationStrategy aclAuthorizationStrategy){
//        return new EhCacheBasedAclCache(ehcache, permissionGrantingStrategy, aclAuthorizationStrategy);
//    }
    EhCacheBasedAclCache getAclCache(CacheManager cacheManager, PermissionGrantingStrategy permissionGrantingStrategy,
                                     AclAuthorizationStrategy aclAuthorizationStrategy){
        cacheManager.addCache("aclCache");
        return new EhCacheBasedAclCache(cacheManager.getEhcache("aclCache"), permissionGrantingStrategy, aclAuthorizationStrategy);
    }

    @Bean
    EhCacheManagerFactoryBean getEhCacheManager(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean =  new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setCacheManagerName("myCacheManager");
        return cacheManagerFactoryBean;
    }

//    @Bean
//    EhCacheFactoryBean getEhCache(CacheManager cacheManager){
//        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
//        ehCacheFactoryBean.setCacheManager(cacheManager);
//        ehCacheFactoryBean.setCacheName("myCache");
//        return ehCacheFactoryBean;
//    }
}
