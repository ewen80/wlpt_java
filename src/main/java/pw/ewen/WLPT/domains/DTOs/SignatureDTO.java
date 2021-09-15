package pw.ewen.WLPT.domains.DTOs;

import pw.ewen.WLPT.domains.entities.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * created by wenliang on 2021-08-09
 */
public class SignatureDTO {

    /**
     * id
     */
    private long id;
    /**
     * 图片数据
     * */
    private String signBase64;
    /**
     * 姓名
     */
    private String name;
    /**
     * 创建日期
     */
    private String createdAt;
    /**
     * 签名图片后缀名
     * @apiNote 后缀名前不要加.
     */
    private String imageExtention;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSignBase64() {
        return signBase64;
    }

    public void setSignBase64(String signBase64) {
        this.signBase64 = signBase64;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageExtention() {
        return imageExtention;
    }

    public void setImageExtention(String imageExtention) {
        this.imageExtention = imageExtention;
    }
}
