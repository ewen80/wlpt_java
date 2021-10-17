package pw.ewen.WLPT.domains.dtoconvertors.resources.yule;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceGwWcDTO;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwWc;
import pw.ewen.WLPT.services.resources.yule.YuleResourceBaseService;

/**
 * created by wenliang on 2021/10/10
 */
@Component
public class YuleResourceGwWcDTOConvertor {
    private final YuleResourceBaseService yuleResourceBaseService;

    public YuleResourceGwWcDTOConvertor(YuleResourceBaseService yuleResourceBaseService) {
        this.yuleResourceBaseService = yuleResourceBaseService;
    }

    public YuleResourceGwWc toWc(YuleResourceGwWcDTO dto) {
        YuleResourceGwWc wc = new YuleResourceGwWc();
        wc.setArea(dto.getArea());
        wc.setDlwc(dto.isDlwc());
        wc.setId(dto.getId());
        wc.setName(dto.getName());
        wc.setYwjf(dto.isYwjf());
        yuleResourceBaseService.findOne(dto.getYuleResourceBaseId()).ifPresent(wc::setYuleResourceBase);
        return wc;
    }

    public YuleResourceGwWcDTO toDTO(YuleResourceGwWc wc) {
        YuleResourceGwWcDTO dto = new YuleResourceGwWcDTO();
        dto.setArea(wc.getArea());
        dto.setName(wc.getName());
        dto.setId(wc.getId());
        dto.setDlwc(wc.isDlwc());
        dto.setYwjf(wc.isYwjf());
        dto.setYuleResourceBaseId(wc.getYuleResourceBase().getId());
        return dto;
    }
}
