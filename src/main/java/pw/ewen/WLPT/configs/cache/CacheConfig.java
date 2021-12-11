package pw.ewen.WLPT.configs.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.PermissionGrantingStrategy;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import java.io.Serializable;

/**
 * Created by wenliang on 17-4-19.
 * 系统缓存配置
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    AclCache getAclCache(org.springframework.cache.CacheManager cm, PermissionGrantingStrategy permissionGrantingStrategy, AclAuthorizationStrategy aclAuthorizationStrategy) {
        return new SpringCacheBasedAclCache(cm.getCache("aclCache"), permissionGrantingStrategy, aclAuthorizationStrategy);
    }

    @Bean
    JCacheCacheManager jcacheManager(CacheManager cm){
       return new JCacheCacheManager(cm);
    }

   @Bean
    CacheManager cacheManager() {
       CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
       MutableConfiguration<Serializable, MutableAcl> configuration = new MutableConfiguration<Serializable, MutableAcl>()
               .setTypes(Serializable.class, MutableAcl.class)
               .setStoreByValue(false);
       cacheManager.createCache("aclCache", configuration);
       return cacheManager;
   }
}