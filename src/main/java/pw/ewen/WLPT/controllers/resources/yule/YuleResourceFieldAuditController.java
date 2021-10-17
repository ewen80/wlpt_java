package pw.ewen.WLPT.controllers.resources.yule;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.services.resources.FieldAuditService;
import pw.ewen.WLPT.services.resources.yule.YuleResourceBaseService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 娱乐场地-现场审核信息
 */
@RestController
@RequestMapping(value = "/resources/yules/fieldaudits")
public class YuleResourceFieldAuditController {
    private final YuleResourceBaseService yuleResourceBaseService;
    private final FieldAuditService fieldAuditService;
    private final FieldAuditDTOConvertor fieldAuditDTOConvertor;

    public YuleResourceFieldAuditController(YuleResourceBaseService yuleResourceBaseService, FieldAuditService fieldAuditService, FieldAuditDTOConvertor fieldAuditDTOConvertor) {
        this.yuleResourceBaseService = yuleResourceBaseService;
        this.fieldAuditService = fieldAuditService;
        this.fieldAuditDTOConvertor = fieldAuditDTOConvertor;
    }

    /**
     * 保存现场审核信息参数包装类
     */
    private static class SaveFieldAuditParam {
        /**
         * 现场审核信息
         */
        public FieldAuditDTO fieldAudit;
        /**
         * 场地id
         */
        public long resourceId;
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
    public ResponseEntity<FieldAuditDTO> save(@RequestBody YuleResourceFieldAuditController.SaveFieldAuditParam saveFieldAuditParam) {
        return this.yuleResourceBaseService.findOne(saveFieldAuditParam.resourceId)
                .map(yule -> {
                    FieldAudit fieldAudit = fieldAuditDTOConvertor.toFieldAudit(saveFieldAuditParam.fieldAudit);
                    fieldAuditService.save(fieldAudit);
                    if(saveFieldAuditParam.fieldAudit.getId() > 0){
                        int index = yule.getFieldAudits().indexOf(fieldAudit);
                        yule.getFieldAudits().set(index, fieldAudit);
                    } else {
                        yule.getFieldAudits().add(fieldAudit);
                    }
                    yuleResourceBaseService.update(yule);

                    return new ResponseEntity<>(fieldAuditDTOConvertor.toDTO(fieldAudit), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * 根据场地id获取现场审核意见
     * @param resourceId 场地id
     */
    @GetMapping()
    public List<FieldAuditDTO> findByYuleResourceId(@RequestParam("resourceId") long resourceId) {
        return yuleResourceBaseService.findOne(resourceId)
                .map(yule -> {
                    List<FieldAudit> fieldAudits = yule.getFieldAudits();
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
            yuleResourceBaseService.findOne(resourceId).ifPresent(yule -> {
                List<FieldAudit> fieldAudits = yule.getFieldAudits();
                for(String id : idArr){
                    fieldAudits.removeIf(fieldAudit -> Long.parseLong(id) == fieldAudit.getId());
                }
                yuleResourceBaseService.update(yule);
            });
        }catch (Exception ignored){}
    }
}
