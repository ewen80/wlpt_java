package pw.ewen.WLPT.domains.entities.resources.myresource;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.Signature;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public  class MyResource extends BaseResource implements Serializable {

    private static final long serialVersionUID = 5485988002718505657L;

    private String changdiName;
    private String changdiAddress;
    private String qxId = "0";

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn
    private Signature sign;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "myResource", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<MyResourceRoom> rooms = new ArrayList<>();

    public MyResource() {
        super();
    }

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

    public List<MyResourceRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<MyResourceRoom> rooms) {
        this.rooms = rooms;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Signature getSign() {
        return sign;
    }

    public void setSign(Signature sign) {
        this.sign = sign;
    }
}
