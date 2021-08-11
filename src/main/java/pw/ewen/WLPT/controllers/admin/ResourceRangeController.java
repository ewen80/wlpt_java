package pw.ewen.WLPT.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.services.ResourceRangeService;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源范围
 */
@RestController
@RequestMapping(value = "/admin/resourceranges")
public class ResourceRangeController {

   private final ResourceRangeService resourceRangeService;
   private final RoleService roleService;
   private final ResourceTypeService resourceTypeService;

    @Autowired
    public ResourceRangeController(ResourceRangeService resourceRangeService,
                                   RoleService roleService,
                                   ResourceTypeService resourceTypeService) {
       this.resourceRangeService = resourceRangeService;
       this.roleService = roleService;
       this.resourceTypeService = resourceTypeService;
    }

    /**
     * 获取资源类型
     * @param resourceTypeClassName 资源类型类名
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{resourceClassName}", produces = "application/json")
    public List<ResourceRangeDTO> getByResourceType(@PathVariable(value = "resourceClassName") String resourceTypeClassName){
        return resourceRangeService.findByResourceType(resourceTypeClassName)
                .stream()
                .map( ResourceRangeDTO::convertFromResourceRange)
                .collect(Collectors.toList());
    }

    /**
     * 通过角色和资源类型获取资源范围
     * @param resourceTypeClassName 资源类型类名称
     * @param roleId 角色id
     */
    @RequestMapping(method = RequestMethod.GET, value= "/{resourceClassName}/{roleId}", produces = "application/json")
    public ResponseEntity<ResourceRangeDTO> getByResourceTypeAndRole(@PathVariable(value = "resourceClassName") String resourceTypeClassName, @PathVariable(value = "roleId") String roleId) {
        ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(resourceTypeClassName, roleId);
        return range == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(ResourceRangeDTO.convertFromResourceRange(range), HttpStatus.OK);
    }

    /**
     * 检查资源范围是否存在
     * @param resourceTypeClassName 资源类型类名称
     * @param roleId 角色id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkexist", produces = "application/json")
    public boolean checkResourceRangeExist(@RequestParam(value = "resourceClassName") String resourceTypeClassName, @RequestParam(value = "roleId") String roleId) {
        ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(resourceTypeClassName, roleId);
        return range != null;
    }

    /**
     * 保存资源范围
     * @param dto 资源范围
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResourceRangeDTO save(@RequestBody ResourceRangeDTO dto){
        ResourceRange range = dto.convertToResourceRange(this.roleService, this.resourceTypeService);
        range =  this.resourceRangeService.save(range);
        return ResourceRangeDTO.convertFromResourceRange(range);
    }

    /**
     * 删除资源范围
     * @param resourceRangeIds 资源范围id.多个资源范围id用,分隔
     */
    @RequestMapping(value = "/{resourceRangeIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("resourceRangeIds") String resourceRangeIds){
        String[] arrResourceRangeIds = resourceRangeIds.split(",");
        this.resourceRangeService.delete(arrResourceRangeIds);
    }

}
