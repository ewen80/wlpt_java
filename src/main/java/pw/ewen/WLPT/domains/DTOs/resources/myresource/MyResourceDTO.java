package pw.ewen.WLPT.domains.DTOs.resources.myresource;

import pw.ewen.WLPT.domains.DTOs.RoleDTO;
import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.resources.BaseResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.RoleDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.myresource.MyResourceDTOConvertor;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResource;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.resources.myresource.MyResourceService;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wenliang on 2021/5/10
 */
public class MyResourceDTO extends BaseResourceDTO {

    /**
     * 场地名称
     */
    private String changdiName;
    /**
     * 场地地址
     */
    private String changdiAddress;
    /**
     * 各区id
     */
    private String qxId;
    /**
     * 签名信息
     */
    private SignatureDTO sign;

    /**
     * 附件列表
     */
    private List<Attachment> attachments = new ArrayList<>();
    /**
     * 房间明细
     */
    private List<MyResourceRoomDTO> rooms = new ArrayList<>();

    public String getChangdiName() {
        return changdiName;
    }

    public void setChangdiName(String changdiName) {
        this.changdiName = changdiName;
    }

    public String getChangdiAddress() {
        return changdiAddress;
    }

    public void setChangdiAddress(String changdiAddress) {
        this.changdiAddress = changdiAddress;
    }

    public String getQxId() {
        return qxId;
    }

    public void setQxId(String qxId) {
        this.qxId = qxId;
    }

    public List<MyResourceRoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<MyResourceRoomDTO> rooms) {
        this.rooms = rooms;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public SignatureDTO getSign() {
        return sign;
    }

    public void setSign(SignatureDTO sign) {
        this.sign = sign;
    }
}
