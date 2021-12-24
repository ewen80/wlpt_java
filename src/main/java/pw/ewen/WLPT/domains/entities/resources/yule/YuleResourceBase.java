package pw.ewen.WLPT.domains.entities.resources.yule;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * created by wenliang on 2021/10/6
 * 娱乐场所设立实地检查表基础表
 */
@Entity
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YuleResourceBase extends BaseResource implements Serializable {
    private static final long serialVersionUID = -201238539135369721L;
    /**
     * 编号
     */
    private String bh;
    /**
     * 各区Id
     */
    private String qxId;
    /**
     * 单位名称
     */
    private String dwmc;
    /**
     * 场所地址
     */
    private String csdz;
    /**
     * 经营范围
     */
    private String jyfw;
    /**
     * 申办项目
     */
    private String sbxm;
    /**
     * 使用面积
     */
    private float symj;
    /**
     * 安全通道
     */
    private String aqtd;
    /**
     * 联系人
     */
    private String lxr;
    /**
     * 联系电话
     */
    private String lxdh;

    @OneToMany(mappedBy = "yuleResourceBase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YuleResourceGwRoom> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "yuleResourceBase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YuleResourceGwWc> wcs = new ArrayList<>();

    @OneToOne(mappedBy = "yuleResourceBase", cascade = CascadeType.ALL, orphanRemoval = true)
    private YuleResourceYyBase yyBase;

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getCsdz() {
        return csdz;
    }

    public void setCsdz(String csdz) {
        this.csdz = csdz;
    }

    public String getJyfw() {
        return jyfw;
    }

    public void setJyfw(String jyfw) {
        this.jyfw = jyfw;
    }

    public String getSbxm() {
        return sbxm;
    }

    public void setSbxm(String sbxm) {
        this.sbxm = sbxm;
    }

    public float getSymj() {
        return symj;
    }

    public void setSymj(float symj) {
        this.symj = symj;
    }

    public String getAqtd() {
        return aqtd;
    }

    public void setAqtd(String aqtd) {
        this.aqtd = aqtd;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public YuleResourceYyBase getYyBase() {
        return yyBase;
    }

    public void setYyBase(YuleResourceYyBase yyBase) {
        this.yyBase = yyBase;
    }

    public String getQxId() {
        return qxId;
    }

    public void setQxId(String qxId) {
        this.qxId = qxId;
    }

    public List<YuleResourceGwRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<YuleResourceGwRoom> rooms) {
        this.rooms = rooms;
    }

    public List<YuleResourceGwWc> getWcs() {
        return wcs;
    }

    public void setWcs(List<YuleResourceGwWc> wcs) {
        this.wcs = wcs;
    }

}
