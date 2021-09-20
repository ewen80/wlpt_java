package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.services.AttachmentBagService;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.resources.FieldAuditService;

import java.util.List;

/**
 * 现场审核信息
 */
@RestController
@RequestMapping("/fieldaudits")
public class FieldAuditContoller {

    private final FieldAuditService fieldAuditService;
    private final AttachmentBagService attachmentBagService;
    private final FileService fileService;
    private final BizConfig bizConfig;
    private final FieldAuditDTOConvertor fieldAuditDTOConvertor;

    @Autowired
    public FieldAuditContoller(FieldAuditService fieldAuditService, AttachmentBagService attachmentBagService, FileService fileService, BizConfig bizConfig, FieldAuditDTOConvertor fieldAuditDTOConvertor) {
        this.fieldAuditService = fieldAuditService;
        this.attachmentBagService = attachmentBagService;
        this.fileService = fileService;
        this.bizConfig = bizConfig;
        this.fieldAuditDTOConvertor = fieldAuditDTOConvertor;
    }

    /**
     * 获取现场审核信息
     * @param id    现场审核信息id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<FieldAuditDTO> getOne(@PathVariable(value = "id") long id) {
        return this.fieldAuditService.findOne(id)
                .map(fieldAudit ->  new ResponseEntity<>(fieldAuditDTOConvertor.toDTO(fieldAudit), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 保存现场审核意见
     * @param dto 现场审核意见DTO
     */
    @PutMapping()
    public void save(@RequestBody  FieldAuditDTO dto) {
        FieldAudit fieldAudit = fieldAuditDTOConvertor.toFieldAudit(dto);
        fieldAuditService.save(fieldAudit);
    }

    /**
     * 删除现场审核意见
     * @param ids 逗号分隔的id
     */
    @DeleteMapping(value = "/{ids}")
    public void delete(@PathVariable String ids) {
        String[] arrIds = ids.split(",");
        for(String id : arrIds) {
            try{
                fieldAuditService.delete(Long.parseLong(id));
            } catch (Exception ignored){}
        }
    }

    /**
     * 删除附件包
     * @param auditId 现场审核意见id
     * @param attachmentBagIds   逗号分隔的附件包id
     * @apiNote 附件包内的所有附件文件都会被实际删除
     */
    @DeleteMapping(value = "/attachmentbags/{auditId}")
    public void deleteAttachmentBag(@PathVariable long auditId, @RequestParam(value = "bagIds") String attachmentBagIds) {
        String[] arrIds = attachmentBagIds.split(",");
        for(String strId : arrIds) {
            try {
                long id = Long.parseLong(strId);
                this.attachmentBagService.findOne(id)
                        .ifPresent(attachmentBag -> {
                            List<Attachment> attachments = attachmentBag.getAttachments();
                            for(Attachment attachment : attachments) {
                                this.fileService.delete(bizConfig.getFile().getFileUploadRootPath() + attachment.getPath());
                            }
                            this.fieldAuditService.deleteAttachmentBag(auditId, attachmentBag.getId());
                        });
            } catch (Exception ignored){}

        }
    }

}
