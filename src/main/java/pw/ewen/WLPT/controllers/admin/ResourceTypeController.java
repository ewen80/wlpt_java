package pw.ewen.WLPT.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.ResourceTypeDTO;
import pw.ewen.WLPT.domains.DTOs.RoleDTO;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

/**
 * 资源类型
 */
@RestController
@RequestMapping(value = "/admin/resourcetypes")
public class ResourceTypeController {
    private final ResourceTypeService resourceTypeService;
    private final RoleService roleService;

    @Autowired
    public ResourceTypeController(ResourceTypeService resourceTypeService,
                                  RoleService roleService) {
        this.resourceTypeService = resourceTypeService;
        this.roleService = roleService;
    }

//    //转为DTO对象的内部辅助类
//    static class ResourceTypeDTOConverter implements Converter<ResourceType, ResourceTypeDTO> {
//        @Override
//        public ResourceTypeDTO convert(ResourceType resourceType) {
//            return  ResourceTypeDTO.convertFromResourceType(resourceType);
//        }
//    }

    /**
     * 获取资源类型（分页，查询）
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<ResourceTypeDTO> getResourcesWithPage(PageInfo pageInfo){
        Page<ResourceType> resourceTypes;
        PageRequest pr = pageInfo.getPageRequest();

        if(pageInfo.getFilter().isEmpty()){
            resourceTypes =  this.resourceTypeService.findAll(pr);
        }else{
            resourceTypes =  this.resourceTypeService.findAll(pageInfo.getFilter(), pr);
        }

        return resourceTypes.map(ResourceTypeDTO::convertFromResourceType);
    }

    /**
     * 获取一个资源类型
     * @param className 资源类型类名
     */
    @RequestMapping(value="/{className}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<ResourceTypeDTO> getOne(@PathVariable("className") String className){
        return resourceTypeService.findOne(className)
                .map((rt) -> new ResponseEntity<ResourceTypeDTO>(ResourceTypeDTO.convertFromResourceType(rt), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 保存
     * @param resourceTypeDTO 资源类型DTO
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public ResourceTypeDTO save(@RequestBody ResourceTypeDTO resourceTypeDTO){
        ResourceType resourceType = ResourceTypeDTO.convertToResourceType(resourceTypeDTO, roleService, resourceTypeService);
        return ResourceTypeDTO.convertFromResourceType(this.resourceTypeService.save(resourceType));
    }

    /**
     * 删除
     * @param resourceTypes 资源类型类名(多个类型用,分隔)
     */
    @RequestMapping(value = "/{resourceTypes}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("resourceTypes") String resourceTypes){
        String[] arrClassNames = resourceTypes.split(",");
        this.resourceTypeService.delete(arrClassNames);
    }

    /**
     * 检查资源类型类名是否存在
     * @param className 资源类型类名
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/check/{className}/")
    public boolean checkClassNameExist(@PathVariable("className") String className) {
        return resourceTypeService.findOne(className).isPresent();
    }
}
