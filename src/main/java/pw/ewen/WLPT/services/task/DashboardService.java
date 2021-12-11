package pw.ewen.WLPT.services.task;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.utils.userunreaded.UserReaded;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.utils.UserReadedRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

/**
 * created by wenliang on 2021-11-26
 * 首页dashboard定时任务
 */
@Service
public class DashboardService implements InitializingBean {

    private final UserReadedRepository userUnreadedRepository;
    private final ApplicationContext applicationContext;
    private final UserRepository userRepository;
    private final ResourceTypeRepository resourceTypeRepository;

    @Value("${bizconfig.schedules.resource_readed_info.do_startup}")
    private  boolean scheduleDoStartup;

    public DashboardService(UserReadedRepository userUnreadedRepository, ApplicationContext applicationContext, UserRepository userRepository, ResourceTypeRepository resourceTypeRepository) {
        this.userUnreadedRepository = userUnreadedRepository;
        this.applicationContext = applicationContext;
        this.userRepository = userRepository;
        this.resourceTypeRepository = resourceTypeRepository;
    }

    /**
     * 更新资源未读信息
     */
    @Scheduled(cron = "${bizconfig.schedules.resource_readed_info.cron}")
    public void doUpdateResourceReadedInfo() {
        List<User> users = userRepository.findAll();
        for(User user : users) {
            try {
                this.updateResourceReadedInfo(user.getId());
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新资源已读信息
     * @param userId 用户id
     */
    private void updateResourceReadedInfo(String userId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<ResourceType> resourceTypes = resourceTypeRepository.findAll();
        for(ResourceType resource : resourceTypes) {
            if(resource.getRepositoryBeanName() != null && !resource.getRepositoryBeanName().isEmpty()) {
                Object bean =  applicationContext.getBean(resource.getRepositoryBeanName());
                Method method = bean.getClass().getMethod("getReadedCount", String.class);
                int count = (int) method.invoke(bean, userId);
                UserReaded userReaded = new UserReaded(userId, resource.getClassName(), count, LocalDateTime.now());
                userUnreadedRepository.save(userReaded);
            }

        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.scheduleDoStartup){
           this.doUpdateResourceReadedInfo();
        }
    }
}
