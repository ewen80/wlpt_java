package pw.ewen.WLPT.controllers.resources.weixings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.dtoconvertors.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
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
    private final FieldAuditDTOConvertor fieldAuditDTOConvertor;

    @Autowired
    public WeixingResourceFieldAuditController(WeixingResourceService weixingResourceService, FieldAuditDTOConvertor fieldAuditDTOConvertor) {
        this.weixingResourceService = weixingResourceService;
        this.fieldAuditDTOConvertor = fieldAuditDTOConvertor;
    }

    /**
     * 保存参数包装类
     */
    private static class SaveParam {
        /**
         * 现场审核信息
         */
        public FieldAuditDTO fieldAudit;
        /**
         * 卫星场地id
         */
        public long weixingResourceId;
    }
    /**
     * 保存卫星场地审核信息
     * @param saveParam  保存参数 ， {
     *         public FieldAuditDTO fieldAudit;
     *         public long weixingResourceId;
     *     }
     * @apiNote 签名图片后缀名不要加点号,保存 jpg 或者 png字样.base64字段只保存图片信息,不要添加data:image等前缀字符
     */
    @PutMapping()
    public void save(@RequestBody SaveParam  saveParam) {
        this.weixingResourceService.findOne(saveParam.weixingResourceId)
                .ifPresent(weixingResource -> {
                    FieldAudit fieldAudit = fieldAuditDTOConvertor.toFieldAudit(saveParam.fieldAudit);
                    weixingResource.getFieldAudits().remove(fieldAudit);
                    weixingResource.getFieldAudits().add(fieldAudit);
                    weixingResourceService.save(weixingResource);
                });
    }

    /**
     * 根据卫星场地id获取现场审核意见
     * @param weixingResourceId 卫星场地id
     */
    @GetMapping()
    public List<FieldAuditDTO> findByWeixingResourceId(@RequestParam("weixingResourceId") long weixingResourceId) {
        return weixingResourceService.findOne(weixingResourceId)
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
     * @param weixingResourceId 卫星场地id
     */
    @DeleteMapping(value = "/{ids}")
    public void delete(@PathVariable("ids") String ids, @RequestParam("weixingResourceId") long weixingResourceId){
        String[] idArr = ids.split(",");
        try{
            weixingResourceService.findOne(weixingResourceId).ifPresent(weixingResource -> {
                List<FieldAudit> fieldAudits = weixingResource.getFieldAudits();
                for(String id : idArr){
                    fieldAudits.removeIf(fieldAudit -> Long.parseLong(id) == fieldAudit.getId());
                }
                weixingResourceService.save(weixingResource);
            });
        }catch (Exception ignored){}
    }
}
