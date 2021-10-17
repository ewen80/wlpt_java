package pw.ewen.WLPT.domains.entities.resources.yule;

import javax.persistence.*;
import java.io.Serializable;

/**
 * created by wenliang on 2021/10/10
 * 歌舞娱乐场所舞池
 */
@Entity
public class YuleResourceGwWc implements Serializable {
    private static final long serialVersionUID = -5155189688577895380L;

    @Id
    @GeneratedValue
    private long id;

    /**
     * 舞池名
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

    @ManyToOne
    @JoinColumn
    private YuleResourceBase yuleResourceBase;

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

    public YuleResourceBase getYuleResourceBase() {
        return yuleResourceBase;
    }

    public void setYuleResourceBase(YuleResourceBase yuleResourceBase) {
        this.yuleResourceBase = yuleResourceBase;
    }
}
