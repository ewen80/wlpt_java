package pw.ewen.WLPT.domains.DTOs.resources;

import pw.ewen.WLPT.domains.entities.User;

import java.time.LocalDateTime;

/**
 * created by wenliang on 2021-05-25
 */
public class ResourceFinishDTO {

    /**
     * 办结信息id
     */
    private long id;
    /**
     * 办结时间
     */
    private String finishDateTime;
    /**
     * 办结人id
     */
    private String finishUserId;
    /**
     * 是否办结
     */
    private boolean finished;

    public ResourceFinishDTO(long id, String finishDateTime, String finishUserId, boolean finished) {
        this.id = id;
        this.finishDateTime = finishDateTime;
        this.finishUserId = finishUserId;
        this.finished = finished;
    }

    public ResourceFinishDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(String finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public String getFinishUserId() {
        return finishUserId;
    }

    public void setFinishUserId(String finishUserId) {
        this.finishUserId = finishUserId;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
