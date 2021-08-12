package pw.ewen.WLPT.domains.dtoconvertors.resources.myresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.myresource.MyResourceRoomDTO;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResourceRoom;
import pw.ewen.WLPT.services.AttachmentService;
import pw.ewen.WLPT.services.resources.myresource.MyResourceService;

import java.util.ArrayList;
import java.util.UUID;

/**
 * created by wenliang on 2021/5/10
 */
@Component
public class MyResourceRoomDTOConvertor {

    private final MyResourceService myResourceService;
    private final AttachmentService attachmentService;

    @Autowired
    public MyResourceRoomDTOConvertor(MyResourceService myResourceService, AttachmentService attachmentService) {
        this.myResourceService = myResourceService;
        this.attachmentService = attachmentService;
    }

    public MyResourceRoom toRoom(MyResourceRoomDTO dto) {
        MyResourceRoom room = new MyResourceRoom();
        room.setId(dto.getId());
        room.setName(dto.getName());
        room.setDescription(dto.getDescription());
        room.setMyResource(myResourceService.findOne(dto.getMyResourceId()).orElse(null));
        room.setAttachments(dto.getAttachments());
        return room;
    }

    public MyResourceRoomDTO toDTO(MyResourceRoom room) {
        MyResourceRoomDTO dto = new MyResourceRoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setDescription(room.getDescription());
        dto.setMyResourceId(room.getMyResource().getId());
        dto.setAttachments(room.getAttachments());
        return dto;
    }
}
