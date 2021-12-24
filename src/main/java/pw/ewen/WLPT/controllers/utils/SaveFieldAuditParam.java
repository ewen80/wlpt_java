package pw.ewen.WLPT.controllers.utils;

import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;

/**
 * 保存现场审核信息参数包装类
 */
public class SaveFieldAuditParam {
    /**
     * 现场审核信息
     */
    public FieldAuditDTO fieldAudit;
    /**
     * 卫星场地id
     */
    public long resourceId;
}

