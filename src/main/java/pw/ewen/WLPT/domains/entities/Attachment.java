package pw.ewen.WLPT.domains.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * created by wenliang on 2021-05-17
 * 附件
 */
@Entity
public class Attachment implements Serializable {
    private static final long serialVersionUID = 7573741503334955848L;

    /**
     * 附件id
     */
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    /**
     * 附件名称
     */
    private String name;
    /**
     * 附件路径
     */
    private String path;
    /**
     * 附件添加日期
     */
    private String date;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        this.date = formatter.parse(date, LocalDateTime::from);
        this.date = date;
    }
}
