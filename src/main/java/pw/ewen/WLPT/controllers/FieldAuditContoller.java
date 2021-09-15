package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.dtoconvertors.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.services.resources.FieldAuditService;

/**
 * 现场审核信息
 */
@RestController
@RequestMapping("/fieldaudits")
public class FieldAuditContoller {

    private final FieldAuditService fieldAuditService;
    private final FieldAuditDTOConvertor fieldAuditDTOConvertor;

    @Autowired
    public FieldAuditContoller(FieldAuditService fieldAuditService, FieldAuditDTOConvertor fieldAuditDTOConvertor) {
        this.fieldAuditService = fieldAuditService;
        this.fieldAuditDTOConvertor = fieldAuditDTOConvertor;
    }

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

}
