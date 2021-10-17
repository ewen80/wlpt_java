package pw.ewen.WLPT.domains.dtoconvertors.resources.yule;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceGwRoomDTO;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.services.resources.yule.YuleResourceBaseService;

/**
 * created by wenliang on 2021/10/6
 */
@Component
public class YuleResourceGwRoomDTOConvertor {
    private final YuleResourceBaseService yuleResourceBaseService;

    public YuleResourceGwRoomDTOConvertor(YuleResourceBaseService yuleResourceBaseService) {
        this.yuleResourceBaseService = yuleResourceBaseService;
    }

    public YuleResourceGwRoom toRoom(YuleResourceGwRoomDTO dto) {
        YuleResourceGwRoom room = new YuleResourceGwRoom();
        room.setId(dto.getId());
        room.setArea(dto.getArea());
        room.setToilet(dto.isToilet());
        room.setWindow(dto.isWindow());
        room.setInnerLock(dto.isInnerLock());
        room.setName(dto.getName());
        room.setHdrs(dto.getHdrs());
        room.setOneThousandSongs(dto.isOneThousandSongs());
        yuleResourceBaseService.findOne(dto.getYuleResourceBaseId()).ifPresent(room::setYuleResourceBase);
        return room;
    }

    public YuleResourceGwRoomDTO toDTO(YuleResourceGwRoom room) {
        YuleResourceGwRoomDTO dto = new YuleResourceGwRoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setArea(room.getArea());
        dto.setToilet(room.isToilet());
        dto.setWindow(room.isWindow());
        dto.setInnerLock(room.isInnerLock());
        dto.setHdrs(room.getHdrs());
        dto.setOneThousandSongs(room.isOneThousandSongs());
        dto.setYuleResourceBaseId(room.getYuleResourceBase().getId());
        return dto;
    }
}
