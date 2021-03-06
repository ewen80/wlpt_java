package pw.ewen.WLPT.domains.entities.resources.vod;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * created by wenliang on 2021-12-21
 * 上海市宾馆饭店视频点播系统技术检测报告
 */
@Entity
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VodResource extends BaseResource implements Serializable {

    private static final long serialVersionUID = -4212081608770995760L;

    /**
     * 系统名称
     */
    private String sysName;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 制造厂商
     */
    private String manufacturer;
    /**
     * 设备型号
     */
    private String deviceModel;
    /**
     * 取样方式
     */
    private String samplingMethod;
    /**
     * 检测地点
     */
    private String detectLocation;
    /**
     * 检测单位
     */
    private String detectUnit;
    /**
     * 系统说明
     */
    private String sysExplanation;
    /**
     * 检测依据
     */
    private String detectBasis;
    /**
     * 检测概况
     */
    private String detectOverview;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getSamplingMethod() {
        return samplingMethod;
    }

    public void setSamplingMethod(String samplingMethod) {
        this.samplingMethod = samplingMethod;
    }

    public String getDetectLocation() {
        return detectLocation;
    }

    public void setDetectLocation(String detectLocation) {
        this.detectLocation = detectLocation;
    }

    public String getDetectUnit() {
        return detectUnit;
    }

    public void setDetectUnit(String detectUnit) {
        this.detectUnit = detectUnit;
    }

    public String getSysExplanation() {
        return sysExplanation;
    }

    public void setSysExplanation(String sysExplanation) {
        this.sysExplanation = sysExplanation;
    }

    public String getDetectBasis() {
        return detectBasis;
    }

    public void setDetectBasis(String detectBasis) {
        this.detectBasis = detectBasis;
    }

    public String getDetectOverview() {
        return detectOverview;
    }

    public void setDetectOverview(String detectOverview) {
        this.detectOverview = detectOverview;
    }

}
