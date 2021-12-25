package pw.ewen.WLPT.domains.dtoconvertors.resources.weixing;

import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.resources.weixing.WeixingResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.FieldAuditDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.ResourceCheckInDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.PermissionService;

/**
 * created by wenliang on 2021-07-21
 */
@Component
public class WeixingResourceDTOConvertor extends DTOBaseConvertor<WeixingResource, WeixingResourceDTO> {

    protected WeixingResourceDTOConvertor(FieldAuditDTOConvertor fieldAuditDTOConvertor, ResourceCheckInDTOConvertor resourceCheckInDTOConvertor, PermissionService permissionService, PermissionDTOConvertor permissionDTOConvertor, UserContext userContext) {
        super(fieldAuditDTOConvertor, resourceCheckInDTOConvertor, permissionService, permissionDTOConvertor, userContext);
    }

    public WeixingResourceDTO toDTO(WeixingResource weixingResource) {
        return this.toDTO(weixingResource, true);
    }

    public WeixingResourceDTO toDTO(WeixingResource weixingResource, boolean fetchLazy) {
        WeixingResourceDTO dto = new WeixingResourceDTO();

        dto.setId(weixingResource.getId());
        dto.setBh(weixingResource.getBh());
        dto.setAzdz(weixingResource.getAzdz());
        dto.setSqdw(weixingResource.getSqdw());
        dto.setBgdh(weixingResource.getBgdh());
        dto.setFzr(weixingResource.getFzr());
        dto.setFzrsj(weixingResource.getFzrsj());
        dto.setQxId(weixingResource.getQxId());
        dto.setJfwz(weixingResource.getJfwz());
        dto.setJnssjmy(weixingResource.getJnssjmy());
        dto.setLc(weixingResource.getLc());
        dto.setSjazdwmc(weixingResource.getSjazdwmc());
        dto.setTxsl(weixingResource.getTxsl());
        dto.setLpm(weixingResource.getLpm());
        dto.setTxwz(weixingResource.getTxwz());
        dto.setWxcsfs(weixingResource.getWxcsfs());
        dto.setWxssazxkzh(weixingResource.getWxssazxkzh());
        dto.setYzbm(weixingResource.getYb());
        dto.setSsnr(weixingResource.getSsnr());
        dto.setZds(weixingResource.getZds());
        dto.setTxlx(weixingResource.getTxlx());
        dto.setSqlx(weixingResource.getSqlx());
        dto.setZds(weixingResource.getZds());
        dto.setXhtzfs(weixingResource.getXhtzfs());
        dto.setWxmc(weixingResource.getWxmc());
        dto.setSsdwlx(weixingResource.getSsdwlx());
        dto.setLxdz(weixingResource.getLxdz());

        this.setReadedInfoToDTO(weixingResource, dto);
        if(!fetchLazy) this.setExtraInfoToDTO(weixingResource, dto);

        return dto;
    }

    public WeixingResource toResource(WeixingResourceDTO dto) {
        WeixingResource weixingResource = new WeixingResource();

        weixingResource.setId(dto.getId());
        weixingResource.setBh(dto.getBh());
        weixingResource.setAzdz(dto.getAzdz());
        weixingResource.setSqdw(dto.getSqdw());
        weixingResource.setBgdh(dto.getBgdh());
        weixingResource.setFzr(dto.getFzr());
        weixingResource.setFzrsj(dto.getFzrsj());
        weixingResource.setQxId(dto.getQxId());
        weixingResource.setJfwz(dto.getJfwz());
        weixingResource.setJnssjmy(dto.getJnssjmy());
        weixingResource.setLc(dto.getLc());
        weixingResource.setSjazdwmc(dto.getSjazdwmc());
        weixingResource.setTxsl(dto.getTxsl());
        weixingResource.setLpm(dto.getLpm());
        weixingResource.setTxwz(dto.getTxwz());
        weixingResource.setWxcsfs(dto.getWxcsfs());
        weixingResource.setWxssazxkzh(dto.getWxssazxkzh());
        weixingResource.setYb(dto.getYzbm());
        weixingResource.setSsnr(dto.getSsnr());
        weixingResource.setZds(dto.getZds());
        weixingResource.setTxlx(dto.getTxlx());
        weixingResource.setSqlx(dto.getSqlx());
        weixingResource.setZds(dto.getZds());
        weixingResource.setXhtzfs(dto.getXhtzfs());
        weixingResource.setWxmc(dto.getWxmc());
        weixingResource.setSsdwlx(dto.getSsdwlx());
        weixingResource.setLxdz(dto.getLxdz());

        this.setExtraInfoToResource(dto, weixingResource);
        return weixingResource;
    }
}
