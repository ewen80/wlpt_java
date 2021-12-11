package pw.ewen.WLPT.domains.dtoconvertors.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.DTOs.resources.FieldAuditDTO;
import pw.ewen.WLPT.domains.entities.resources.FieldAudit;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.repositories.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021/9/12
 */
@Component
public class FieldAuditDTOConvertor {

    private final SignatureDTOConvertor signatureDTOConvertor;
    private final UserRepository userRepository;

    private final DateTimeFormatter localDateFormat;

    @Autowired
    public FieldAuditDTOConvertor(BizConfig bizConfig, SignatureDTOConvertor signatureDTOConvertor, UserRepository userRepository) {
        this.signatureDTOConvertor = signatureDTOConvertor;
        this.userRepository = userRepository;

        this.localDateFormat = DateTimeFormatter.ofPattern(bizConfig.getDateFormat().getLocalDateFormat());
    }



    public FieldAuditDTO toDTO(FieldAudit fieldAudit) {
        FieldAuditDTO dto = new FieldAuditDTO();
        dto.setId(fieldAudit.getId());
        dto.setContent(fieldAudit.getContent());
        dto.setAuditUserId(fieldAudit.getUser().getId());
        dto.setAuditDate(fieldAudit.getAuditDate().format(localDateFormat));
        if(fieldAudit.getFzrSignature() != null) {
            dto.setFzrSignature(signatureDTOConvertor.toDTO(fieldAudit.getFzrSignature()));
        }
        dto.setAttachmentBags(fieldAudit.getAttachmentBags());
        dto.setAuditDepartment(fieldAudit.getAuditDepartment());
        dto.setGps(fieldAudit.getGps());
        List<SignatureDTO> auditorSignatureDTOs =  fieldAudit.getAuditorSignatures().stream().map(signatureDTOConvertor::toDTO).collect(Collectors.toList());
        dto.setAuditorSignatures(auditorSignatureDTOs);

        return dto;
    }

    public FieldAudit toFieldAudit(FieldAuditDTO dto) {
        FieldAudit fieldAudit = new FieldAudit();
        fieldAudit.setId(dto.getId());
        fieldAudit.setContent(dto.getContent());
        userRepository.findById(dto.getAuditUserId()).ifPresent(fieldAudit::setUser);
        fieldAudit.setAuditDepartment(dto.getAuditDepartment());
        fieldAudit.setAuditDate(LocalDate.parse(dto.getAuditDate(), localDateFormat));

        if(dto.getFzrSignature() != null) {
            fieldAudit.setFzrSignature(signatureDTOConvertor.toSignature(dto.getFzrSignature()));
        }
        
        fieldAudit.setAttachmentBags(dto.getAttachmentBags());
        fieldAudit.setGps(dto.getGps());
        List<Signature> auditorSignature = dto.getAuditorSignatures().stream().map(signatureDTOConvertor::toSignature).collect(Collectors.toList());
        fieldAudit.setAuditorSignatures(auditorSignature);
        return fieldAudit;
    }

}
