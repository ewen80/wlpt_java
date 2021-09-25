package pw.ewen.WLPT.domains.entities.resources.weixing;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * created by wenliang on 2021/7/19
 * 《接收卫星传送的境外电视节目许可证》审批现场核查记录表
 */
@Entity
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeixingResource extends BaseResource implements Serializable {
    private static final long serialVersionUID = -5924003097388262942L;



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
     * 新办：xb, 变更: bg, 延续： yx
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
    private String yb;
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
     * 正馈 ，偏馈
     */
    private String txlx;
    /**
     * 境内收视节目源
     * 有线电视联网, 开路信号, IPTV, 其他
     */
    private String jnssjmy;
    /**
     * 卫星传输方式
     * 同网传输, 分网传输
     */
    private String wxcsfs;
    /**
     * 卫星名称
     * 亚太六号，中星6B
     */
    private String wxmc;
    /**
     * 信号调制方式
     * 数字, 模拟
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
     * 收视单位类型
     * 酒店、机构、公寓、境内
     */
    private String ssdwlx;
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
     * 场地核查信息
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FieldAudit> fieldAudits = new ArrayList<>();

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

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
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

    public String getXhtzfs() {
        return xhtzfs;
    }

    public void setXhtzfs(String xhtzfs) {
        this.xhtzfs = xhtzfs;
    }

    public List<FieldAudit> getFieldAudits() {
        return fieldAudits;
    }

    public void setFieldAudits(List<FieldAudit> fieldAudits) {
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
