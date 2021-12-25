package pw.ewen.WLPT.domains.entities.resources.artifactshop;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * created by wenliang on 2021-12-24
 * 文物商店
 */
@Entity
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArtifactShopResource extends BaseResource implements Serializable {

    private static final long serialVersionUID = -303746413527702698L;
    /**
     * 申请单位
     */
    private String sqdw;
    /**
     * 法人
     */
    private String faren;
    /**
     * 场所地址
     */
    private String csdz;
    /**
     * 联系人
     */
    private String lxr;
    /**
     * 申办项目
     */
    private String sbxm;
    /**
     * 联系电话
     */
    private String lxdh;

    public String getSqdw() {
        return sqdw;
    }

    public void setSqdw(String sqdw) {
        this.sqdw = sqdw;
    }

    public String getFaren() {
        return faren;
    }

    public void setFaren(String faren) {
        this.faren = faren;
    }

    public String getCsdz() {
        return csdz;
    }

    public void setCsdz(String csdz) {
        this.csdz = csdz;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getSbxm() {
        return sbxm;
    }

    public void setSbxm(String sbxm) {
        this.sbxm = sbxm;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }
}
