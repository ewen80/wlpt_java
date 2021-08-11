package pw.ewen.WLPT.domains.entities.resources;

import pw.ewen.WLPT.domains.entities.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * created by wenliang on 2021-08-09
 * 签名信息
 */
@Entity
public class Signature {

    @Id
    @GeneratedValue
    private long id;
    /**
     * 图片数据
     */
    @Lob
    private byte[] bytes;
    /**
     * 姓名
     */
    private String name;
    /**
     * 创建日期
     */
    private LocalDateTime createdAt;
    /**
     * 签名图片后缀名
     */
    private String imageExtention;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

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

    public String getImageExtention() {
        return imageExtention;
    }

    public void setImageExtention(String imageExtention) {
        this.imageExtention = imageExtention;
    }
}
