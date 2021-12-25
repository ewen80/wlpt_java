package pw.ewen.WLPT.domains.DTOs.resources.yule;

import pw.ewen.WLPT.domains.DTOs.resources.BaseResourceDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wenliang on 2021/10/6
 */
public class YuleResourceBaseDTO extends BaseResourceDTO {

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
    /**
     * 歌舞娱乐场所包房信息
     */
    private List<YuleResourceGwRoomDTO> rooms = new ArrayList<>();
    /**
     * 歌舞娱乐场所舞池信息
     */
    private List<YuleResourceGwWcDTO> wcs = new ArrayList<>();

    /**
     * 游艺娱乐场所信息
     */
    private YuleResourceYyBaseDTO yyBase;

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

    public String getQxId() {
        return qxId;
    }

    public void setQxId(String qxId) {
        this.qxId = qxId;
    }

    public List<YuleResourceGwRoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<YuleResourceGwRoomDTO> rooms) {
        this.rooms = rooms;
    }

    public List<YuleResourceGwWcDTO> getWcs() {
        return wcs;
    }

    public void setWcs(List<YuleResourceGwWcDTO> wcs) {
        this.wcs = wcs;
    }

    public YuleResourceYyBaseDTO getYyBase() {
        return yyBase;
    }

    public void setYyBase(YuleResourceYyBaseDTO yyBase) {
        this.yyBase = yyBase;
    }
}
