package pw.ewen.WLPT.domains.dtoconvertors.resources.yule;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceYyBaseDTO;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceYyBase;
import pw.ewen.WLPT.services.resources.yule.YuleResourceBaseService;

/**
 * created by wenliang on 2021/10/10
 */
@Component
public class YuleResourceYyDTOConvertor {
    private final YuleResourceBaseService yuleService;

    public YuleResourceYyDTOConvertor(YuleResourceBaseService yuleService) {
        this.yuleService = yuleService;
    }

    public YuleResourceYyBase toYyBase(YuleResourceYyBaseDTO dto) {
        YuleResourceYyBase yy = new YuleResourceYyBase();
        yy.setId(dto.getId());
        yy.setFenqu(dto.isFenqu());
        yy.setTuibi(dto.isTuibi());
        yy.setJiangpinValue(dto.isJiangpinValue());
        yy.setJiangpinCatalogSame(dto.isJiangpinCatalogSame());
        yuleService.findOne(dto.getYuleResourceBaseId()).ifPresent(yy::setYuleResourceBase);
        return yy;
    }

    public YuleResourceYyBaseDTO toDTO(YuleResourceYyBase yy) {
        YuleResourceYyBaseDTO dto = new YuleResourceYyBaseDTO();
        dto.setYuleResourceBaseId(yy.getYuleResourceBase().getId());
        dto.setId(yy.getId());
        dto.setFenqu(yy.isFenqu());
        dto.setTuibi(yy.isTuibi());
        dto.setJiangpinValue(yy.isJiangpinValue());
        dto.setJiangpinCatalogSame(yy.isJiangpinCatalogSame());
        return dto;
    }
}
