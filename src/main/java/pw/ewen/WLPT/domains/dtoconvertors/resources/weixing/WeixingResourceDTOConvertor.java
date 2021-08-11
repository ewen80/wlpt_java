package pw.ewen.WLPT.domains.dtoconvertors.resources.weixing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.resources.ResourceCheckInDTO;
import pw.ewen.WLPT.domains.DTOs.resources.weixing.WeixingResourceDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.ResourceCheckInDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.SignatureDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.AttachmentService;
import pw.ewen.WLPT.services.PermissionService;
import pw.ewen.WLPT.services.resources.weixing.WeixingResourceService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021-07-21
 */
@Component
public class WeixingResourceDTOConvertor {

    private final PermissionService permissionService;
    private final PermissionDTOConvertor permissionDTOConvertor;
    private final ResourceCheckInDTOConvertor resourceCheckInDTOConvertor;
    private final SignatureDTOConvertor signatureDTOConvertor;
    private final UserContext userContext;

    private final DateTimeFormatter hcrqFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public WeixingResourceDTOConvertor(PermissionService permissionService,
                                       PermissionDTOConvertor permissionDTOConvertor,
                                       ResourceCheckInDTOConvertor resourceCheckInDTOConvertor,
                                       SignatureDTOConvertor signatureDTOConvertor, UserContext userContext) {
        this.permissionService = permissionService;
        this.signatureDTOConvertor = signatureDTOConvertor;
        this.userContext = userContext;
        this.permissionDTOConvertor = permissionDTOConvertor;
        this.resourceCheckInDTOConvertor = resourceCheckInDTOConvertor;
    }

    public WeixingResourceDTO toDTO(WeixingResource weixingResource) {
        return this.toDTO(weixingResource, true);
    }

    public WeixingResourceDTO toDTO(WeixingResource weixingResource, boolean fetchLazy) {
        WeixingResourceDTO dto = new WeixingResourceDTO();
        dto.setId(weixingResource.getId());
        // 将resource信息转为dto信息
        dto.setAzdz(weixingResource.getAzdz());
        dto.setSqdw(weixingResource.getSqdw());
        dto.setBgdh(weixingResource.getBgdh());
        dto.setBh(weixingResource.getBh());
        dto.setFzr(weixingResource.getFzr());
        dto.setFzrsj(weixingResource.getFzrsj());

        dto.setHcrq(this.hcrqFormatter.format(weixingResource.getHcrq()));
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

        if(!fetchLazy) {
            // 添加签名信息
            Signature sign = weixingResource.getSign();
            if(sign != null) {
                SignatureDTO signatureDTO = signatureDTOConvertor.toDTO(sign);
                dto.setSign(signatureDTO);
            }

            // 添加附件信息
            dto.setAttachments(weixingResource.getAttachments());

            // 添加资源登记信息
            ResourceCheckIn resourceCheckIn = weixingResource.getResourceCheckIn();
            if(resourceCheckIn != null) {
                ResourceCheckInDTO resourceCheckInDTO = resourceCheckInDTOConvertor.toDTO(resourceCheckIn);
                dto.setResourceCheckIn(resourceCheckInDTO);
            }


            // 添加权限列表
            ResourceRangePermissionWrapper wrapper = permissionService.getByRoleAndResource(userContext.getCurrentUser().getRole().getId(), weixingResource);
            List<PermissionDTO> permissionDTOS = wrapper.getPermissions().stream().map(permissionDTOConvertor::toDTO).collect(Collectors.toList());
            dto.setPermissions(permissionDTOS);
        }


        return dto;
    }

    public WeixingResource toWeixingResource(WeixingResourceDTO dto, WeixingResourceService weixingResourceService, AttachmentService attachmentService) {
        WeixingResource weixingResource = new WeixingResource();

        weixingResource.setId(dto.getId());
        weixingResource.setAzdz(dto.getAzdz());
        weixingResource.setSqdw(dto.getSqdw());
        weixingResource.setBgdh(dto.getBgdh());
        weixingResource.setBh(dto.getBh());
        weixingResource.setFzr(dto.getFzr());
        weixingResource.setFzrsj(dto.getFzrsj());
        weixingResource.setHcrq(LocalDate.parse(dto.getHcrq(), hcrqFormatter));
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

        // 添加签名信息
        SignatureDTO signatureDTO = dto.getSign();
        if(signatureDTO != null) {
            Signature sign = signatureDTOConvertor.toSignature(signatureDTO);
            weixingResource.setSign(sign);
        }
        // 添加附件信息
        weixingResource.setAttachments(dto.getAttachments());
        // 添加登记信息
        ResourceCheckInDTO resourceCheckInDTO = dto.getResourceCheckIn();
        if(resourceCheckInDTO != null) {
            ResourceCheckIn resourceCheckIn = resourceCheckInDTOConvertor.toResourceCheckIn(resourceCheckInDTO);
            weixingResource.setResourceCheckIn(resourceCheckIn);
        }
        return weixingResource;
    }
}
