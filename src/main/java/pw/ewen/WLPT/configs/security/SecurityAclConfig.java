package pw.ewen.WLPT.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.security.acl.ChangdiPermission;

import javax.sql.DataSource;


/**
 * Created by wen on 17-2-22.
 * Acl配置
 */
@Configuration
public class SecurityAclConfig {

    @Autowired
    private BizConfig bizConfig;

    @Bean
    PermissionEvaluator getAclPermissionEvaluator(AclService aclService, ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy){
        AclPermissionEvaluator aclPermissionEvaluator = new AclPermissionEvaluator(aclService);
        aclPermissionEvaluator.setObjectIdentityRetrievalStrategy(objectIdentityRetrievalStrategy);
        return aclPermissionEvaluator;
    }

    @Bean
    AclPermissionCacheOptimizer getAclPermissionCacheOptimizer(AclService aclService){
        return new AclPermissionCacheOptimizer(aclService);
    }

//    替代默认JdbcMutableAclService，因为默认是以HSQLDB为准，MYSQL需要做修改。
//    参见： https://stackoverflow.com/questions/54859029/spring-security-acl-object/56275135#56275135
//          https://stackoverflow.com/a/56275135
    @Bean
    JdbcMutableAclService getAclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache){
        JdbcMutableAclService jdbcMutableAclService = new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
        // MySQL ONLY
        jdbcMutableAclService.setClassIdentityQuery("SELECT @@IDENTITY");
        jdbcMutableAclService.setSidIdentityQuery("SELECT @@IDENTITY");
        return jdbcMutableAclService;
    }

    @Bean
    BasicLookupStrategy getLookupStrategy(DataSource dataSource, AclCache aclCache,
                                          AclAuthorizationStrategy aclAuthorizationStrategy,
                                          AuditLogger auditLogger){
        BasicLookupStrategy  basicLookupStrategy =  new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, auditLogger);
        // 注册自定义权限
        basicLookupStrategy.setPermissionFactory(new DefaultPermissionFactory(ChangdiPermission.class));
        return basicLookupStrategy;
    }

    @Bean
    DefaultPermissionGrantingStrategy getPermissionGrantingStrategy(AuditLogger auditLogger){
        return new DefaultPermissionGrantingStrategy(auditLogger);
    }

    @Bean
    AuditLogger getAuditLogger(){
        return new ConsoleAuditLogger();
    }

    /**
     * 有管理ACL权限的角色(能进行添加、修改、删除acl操作)，默认：admin组
     * @return
     */
    @Bean
    AclAuthorizationStrategy getAclAuthorizationStrategy(){
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority(bizConfig.getUser().getAdminRoleId()));
    }

}
