package pw.ewen.WLPT.controllers.resources.vod;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.SaveFieldAuditParam;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.services.resources.FieldAuditService;
import pw.ewen.WLPT.services.resources.vod.VodResourceService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 视频点播-现场审核信息
 */
@RestController
@RequestMapping(value = "/resources/vods/fieldaudits")
public class VodResourceFieldAuditController {
    private final VodResourceService vodResourceService;
    private final FieldAuditDTOConvertor fieldAuditDTOConvertor;
    private final FieldAuditService fieldAuditService;

    public VodResourceFieldAuditController(VodResourceService vodResourceService, FieldAuditDTOConvertor fieldAuditDTOConvertor, FieldAuditService fieldAuditService) {
        this.vodResourceService = vodResourceService;
        this.fieldAuditDTOConvertor = fieldAuditDTOConvertor;
        this.fieldAuditService = fieldAuditService;
    }

    /**
     * 保存场地审核信息
     * @param saveFieldAuditParam  保存参数 ， {
     *         public FieldAuditDTO fieldAudit;
     *         public long resourceId;
     *     }
     * @apiNote 签名图片后缀名不要加点号,保存 jpg 或者 png字样.base64字段只保存图片信息,不要添加data:image等前缀字符
     */
    @PutMapping()
    public ResponseEntity<FieldAuditDTO> save(@RequestBody SaveFieldAuditParam saveFieldAuditParam) {
        return this.vodResourceService.findOne(saveFieldAuditParam.resourceId)
                .map(resource -> {
                    FieldAudit fieldAudit = fieldAuditDTOConvertor.toFieldAudit(saveFieldAuditParam.fieldAudit);
                    fieldAuditService.save(fieldAudit);
                    if(saveFieldAuditParam.fieldAudit.getId() > 0){
                        int index = resource.getFieldAudits().indexOf(fieldAudit);
                        resource.getFieldAudits().set(index, fieldAudit);
                    } else {
                        resource.getFieldAudits().add(fieldAudit);
                    }
                    vodResourceService.update(resource);

                    return new ResponseEntity<>(fieldAuditDTOConvertor.toDTO(fieldAudit), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * 根据场地id获取现场审核意见
     * @param resourceId 场地id
     */
    @GetMapping()
    public List<FieldAuditDTO> findByResourceId(@RequestParam("resourceId") long resourceId) {
        return vodResourceService.findOne(resourceId)
                .map(resource -> {
                    List<FieldAudit> fieldAudits = resource.getFieldAudits();
                    fieldAudits.sort(Comparator.comparing(FieldAudit::getAuditDate));
                    List<FieldAuditDTO> fieldAuditDTOS = new ArrayList<>();
                    for(FieldAudit fieldAudit : fieldAudits) {
                        FieldAuditDTO dto = this.fieldAuditDTOConvertor.toDTO(fieldAudit);
                        fieldAuditDTOS.add(dto);
                    }
                    return fieldAuditDTOS;
                })
                .orElse(new ArrayList<>());
    }

    /**
     * 删除场地现场审核意见
     * @param ids 用逗号分隔的意见id
     * @param resourceId 场地id
     */
    @DeleteMapping(value = "/{ids}")
    public void delete(@PathVariable("ids") String ids, @RequestParam("resourceId") long resourceId){
        String[] idArr = ids.split(",");
        try{
            vodResourceService.findOne(resourceId).ifPresent(resource -> {
                List<FieldAudit> fieldAudits = resource.getFieldAudits();
                for(String id : idArr){
                    fieldAudits.removeIf(fieldAudit -> Long.parseLong(id) == fieldAudit.getId());
                }
                vodResourceService.update(resource);
            });
        }catch (Exception ignored){}
    }
}
