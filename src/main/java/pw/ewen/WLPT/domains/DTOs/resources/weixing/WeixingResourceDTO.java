package pw.ewen.WLPT.domains.DTOs.resources.weixing;

import pw.ewen.WLPT.domains.DTOs.resources.BaseResourceDTO;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wenliang on 2021-07-21
 */
public class WeixingResourceDTO extends BaseResourceDTO {

    /**
     * 编号
     */
    private String bh;
    /**
     * 申请单位
     */
    private String sqdw;
    /**
     * 各区Id
     */
    private String qxId;
    /**
     * 申请类型
     */
    private String sqlx;
    /**
     * 安装地址
     **/
    private String azdz;
    /**
     * 办公电话
     */
    private String bgdh;
    /**
     * 邮政编码
     */
    private String yzbm;
    /**
     * 负责人
     */
    private String fzr;
    /**
     * 负责人手机
     */
    private String fzrsj;
    /**
     * 机房位置
     */
    private String jfwz;
    /**
     * 天线位置
     */
    private String txwz;
    /**
     * 天线数量
     */
    private int txsl;
    /**
     * 天线类型
     */
    private String txlx;
    /**
     * 境内收视节目源
     */
    private String jnssjmy;
    /**
     * 卫星传输方式
     */
    private String wxcsfs;
    /**
     * 信号调制方式
     */
    private String xhtzfs;
    /**
     * 收视内容
     */
    private String ssnr;
    /**
     * 设计安装单位名称
     */
    private String sjazdwmc;
    /**
     * 卫星设施安装许可证号
     */
    private String wxssazxkzh;
    /**
     * 楼盘名
     */
    private String lpm;
    /**
     * 楼层
     */
    private String lc;
    /**
     * 终端数
     */
    private int zds;
    /**
     * 卫星名称
     */
    private String wxmc;
    /**
     * 收视单位类型
     */
    private String ssdwlx;


    private List<FieldAuditDTO> fieldAudits = new ArrayList<>();


    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getSqdw() {
        return sqdw;
    }

    public void setSqdw(String sqdw) {
        this.sqdw = sqdw;
    }

    public String getQxId() {
        return qxId;
    }

    public void setQxId(String qxId) {
        this.qxId = qxId;
    }

    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    public String getAzdz() {
        return azdz;
    }

    public void setAzdz(String azdz) {
        this.azdz = azdz;
    }

    public String getBgdh() {
        return bgdh;
    }

    public void setBgdh(String bgdh) {
        this.bgdh = bgdh;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getFzrsj() {
        return fzrsj;
    }

    public void setFzrsj(String fzrsj) {
        this.fzrsj = fzrsj;
    }

    public String getJfwz() {
        return jfwz;
    }

    public void setJfwz(String jfwz) {
        this.jfwz = jfwz;
    }

    public String getTxwz() {
        return txwz;
    }

    public String getXhtzfs() {
        return xhtzfs;
    }

    public void setXhtzfs(String xhtzfs) {
        this.xhtzfs = xhtzfs;
    }

    public void setTxwz(String txwz) {
        this.txwz = txwz;
    }

    public int getTxsl() {
        return txsl;
    }

    public void setTxsl(int txsl) {
        this.txsl = txsl;
    }

    public String getTxlx() {
        return txlx;
    }

    public void setTxlx(String txlx) {
        this.txlx = txlx;
    }

    public String getJnssjmy() {
        return jnssjmy;
    }

    public void setJnssjmy(String jnssjmy) {
        this.jnssjmy = jnssjmy;
    }

    public String getWxcsfs() {
        return wxcsfs;
    }

    public void setWxcsfs(String wxcsfs) {
        this.wxcsfs = wxcsfs;
    }

    public String getSsnr() {
        return ssnr;
    }

    public void setSsnr(String ssnr) {
        this.ssnr = ssnr;
    }

    public String getSjazdwmc() {
        return sjazdwmc;
    }

    public void setSjazdwmc(String sjazdwmc) {
        this.sjazdwmc = sjazdwmc;
    }

    public String getWxssazxkzh() {
        return wxssazxkzh;
    }

    public void setWxssazxkzh(String wxssazxkzh) {
        this.wxssazxkzh = wxssazxkzh;
    }

    public String getLpm() {
        return lpm;
    }

    public void setLpm(String lpm) {
        this.lpm = lpm;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public int getZds() {
        return zds;
    }

    public void setZds(int zds) {
        this.zds = zds;
    }

    public List<FieldAuditDTO> getFieldAudits() {
        return fieldAudits;
    }

    public void setFieldAudits(List<FieldAuditDTO> fieldAudits) {
        this.fieldAudits = fieldAudits;
    }

    public String getWxmc() {
        return wxmc;
    }

    public void setWxmc(String wxmc) {
        this.wxmc = wxmc;
    }

    public String getSsdwlx() {
        return ssdwlx;
    }

    public void setSsdwlx(String ssdwlx) {
        this.ssdwlx = ssdwlx;
    }
}
