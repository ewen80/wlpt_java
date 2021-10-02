package pw.ewen.WLPT.controllers.resources.weixings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.AttachmentBag;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.services.resources.FieldAuditService;
import pw.ewen.WLPT.services.resources.weixing.WeixingResourceService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 卫星场地-现场审核信息
 */
@RestController
@RequestMapping(value = "/resources/weixings/fieldaudits")
public class WeixingResourceFieldAuditController {

    private final WeixingResourceService weixingResourceService;
    private final FieldAuditService fieldAuditService;
    private final FieldAuditDTOConvertor fieldAuditDTOConvertor;

    @Autowired
    public WeixingResourceFieldAuditController(WeixingResourceService weixingResourceService, FieldAuditService fieldAuditService, FieldAuditDTOConvertor fieldAuditDTOConvertor) {
        this.weixingResourceService = weixingResourceService;
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
         * 卫星场地id
         */
        public long resourceId;
    }
    /**
     * 保存卫星场地审核信息
     * @param saveFieldAuditParam  保存参数 ， {
     *         public FieldAuditDTO fieldAudit;
     *         public long resourceId;
     *     }
     * @apiNote 签名图片后缀名不要加点号,保存 jpg 或者 png字样.base64字段只保存图片信息,不要添加data:image等前缀字符
     */
    @PutMapping()
    public ResponseEntity<FieldAuditDTO> save(@RequestBody SaveFieldAuditParam saveFieldAuditParam) {
        return this.weixingResourceService.findOne(saveFieldAuditParam.resourceId)
                .map(weixingResource -> {
                    FieldAudit fieldAudit = fieldAuditDTOConvertor.toFieldAudit(saveFieldAuditParam.fieldAudit);
                    fieldAuditService.save(fieldAudit);
                    if(saveFieldAuditParam.fieldAudit.getId() > 0){
                        int index = weixingResource.getFieldAudits().indexOf(fieldAudit);
                        weixingResource.getFieldAudits().set(index, fieldAudit);
                    } else {
                        weixingResource.getFieldAudits().add(fieldAudit);
                    }
                    weixingResourceService.update(weixingResource);

                    return new ResponseEntity<>(fieldAuditDTOConvertor.toDTO(fieldAudit), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * 根据卫星场地id获取现场审核意见
     * @param resourceId 卫星场地id
     */
    @GetMapping()
    public List<FieldAuditDTO> findByWeixingResourceId(@RequestParam("resourceId") long resourceId) {
        return weixingResourceService.findOne(resourceId)
                .map(weixingResource -> {
                    List<FieldAudit> fieldAudits = weixingResource.getFieldAudits();
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
     * 删除卫星场地现场审核意见
     * @param ids 用逗号分隔的意见id
     * @param resourceId 卫星场地id
     */
    @DeleteMapping(value = "/{ids}")
    public void delete(@PathVariable("ids") String ids, @RequestParam("resourceId") long resourceId){
        String[] idArr = ids.split(",");
        try{
            weixingResourceService.findOne(resourceId).ifPresent(weixingResource -> {
                List<FieldAudit> fieldAudits = weixingResource.getFieldAudits();
                for(String id : idArr){
                    fieldAudits.removeIf(fieldAudit -> Long.parseLong(id) == fieldAudit.getId());
                }
                weixingResourceService.update(weixingResource);
            });
        }catch (Exception ignored){}
    }
}
