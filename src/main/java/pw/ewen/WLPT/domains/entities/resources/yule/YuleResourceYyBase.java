package pw.ewen.WLPT.domains.entities.resources.yule;

import javax.persistence.*;

/**
 * created by wenliang on 2021/10/6
 * 游艺娱乐场所
 */
@Entity
public class YuleResourceYyBase {

    @Id
    @GeneratedValue
    private long id;
    /**
     * 是否分区经营
     */
    private boolean fenqu;
    /**
     * 是否有退币，退钢珠功能
     */
    private boolean tuibi;
    /**
     * 奖品是否与目录单一致
     */
    private boolean jiangpinCatalogSame;
    /**
     * 单件奖品价值是否超过500元
     */
    private boolean jiangpinValue;

    @OneToOne
    @JoinColumn
    private YuleResourceBase yuleResourceBase;

    public boolean isFenqu() {
        return fenqu;
    }

    public void setFenqu(boolean fenqu) {
        this.fenqu = fenqu;
    }

    public boolean isTuibi() {
        return tuibi;
    }

    public void setTuibi(boolean tuibi) {
        this.tuibi = tuibi;
    }

    public boolean isJiangpinCatalogSame() {
        return jiangpinCatalogSame;
    }

    public void setJiangpinCatalogSame(boolean jiangpinCatalogSame) {
        this.jiangpinCatalogSame = jiangpinCatalogSame;
    }

    public boolean isJiangpinValue() {
        return jiangpinValue;
    }

    public void setJiangpinValue(boolean jiangpinValue) {
        this.jiangpinValue = jiangpinValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public YuleResourceBase getYuleResourceBase() {
        return yuleResourceBase;
    }

    public void setYuleResourceBase(YuleResourceBase yuleResourceBase) {
        this.yuleResourceBase = yuleResourceBase;
    }
}
