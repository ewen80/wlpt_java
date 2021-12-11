package pw.ewen.WLPT.domains.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

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
//    @Column(columnDefinition = "BINARY(16)")
//    private UUID id;
    private long id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
