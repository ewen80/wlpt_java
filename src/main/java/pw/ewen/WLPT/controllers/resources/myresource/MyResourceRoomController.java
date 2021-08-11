package pw.ewen.WLPT.controllers.resources.myresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.resources.myresource.MyResourceRoomDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.myresource.MyResourceRoomDTOConvertor;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResourceRoom;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.resources.myresource.MyResourceRoomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 我的资源-房间明细
 * @apiNote 范例
 */
@RestController
@RequestMapping(value = "/resources/myresource/room")
public class MyResourceRoomController {
    private final MyResourceRoomService myResourceRoomService;
    private final FileService fileService;
    private final MyResourceRoomDTOConvertor myResourceRoomDTOConvertor;
    private final BizConfig bizConfig;

    @Autowired
    public MyResourceRoomController(MyResourceRoomService myResourceRoomService,
                                    FileService fileService,
                                    MyResourceRoomDTOConvertor myResourceRoomDTOConvertor, BizConfig bizConfig) {
        this.myResourceRoomService = myResourceRoomService;
        this.fileService = fileService;
        this.myResourceRoomDTOConvertor = myResourceRoomDTOConvertor;
        this.bizConfig = bizConfig;
    }

    /**
     * 保存
     * @param dto 房间DTO
     */
    @PostMapping()
    public MyResourceRoomDTO save(@RequestBody MyResourceRoomDTO dto) {
        MyResourceRoom room = myResourceRoomDTOConvertor.toRoom(dto);
        this.myResourceRoomService.save(room);
        return myResourceRoomDTOConvertor.toDTO(room);
    }

    /**
     * 获取一个房间明细
     * @param roomId 房间id
     * @apiNote 如果找不到返回404
     */
    @GetMapping("/{roomId}")
    public ResponseEntity<MyResourceRoomDTO> getOne(@PathVariable("roomId") long roomId) {
        MyResourceRoom room = this.myResourceRoomService.findOne(roomId);
        return room == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(myResourceRoomDTOConvertor.toDTO(room), HttpStatus.OK);
    }

    /**
     * 获取我的资源对应的房间
     * @param myResourceId 我的资源id
     */
    @GetMapping()
    public List<MyResourceRoomDTO> getRooms(@RequestParam("myResourceId") long myResourceId) {
        List<MyResourceRoom> rooms = this.myResourceRoomService.findByMyResourceId(myResourceId);
        List<MyResourceRoomDTO> roomDTOS = new ArrayList<>();
        rooms.forEach( (room) -> {
            roomDTOS.add(myResourceRoomDTOConvertor.toDTO(room));
        });
        return roomDTOS;
    }

    /**
     * 删除房间
     * @param roomIds 房间id,多个房间id用逗号分隔
     */
    @DeleteMapping("/{roomIds}")
    public void delete(@PathVariable("roomIds") String roomIds) {
        List<Long> roomIdList = Arrays.stream(roomIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
        List<MyResourceRoom> rooms = myResourceRoomService.findByIdIn(roomIdList);
        rooms.forEach( room -> {
           List<Attachment> attachments = room.getAttachments();
           attachments.forEach( attachment -> {
               // 物理删除文件
               fileService.delete(bizConfig.getFile().getFileUploadRootPath() + attachment.getPath());
           });
           // 删除数据库数据
           myResourceRoomService.delete(room.getId());
        });

    }
}
