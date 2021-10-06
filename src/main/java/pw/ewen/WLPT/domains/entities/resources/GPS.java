package pw.ewen.WLPT.domains.entities.resources;

import javax.persistence.*;
import java.io.Serializable;

/**
 * created by wenliang on 2021/10/3
 */
@Entity
public class GPS implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lng;
    /**
     * 地点描述
     */
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
