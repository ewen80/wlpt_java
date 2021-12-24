package pw.ewen.WLPT.domains.dtoconvertors.resources;

import org.springframework.security.acls.model.Permission;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.resources.BaseResourceDTO;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.DTOs.resources.ResourceCheckInDTO;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceReadInfo;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.PermissionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021-12-21
 */
public abstract class DTOBaseConvertor<T extends BaseResource, D extends BaseResourceDTO> {

    private final FieldAuditDTOConvertor fieldAuditDTOConvertor;
    private final ResourceCheckInDTOConvertor resourceCheckInDTOConvertor;
    private final PermissionService permissionService;
    private final PermissionDTOConvertor permissionDTOConvertor;
    private final UserContext userContext;

    protected DTOBaseConvertor(FieldAuditDTOConvertor fieldAuditDTOConvertor, ResourceCheckInDTOConvertor resourceCheckInDTOConvertor, PermissionService permissionService, PermissionDTOConvertor permissionDTOConvertor, UserContext userContext) {
        this.fieldAuditDTOConvertor = fieldAuditDTOConvertor;
        this.resourceCheckInDTOConvertor = resourceCheckInDTOConvertor;
        this.permissionService = permissionService;
        this.permissionDTOConvertor = permissionDTOConvertor;
        this.userContext = userContext;
    }

    /**
     * 设置额外信息到DTO，如场地核查信息，资源登记信息，权限信息等
     * @param t 资源对象
     * @param dto dto对象
     */
    public void setExtraInfoToDTO(T t, D dto) {
        // 添加场地核查信息
        List<FieldAudit> fieldAudits = t.getFieldAudits();
        List<FieldAuditDTO> fieldAuditDTOS = new ArrayList<>();
        for (FieldAudit fieldAudit : fieldAudits) {
            fieldAuditDTOS.add(this.fieldAuditDTOConvertor.toDTO(fieldAudit));
        }
        dto.setFieldAudits(fieldAuditDTOS);

        // 添加资源登记信息
        ResourceCheckIn resourceCheckIn = t.getResourceCheckIn();
        if(resourceCheckIn != null) {
            ResourceCheckInDTO resourceCheckInDTO = resourceCheckInDTOConvertor.toDTO(resourceCheckIn);
            dto.setResourceCheckIn(resourceCheckInDTO);
        }

        // 添加权限列表
        Set<Permission> permissions =  permissionService.getPermissionsByRolesAndResource(userContext.getCurrentUser().getCurrentRole(), t);
        List<PermissionDTO> permissionDTOs = permissions.stream().map(permissionDTOConvertor::toDTO).collect(Collectors.toList());
        dto.setPermissions(permissionDTOs);
    }

    /**
     * 设置资源是否已读信息到DTO
     * @param t 资源对象
     * @param dto dto对象
     */
    public void setReadedInfoToDTO(T t, D dto){
        // 是否已读
        List<ResourceReadInfo> readedInfos = t.getReadInfoList();
        boolean haveReaded = readedInfos.stream().anyMatch(readInfo -> Objects.equals(readInfo.getUser().getId(), userContext.getCurrentUser().getId()));
        dto.setReaded(haveReaded);
    }

    /**
     * 设置额外信息到资源对象
     * @param dto dto对象
     * @param t 资源对象
     */
    public void setExtraInfoToResource(D dto, T t) {
        // 添加现场审核信息
        List<FieldAuditDTO> fieldAuditDTOS = dto.getFieldAudits();
        List<FieldAudit> fieldAudits = new ArrayList<>();
        for (FieldAuditDTO fieldAuditDTO : fieldAuditDTOS) {
            fieldAudits.add(this.fieldAuditDTOConvertor.toFieldAudit(fieldAuditDTO));
        }
        t.setFieldAudits(fieldAudits);

        // 添加登记信息
        ResourceCheckInDTO resourceCheckInDTO = dto.getResourceCheckIn();
        if(resourceCheckInDTO != null) {
            ResourceCheckIn resourceCheckIn = resourceCheckInDTOConvertor.toResourceCheckIn(resourceCheckInDTO);
            t.setResourceCheckIn(resourceCheckIn);
        }
    }
}
