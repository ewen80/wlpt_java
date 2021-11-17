package pw.ewen.WLPT.domains.entities.resources.yule;

import javax.persistence.*;
import java.io.Serializable;

/**
 * created by wenliang on 2021/10/6
 * 歌舞娱乐场所包房
 */
@Entity
public class YuleResourceGwRoom implements Serializable {
    private static final long serialVersionUID = -8124717228615385518L;

    @Id
    @GeneratedValue
    private long id;

    /**
     * 包房名
     */
    private String name;
    /**
     * 包房面积
     */
    private float area;
    /**
     * 核定人数
     */
    private int hdrs;
    /**
     * 是否有隔断或者卫生间
     */
    private boolean toilet;
    /**
     * 是否有内锁装置
     */
    private boolean innerLock;
    /**
     * 是否安装透明清晰材料
     */
    private boolean window;
    /**
     * 点唱歌曲1000首以上
     */
    private boolean oneThousandSongs;
    /**
     * 是否安装长明灯
     */
    private boolean everlight;

    @ManyToOne
    @JoinColumn
    private YuleResourceBase yuleResourceBase;

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public boolean isToilet() {
        return toilet;
    }

    public void setToilet(boolean toilet) {
        this.toilet = toilet;
    }

    public boolean isInnerLock() {
        return innerLock;
    }

    public void setInnerLock(boolean innerLock) {
        this.innerLock = innerLock;
    }

    public boolean isWindow() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window = window;
    }

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

    public int getHdrs() {
        return hdrs;
    }

    public void setHdrs(int hdrs) {
        this.hdrs = hdrs;
    }

    public boolean isOneThousandSongs() {
        return oneThousandSongs;
    }

    public void setOneThousandSongs(boolean oneThousandSongs) {
        this.oneThousandSongs = oneThousandSongs;
    }

    public YuleResourceBase getYuleResourceBase() {
        return yuleResourceBase;
    }

    public void setYuleResourceBase(YuleResourceBase yuleResourceBase) {
        this.yuleResourceBase = yuleResourceBase;
    }

    public boolean isEverlight() {
        return everlight;
    }

    public void setEverlight(boolean everlight) {
        this.everlight = everlight;
    }
}
