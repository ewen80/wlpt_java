package pw.ewen.WLPT.domains.dtoconvertors.resources.myresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.resources.ResourceCheckInDTO;
import pw.ewen.WLPT.domains.DTOs.resources.myresource.MyResourceDTO;
import pw.ewen.WLPT.domains.DTOs.resources.myresource.MyResourceRoomDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.ResourceCheckInDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.SignatureDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResource;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResourceRoom;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.AttachmentService;
import pw.ewen.WLPT.services.PermissionService;
import pw.ewen.WLPT.services.resources.myresource.MyResourceService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021/5/18
 */
@Component
public class MyResourceDTOConvertor {

    private final PermissionService permissionService;
    private final PermissionDTOConvertor permissionDTOConvertor;
    private final ResourceCheckInDTOConvertor resourceCheckInDTOConvertor;
    private final MyResourceRoomDTOConvertor myResourceRoomDTOConvertor;
    private final SignatureDTOConvertor signatureDTOConvertor;
    private final UserContext userContext;

    @Autowired
    public MyResourceDTOConvertor(PermissionService permissionService,
                                  PermissionDTOConvertor permissionDTOConvertor,
                                  ResourceCheckInDTOConvertor resourceCheckInDTOConvertor,
                                  MyResourceRoomDTOConvertor myResourceRoomDTOConvertor,
                                  SignatureDTOConvertor signatureDTOConvertor, UserContext userContext) {
        this.permissionService = permissionService;
        this.signatureDTOConvertor = signatureDTOConvertor;
        this.userContext = userContext;
        this.permissionDTOConvertor = permissionDTOConvertor;
        this.resourceCheckInDTOConvertor = resourceCheckInDTOConvertor;
        this.myResourceRoomDTOConvertor = myResourceRoomDTOConvertor;
    }

    /**
     * 转换DTO，所有关联信息全部加载
     * @param myResource 资源实体
     */
    public MyResourceDTO toDTO(MyResource myResource) {
        return this.toDTO(myResource, true);
    }

    /**
     * 转换到DTO
     * @param myResource 资源实体
     * @param fetchLazy 是否是懒加载，懒加载将不加载房间，附件，登记，权限等信息，仅转换本身基本字段
     */
    public MyResourceDTO toDTO(MyResource myResource, boolean fetchLazy) {
        MyResourceDTO dto = new MyResourceDTO();
        dto.setId(myResource.getId());
        dto.setChangdiName(myResource.getChangdiName());
        dto.setChangdiAddress(myResource.getChangdiAddress());
        dto.setQxId(myResource.getQxId());

        if(!fetchLazy) {
            // 添加签名信息
            Signature sign = myResource.getSign();
            if(sign != null) {
                SignatureDTO signatureDTO = signatureDTOConvertor.toDTO(sign);
                dto.setSign(signatureDTO);
            }
            // 添加房间
            List<MyResourceRoom> rooms = myResource.getRooms();
            List<MyResourceRoomDTO> roomDTOS = new ArrayList<>();
            rooms.forEach( (room -> roomDTOS.add(myResourceRoomDTOConvertor.toDTO(room))));
            dto.setRooms(roomDTOS);

            // 添加附件
            dto.setAttachments(myResource.getAttachments());

            // 添加登记信息
            ResourceCheckIn resourceCheckIn = myResource.getResourceCheckIn();
            if(resourceCheckIn != null) {
                ResourceCheckInDTO resourceCheckInDTO = resourceCheckInDTOConvertor.toDTO(resourceCheckIn);
                dto.setResourceCheckIn(resourceCheckInDTO);
            }

            // 添加权限列表
            ResourceRangePermissionWrapper wrapper = permissionService.getByRoleAndResource(userContext.getCurrentUser().getRole().getId(), myResource);
            List<PermissionDTO> permissionDTOS = wrapper.getPermissions().stream().map(permissionDTOConvertor::toDTO).collect(Collectors.toList());
            dto.setPermissions(permissionDTOS);
        }

        return dto;
    }

    public MyResource toMyResource(MyResourceDTO dto) {
        MyResource myResource = new MyResource();
        myResource.setId(dto.getId());
        myResource.setChangdiName(dto.getChangdiName());
        myResource.setChangdiAddress(dto.getChangdiAddress());
        myResource.setQxId(dto.getQxId());

        // 添加签名信息
        SignatureDTO signatureDTO = dto.getSign();
        if(signatureDTO != null) {
            Signature sign = signatureDTOConvertor.toSignature(signatureDTO);
            myResource.setSign(sign);
        }
        // 添加房间
        List<MyResourceRoom> rooms = new ArrayList<>();
        dto.getRooms().forEach( (roomDTO -> {
            MyResourceRoom room = myResourceRoomDTOConvertor.toRoom(roomDTO);
            rooms.add(room);
        }));
        myResource.setRooms(rooms);
        // 添加附件
        myResource.setAttachments(dto.getAttachments());
        // 添加登记信息
        ResourceCheckInDTO resourceCheckInDTO = dto.getResourceCheckIn();
        if(resourceCheckInDTO != null) {
            ResourceCheckIn resourceCheckIn = resourceCheckInDTOConvertor.toResourceCheckIn(resourceCheckInDTO);
            myResource.setResourceCheckIn(resourceCheckIn);
        }
        return myResource;
    }
}
