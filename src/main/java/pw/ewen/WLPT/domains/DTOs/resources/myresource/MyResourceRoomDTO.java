package pw.ewen.WLPT.domains.DTOs.resources.myresource;

import pw.ewen.WLPT.domains.DTOs.resources.BaseResourceDTO;
import pw.ewen.WLPT.domains.entities.Attachment;

import java.util.*;

/**
 * created by wenliang on 2021/5/10
 */
public class MyResourceRoomDTO {

    /**
     * 房间id
     */
    private long id;
    /**
     * 房间名称
     */
    private String name;
    /**
     * 房间描述
     */
    private String description;
    /**
     * 我的资源id
     */
    private long myResourceId;
    /**
     * 附件列表
     */
    private List<Attachment> attachments = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMyResourceId() {
        return myResourceId;
    }

    public void setMyResourceId(long myResourceId) {
        this.myResourceId = myResourceId;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
