package pw.ewen.WLPT.domains.dtoconvertors.resources;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.domains.entities.utils.userunreaded.UserReaded;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.MenuService;
import pw.ewen.WLPT.services.utils.UserReadedService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * created by wenliang on 2021/4/10
 */
@Component
public class MenuDTOConvertor {
    private final UserContext userContext;
    private final UserReadedService userReadedService;
    private final ResourceTypeRepository resourceTypeRepository;
    private final ApplicationContext applicationContext;
    private List<UserReaded> userReadeds = new ArrayList<>();

    public MenuDTOConvertor(UserContext userContext, UserReadedService userReadedService, ResourceTypeRepository resourceTypeRepository, ApplicationContext applicationContext) {
        this.userContext = userContext;
        this.userReadedService = userReadedService;
        this.resourceTypeRepository = resourceTypeRepository;
        this.applicationContext = applicationContext;
    }

    public Menu toMenu(MenuDTO menuDTO, MenuService menuService) {
        Menu menu = new Menu();
        menu.setId(menuDTO.getId());
        menu.setName(menuDTO.getName());
        menu.setOrderId(menuDTO.getOrderId());
        menu.setIconClass(menuDTO.getIconClass());
        menu.setPath(menuDTO.getPath());

        Optional<Menu> parent;
        if(menuDTO.getParentId() != 0 ) {
            parent = menuService.findOne(menuDTO.getParentId());
            parent.ifPresent(menu::setParent);
        }
        return menu;
    }

    public MenuDTO toDTO(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setOrderId(menu.getOrderId());
        dto.setIconClass(menu.getIconClass());
        dto.setPath(menu.getPath());

        if(menu.getParent() != null){
            dto.setParentId(menu.getParent().getId());
        }

        List<Menu> childrenMenus = menu.getChildren();
        if(childrenMenus.size() > 0) {
            childrenMenus.forEach( (m) -> {
                MenuDTO d = this.toDTO(m);
                dto.getChildren().add(d);
            });
        } else {
            if(menu.getResourceType() != null ){
                if(userContext.getCurrentUser() != null) {
                    userReadeds = userReadedService.findByUserId(userContext.getCurrentUser().getId());
                }

                userReadeds.stream().filter(ur->ur.getResourceTypeClassName().equals(menu.getResourceType().getClassName()))
                        .findFirst()
                        .ifPresent(ur -> {
                            resourceTypeRepository.findById(ur.getResourceTypeClassName())
                                    .ifPresent(resourceType -> {
                                        if(resourceType.getServiceBeanName() != null && !resourceType.getServiceBeanName().isEmpty()){
                                            Object bean = applicationContext.getBean(resourceType.getServiceBeanName());
                                            try {
                                                Method method = bean.getClass().getMethod("findAll");
                                                List allList = (List)method.invoke(bean);
                                                int total = allList.size();
                                                int readedCount = ur.getReadedCount();
                                                dto.setUnReadCount(total - readedCount);
                                            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        });
            }
        }
        return dto;
    }
}
