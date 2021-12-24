package pw.ewen.WLPT.domains.dtoconvertors.resources.vod;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.vod.VodResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.ResourceCheckInDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.vod.VodResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.PermissionService;

/**
 * created by wenliang on 2021-12-21
 */
@Component
public class VodResourceDTOConvertor extends DTOBaseConvertor<VodResource, VodResourceDTO> {

    protected VodResourceDTOConvertor(FieldAuditDTOConvertor fieldAuditDTOConvertor, ResourceCheckInDTOConvertor resourceCheckInDTOConvertor, PermissionService permissionService, PermissionDTOConvertor permissionDTOConvertor, UserContext userContext) {
        super(fieldAuditDTOConvertor, resourceCheckInDTOConvertor, permissionService, permissionDTOConvertor, userContext);
    }

    public VodResourceDTO toDTO(VodResource vod) {
        return toDTO(vod, true);
    }

    public VodResourceDTO toDTO(VodResource vod, boolean fetchLazy) {
        VodResourceDTO dto = new VodResourceDTO();

        dto.setId(vod.getId());
        dto.setBh(vod.getBh());
        dto.setDetectLocation(vod.getDetectLocation());
        dto.setDetectBasis(vod.getDetectLocation());
        dto.setDetectOverview(vod.getDetectBasis());
        dto.setDetectUnit(vod.getDetectUnit());
        dto.setDeviceModel(vod.getDeviceModel());
        dto.setManufacturer(vod.getManufacturer());
        dto.setDeviceName(vod.getDeviceName());
        dto.setSamplingMethod(vod.getSamplingMethod());
        dto.setSysExplanation(vod.getSysExplanation());
        dto.setSysName(vod.getSysName());

        this.setReadedInfoToDTO(vod, dto);
        if(!fetchLazy) this.setExtraInfoToDTO(vod, dto);
        return dto;
    }

    public VodResource toVodResource(VodResourceDTO dto) {
        VodResource vod = new VodResource();
        this.setExtraInfoToResource(dto, vod);
        vod.setId(dto.getId());
        vod.setBh(dto.getBh());
        vod.setDetectBasis(dto.getDetectBasis());
        vod.setDetectLocation(dto.getDetectLocation());
        vod.setDetectOverview(dto.getDetectOverview());
        vod.setDetectUnit(dto.getDetectUnit());
        vod.setManufacturer(dto.getManufacturer());
        vod.setDeviceModel(dto.getDeviceModel());
        vod.setDeviceName(dto.getDeviceName());
        vod.setSamplingMethod(dto.getSamplingMethod());
        vod.setSysExplanation(dto.getSysExplanation());
        vod.setSysName(dto.getSysName());

        return vod;
    }
}
