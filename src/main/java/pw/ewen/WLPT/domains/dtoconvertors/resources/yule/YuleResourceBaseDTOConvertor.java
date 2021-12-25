package pw.ewen.WLPT.domains.dtoconvertors.resources.yule;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceBaseDTO;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceGwRoomDTO;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceGwWcDTO;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.ResourceCheckInDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwWc;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.PermissionService;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wenliang on 2021/10/6
 */
@Component
public class YuleResourceBaseDTOConvertor extends DTOBaseConvertor<YuleResourceBase, YuleResourceBaseDTO> {

    private final YuleResourceGwRoomDTOConvertor roomDTOConvertor;
    private final YuleResourceGwWcDTOConvertor wcDTOConvertor;
    private final YuleResourceYyDTOConvertor yyDTOConvertor;

    protected YuleResourceBaseDTOConvertor(FieldAuditDTOConvertor fieldAuditDTOConvertor, ResourceCheckInDTOConvertor resourceCheckInDTOConvertor, PermissionService permissionService, PermissionDTOConvertor permissionDTOConvertor, UserContext userContext, YuleResourceGwRoomDTOConvertor roomDTOConvertor, YuleResourceGwWcDTOConvertor wcDTOConvertor, YuleResourceYyDTOConvertor yyDTOConvertor) {
        super(fieldAuditDTOConvertor, resourceCheckInDTOConvertor, permissionService, permissionDTOConvertor, userContext);
        this.roomDTOConvertor = roomDTOConvertor;
        this.wcDTOConvertor = wcDTOConvertor;
        this.yyDTOConvertor = yyDTOConvertor;
    }

    public YuleResourceBase toResource(YuleResourceBaseDTO dto) {
        YuleResourceBase yule = new YuleResourceBase();
        yule.setId(dto.getId());
        yule.setBh(dto.getBh());
        yule.setAqtd(dto.getAqtd());
        yule.setCsdz(dto.getCsdz());
        yule.setDwmc(dto.getDwmc());
        yule.setJyfw(dto.getJyfw());
        yule.setLxdh(dto.getLxdh());
        yule.setLxr(dto.getLxr());
        yule.setSbxm(dto.getSbxm());
        yule.setLxdh(dto.getLxdh());
        yule.setSymj(dto.getSymj());
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

        this.setExtraInfoToResource(dto, yule);

        return  yule;
    }

    public YuleResourceBaseDTO toDTO(YuleResourceBase yule) {
        return this.toDTO(yule, true);
    }

    public YuleResourceBaseDTO toDTO(YuleResourceBase yule, boolean fetchLazy) {
        YuleResourceBaseDTO dto = new YuleResourceBaseDTO();
        dto.setId(yule.getId());
        dto.setBh(yule.getBh());
        dto.setAqtd(yule.getAqtd());
        dto.setCsdz(yule.getCsdz());
        dto.setDwmc(yule.getDwmc());
        dto.setJyfw(yule.getJyfw());
        dto.setLxr(yule.getLxr());
        dto.setLxdh(yule.getLxdh());
        dto.setSbxm(yule.getSbxm());
        dto.setSymj(yule.getSymj());
        dto.setQxId(yule.getQxId());

        // 是否已读
        this.setReadedInfoToDTO(yule, dto);

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

            this.setExtraInfoToDTO(yule, dto);
        }
        return dto;
    }
}
