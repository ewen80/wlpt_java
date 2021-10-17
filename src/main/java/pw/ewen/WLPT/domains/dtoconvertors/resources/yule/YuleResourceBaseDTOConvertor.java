package pw.ewen.WLPT.domains.dtoconvertors.resources.yule;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.DTOs.resources.ResourceCheckInDTO;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceBaseDTO;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceGwRoomDTO;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceGwWcDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.ResourceCheckInDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwWc;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.PermissionService;
import pw.ewen.WLPT.services.resources.yule.YuleResourceYyBaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021/10/6
 */
@Component
public class YuleResourceBaseDTOConvertor {
    private final YuleResourceYyBaseService yuleResourceYyBaseService;
    private final ResourceCheckInDTOConvertor resourceCheckInDTOConvertor;
    private final PermissionService permissionService;
    private final UserContext userContext;
    private final PermissionDTOConvertor permissionDTOConvertor;
    private final YuleResourceGwRoomDTOConvertor roomDTOConvertor;
    private final YuleResourceGwWcDTOConvertor wcDTOConvertor;
    private final YuleResourceYyDTOConvertor yyDTOConvertor;
    private final FieldAuditDTOConvertor fieldAuditDTOConvertor;

    public YuleResourceBaseDTOConvertor(YuleResourceYyBaseService yuleResourceYyBaseService, ResourceCheckInDTOConvertor resourceCheckInDTOConvertor, PermissionService permissionService, UserContext userContext, PermissionDTOConvertor permissionDTOConvertor, YuleResourceGwRoomDTOConvertor yuleResourceGwRoomDTOConvertor, YuleResourceGwWcDTOConvertor wcDTOConvertor, YuleResourceYyDTOConvertor yyDTOConvertor, FieldAuditDTOConvertor fieldAuditDTOConvertor) {
        this.yuleResourceYyBaseService = yuleResourceYyBaseService;
        this.resourceCheckInDTOConvertor = resourceCheckInDTOConvertor;
        this.permissionService = permissionService;
        this.userContext = userContext;
        this.permissionDTOConvertor = permissionDTOConvertor;
        this.roomDTOConvertor = yuleResourceGwRoomDTOConvertor;
        this.wcDTOConvertor = wcDTOConvertor;
        this.yyDTOConvertor = yyDTOConvertor;
        this.fieldAuditDTOConvertor = fieldAuditDTOConvertor;
    }

    public YuleResourceBase toYuleBase(YuleResourceBaseDTO dto) {
        YuleResourceBase yule = new YuleResourceBase();
        yule.setAqtd(dto.getAqtd());
        yule.setBh(dto.getBh());
        yule.setCsdz(dto.getCsdz());
        yule.setDwmc(dto.getDwmc());
        yule.setJyfw(dto.getJyfw());
        yule.setLxdh(dto.getLxdh());
        yule.setLxr(dto.getLxr());
        yule.setSbxm(dto.getSbxm());
        yule.setLxdh(dto.getLxdh());
        yule.setSymj(dto.getSymj());
        yule.setId(dto.getId());
        yule.setQxId(dto.getQxId());
        // 添加歌舞娱乐包房
        List<YuleResourceGwRoom> rooms = new ArrayList<>();
        dto.getRooms().forEach(roomDTO -> {
            YuleResourceGwRoom room = roomDTOConvertor.toRoom(roomDTO);
            rooms.add(room);
        });
        yule.setRooms(rooms);
        // 添加歌舞娱乐舞池
        List<YuleResourceGwWc> wcs = new ArrayList<>();
        dto.getWcs().forEach(wcDTO -> wcs.add(wcDTOConvertor.toWc(wcDTO)));
        yule.setWcs(wcs);

        if(dto.getYyBase() != null) {
            yule.setYyBase(yyDTOConvertor.toYyBase(dto.getYyBase()));
        }

        // 添加现场审核信息
        List<FieldAuditDTO> fieldAuditDTOS = dto.getFieldAudits();
        List<FieldAudit> fieldAudits = new ArrayList<>();
        for (FieldAuditDTO fieldAuditDTO : fieldAuditDTOS) {
            fieldAudits.add(this.fieldAuditDTOConvertor.toFieldAudit(fieldAuditDTO));
        }
        yule.setFieldAudits(fieldAudits);

        return  yule;
    }

    public YuleResourceBaseDTO toDTO(YuleResourceBase yule) {
        return this.toDTO(yule, true);
    }

    public YuleResourceBaseDTO toDTO(YuleResourceBase yule, boolean fetchLazy) {
        YuleResourceBaseDTO dto = new YuleResourceBaseDTO();
        dto.setAqtd(yule.getAqtd());
        dto.setBh(yule.getBh());
        dto.setCsdz(yule.getCsdz());
        dto.setDwmc(yule.getDwmc());
        dto.setJyfw(yule.getJyfw());
        dto.setLxr(yule.getLxr());
        dto.setLxdh(yule.getLxdh());
        dto.setSbxm(yule.getSbxm());
        dto.setSymj(yule.getSymj());
        dto.setId(yule.getId());
        dto.setQxId(yule.getQxId());

        if(!fetchLazy) {
            // 添加歌舞娱乐场所包房信息
            List<YuleResourceGwRoomDTO> roomDTOs = new ArrayList<>();
            yule.getRooms().forEach(room-> roomDTOs.add(roomDTOConvertor.toDTO(room)));
            dto.setRooms(roomDTOs);
            // 添加歌舞娱乐场所舞池信息
            List<YuleResourceGwWcDTO> wcDTOs = new ArrayList<>();
            yule.getWcs().forEach(wc-> wcDTOs.add(wcDTOConvertor.toDTO(wc)));
            dto.setWcs(wcDTOs);
            // 添加游艺场所信息
            if(yule.getYyBase() != null) {
                dto.setYyBase(yyDTOConvertor.toDTO(yule.getYyBase()));
            }
            // 添加登记信息
            ResourceCheckIn resourceCheckIn = yule.getResourceCheckIn();
            if(resourceCheckIn != null) {
                ResourceCheckInDTO resourceCheckInDTO = resourceCheckInDTOConvertor.toDTO(resourceCheckIn);
                dto.setResourceCheckIn(resourceCheckInDTO);
            }
            // 添加场地核查信息
            List<FieldAudit> fieldAudits = yule.getFieldAudits();
            List<FieldAuditDTO> fieldAuditDTOS = new ArrayList<>();
            for (FieldAudit fieldAudit : fieldAudits) {
                fieldAuditDTOS.add(this.fieldAuditDTOConvertor.toDTO(fieldAudit));
            }
            dto.setFieldAudits(fieldAuditDTOS);

            // 添加权限列表
            ResourceRangePermissionWrapper wrapper = permissionService.getByRoleAndResource(userContext.getCurrentUser().getRole().getId(), yule);
            List<PermissionDTO> permissionDTOS = wrapper.getPermissions().stream().map(permissionDTOConvertor::toDTO).collect(Collectors.toList());
            dto.setPermissions(permissionDTOS);
        }
        return dto;
    }
}
