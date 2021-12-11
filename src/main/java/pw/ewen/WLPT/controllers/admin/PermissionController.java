package pw.ewen.WLPT.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.acls.model.Permission;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.ResourceRangePermissionWrapperDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.dtoconvertors.ResourceRangePermissionWrapperDTOConvertor;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.services.PermissionService;
import pw.ewen.WLPT.services.ResourceRangeService;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 权限控制
 */
@RestController
@RequestMapping(value = "/admin/permissions")
public class PermissionController {

    private final PermissionService permissionService;
    private final ResourceTypeService resourceTypeService;
    private final ResourceRangeService resourceRangeService;
    private final RoleService roleService;
    private final BizConfig bizConfig;
    private final ResourceRangePermissionWrapperDTOConvertor resourceRangePermissionWrapperDTOConvertor;

    @Autowired
    public PermissionController(PermissionService permissionService,
                                ResourceTypeService resourceTypeService,
                                ResourceRangeService resourceRangeService,
                                ResourceRangePermissionWrapperDTOConvertor resourceRangePermissionWrapperDTOConvertor,
                                RoleService roleService, BizConfig bizConfig) {
        this.permissionService = permissionService;
        this.resourceTypeService = resourceTypeService;
        this.resourceRangeService = resourceRangeService;
        this.resourceRangePermissionWrapperDTOConvertor = resourceRangePermissionWrapperDTOConvertor;
        this.roleService = roleService;
        this.bizConfig = bizConfig;
    }

    /**
     * 获取权限包装类
     * @param filter 过滤器
     * @apiNote 只限管理员执行
     */
    @GetMapping(produces = "application/json", value = "/wrappers")
    public Set<ResourceRangePermissionWrapperDTO> getPermissionWrappers(@RequestParam(required = false, defaultValue = "") String filter) {
        List<ResourceRange> ranges = this.resourceRangeService.findAll(filter);
        if(ranges.size() > 0) {
            StringBuilder sb = new StringBuilder();
            ranges.forEach( (range) -> sb.append(range.getId()).append(','));
            return this.getByResourceRanges(sb.toString());
        }
        return new HashSet<>();
    }

    /**
     * 根据资源类别和角色获取权限
     * @param resourceTypeClassName 资源类别的资源类名
     * @param roleId 角色id
     * @apiNote 权限列表
     */
    @GetMapping()
    public Set<Permission> getPermissionsByResourceTypeAndRole(@RequestParam("resourceTypeClassName") String resourceTypeClassName, @RequestParam("roleId") String roleId) {
        ResourceRange rr =  resourceRangeService.findByResourceTypeAndRole(resourceTypeClassName, roleId);
        if(rr != null) {
            ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(rr.getId());
            return wrapper.getPermissions();
        } else {
            return new HashSet<>();
        }

    }

    /**
     * 获取一个或者多个ResourceRange权限
     * @param resourceRangeIds 资源范围id。多个ResourceRange用,分割
     */
//    @RequestMapping(value = "/ResourceRange", method = RequestMethod.GET, produces = "application/json")
    private Set<ResourceRangePermissionWrapperDTO> getByResourceRanges(@RequestParam("resourceRangeIds") String resourceRangeIds) throws IllegalArgumentException{
        Set<ResourceRangePermissionWrapperDTO> wrappers = new HashSet<>();

        String[] arrResourceRangeIds = resourceRangeIds.split(",");

        for(String id : arrResourceRangeIds){
            try{
                long resourceRangeId = Long.parseLong(id);
                ResourceRangePermissionWrapper wrapper = this.permissionService.getByResourceRange(resourceRangeId);
                if(wrapper != null) {
                    ResourceRangePermissionWrapperDTO dto = resourceRangePermissionWrapperDTOConvertor.toResourceRangePermissionWrapperDTO(wrapper);
                    wrappers.add(dto);
                }
            }catch(NumberFormatException e){
                throw new IllegalArgumentException("ResourceRangeId必须是数字");
            }
        }
        return wrappers;
    }

    /**
     * 保存权限
     * @return int  插入的权限记录数
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @Transactional
    public int save(@RequestBody ResourceRangePermissionWrapperDTO wrapperDTO) {
        // 检查ResourceRange是否已经保存，如果没保存先保存
        ResourceRangeDTO rangeDTO = wrapperDTO.getResourceRangeDTO();
        ResourceRange range = rangeDTO.convertToResourceRange(this.roleService, this.resourceTypeService);
        if(rangeDTO.getId() == 0) {
            // 保存ResourceRange
            this.resourceRangeService.save(range);
        } else {
            // 如果resourceRange已经存在，先删除所有已有权限
            this.permissionService.deleteResourceRangeAllPermissions(wrapperDTO.getResourceRangeDTO().getId());
        }



        int insertNumber = 0;

        for (PermissionDTO pDTO : wrapperDTO.getPermissions()) {
            Optional<Permission> permission = Arrays.stream(bizConfig.getPermission().getArray())
                    .filter(pm -> pm.getMask() == pDTO.getMask())
                    .findFirst();
            if(permission.isPresent()) {
                try {
                    this.permissionService.insertPermission(range.getId(), permission.get());
                    insertNumber++;
                } catch (Exception ignored) {

                }
            }
        }
        return insertNumber;
    }

    /**
     * 删除权限
     * @param resourceRangeIds 需要删除权限的资源范围id。多个id用,分隔
     */
    @DeleteMapping(value = "/{resourceRangeIds}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "resourceRangeIds") String resourceRangeIds) {
        String[] arrResourceRangeIds = resourceRangeIds.split(",");
        // 删除所有权限
        for(String id : arrResourceRangeIds) {
            this.permissionService.deleteResourceRangeAllPermissions(Long.parseLong(id));
        }
        // 删除资源范围
        this.resourceRangeService.delete(arrResourceRangeIds);
    }
}
