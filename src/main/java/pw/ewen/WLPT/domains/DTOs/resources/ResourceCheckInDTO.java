package pw.ewen.WLPT.domains.DTOs.resources;

/**
 * created by wenliang on 2021-05-25
 */
public class ResourceCheckInDTO {

    /**
     * 登记信息id
     */
    private String id;
    /**
     * 登记时间
     */
    private String createdDateTime;
    /**
     * 登记人id
     */
    private String createdUserId;
    /**
     * 是否办结
     */
    private boolean finished;
    /**
     * 办结信息
     */
    private ResourceFinishDTO resourceFinish;

    public ResourceCheckInDTO(String id, String createdDateTime, String createdUserId, boolean finished, ResourceFinishDTO resourceFinish) {
        this.id = id;
        this.finished = finished;
        this.createdDateTime = createdDateTime;
        this.createdUserId = createdUserId;
        this.resourceFinish = resourceFinish;
    }

    public ResourceCheckInDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public ResourceFinishDTO getResourceFinish() {
        return resourceFinish;
    }

    public void setResourceFinish(ResourceFinishDTO resourceFinish) {
        this.resourceFinish = resourceFinish;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
