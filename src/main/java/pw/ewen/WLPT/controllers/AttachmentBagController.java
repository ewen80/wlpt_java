package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.AttachmentBag;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.services.AttachmentBagService;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.resources.FieldAuditService;

import java.util.ArrayList;
import java.util.List;

/**
 * 附件包
 */
@RestController
@RequestMapping(value = "/attachmentbags")
public class AttachmentBagController {
    private final FieldAuditService fieldAuditService;
    private final AttachmentBagService attachmentBagService;

    @Autowired
    public AttachmentBagController(FieldAuditService fieldAuditService, AttachmentBagService attachmentBagService) {
        this.fieldAuditService = fieldAuditService;
        this.attachmentBagService = attachmentBagService;
    }


    /**
     * 通过附件包id获取附件包信息
     * @param id    附件包id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<AttachmentBag> findOne(@PathVariable long id) {
        return this.attachmentBagService.findOne(id).map(attachmentBag -> new ResponseEntity<>(attachmentBag, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 通过现场审核信息id获取附件包列表
     * @param auditId   现场审核信息id
     */
    @GetMapping
    public List<AttachmentBag> findByAuditId(@RequestParam long auditId) {
        return this.fieldAuditService.findOne(auditId)
                .map(FieldAudit::getAttachmentBags)
                .orElse(new ArrayList<>());
    }

    /**
     * 现场审核信息附件包关联包装类
     */
    private static class FieldAuditAttachmentBagParam {
        /**
         * 附件包
         */
        public AttachmentBag attachmentBag;
        /**
         * 现场审核信息id
         */
        public long auditId;
    }

    /**
     * 保存与现场审核信息关联的附件包
     * @param saveParam 现场审核信息附件包关联包装类
     */
    @PutMapping()
    public ResponseEntity<AttachmentBag> saveByFieldAudit(@RequestBody FieldAuditAttachmentBagParam saveParam) {
        return this.fieldAuditService.findOne(saveParam.auditId)
                .map(fieldAudit -> {
                    AttachmentBag bag = saveParam.attachmentBag;
                    this.attachmentBagService.save(bag);
                    List<AttachmentBag> bags = fieldAudit.getAttachmentBags();
                    int index = bags.indexOf(bag);
                    if(index > -1) {
                        bags.set(index, bag);
                    } else {
                        bags.add(bag);
                    }
                    fieldAuditService.save(fieldAudit);
                    return new ResponseEntity<>(bag, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
