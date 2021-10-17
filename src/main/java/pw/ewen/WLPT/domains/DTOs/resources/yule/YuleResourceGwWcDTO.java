package pw.ewen.WLPT.domains.DTOs.resources.yule;

/**
 * created by wenliang on 2021/10/10
 */
public class YuleResourceGwWcDTO {

    private long id;
    /**
     * 舞池名称
     */
    private String name;
    /**
     * 舞池面积
     */
    private float area;
    /**
     * 舞池与休息座是否分开
     */
    private boolean dlwc;
    /**
     * 是否有衣物寄放室
     */
    private boolean ywjf;

    private long yuleResourceBaseId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public boolean isDlwc() {
        return dlwc;
    }

    public void setDlwc(boolean dlwc) {
        this.dlwc = dlwc;
    }

    public boolean isYwjf() {
        return ywjf;
    }

    public void setYwjf(boolean ywjf) {
        this.ywjf = ywjf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getYuleResourceBaseId() {
        return yuleResourceBaseId;
    }

    public void setYuleResourceBaseId(long yuleResourceBaseId) {
        this.yuleResourceBaseId = yuleResourceBaseId;
    }
}
