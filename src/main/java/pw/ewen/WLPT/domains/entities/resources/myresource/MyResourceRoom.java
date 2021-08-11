package pw.ewen.WLPT.domains.entities.resources.myresource;

import pw.ewen.WLPT.domains.entities.Attachment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * created by wenliang on 2021/5/10
 * 场地包房信息
 */
@Entity
public class MyResourceRoom {

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String name;
    @Column
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MyResource myResource;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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

    public MyResource getMyResource() {
        return myResource;
    }

    public void setMyResource(MyResource myResource) {
        this.myResource = myResource;
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
