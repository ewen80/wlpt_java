package pw.ewen.WLPT.domains.DTOs.resources.yule;

/**
 * created by wenliang on 2021/10/6
 */
public class YuleResourceYyBaseDTO {

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
    /**
     * 与申请材料是否一致
     */
    private boolean materialSame;

    private long yuleResourceBaseId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getYuleResourceBaseId() {
        return yuleResourceBaseId;
    }

    public void setYuleResourceBaseId(long yuleResourceBaseId) {
        this.yuleResourceBaseId = yuleResourceBaseId;
    }

    public boolean isMaterialSame() {
        return materialSame;
    }

    public void setMaterialSame(boolean materialSame) {
        this.materialSame = materialSame;
    }
}
