package pw.ewen.WLPT.domains.dtoconvertors.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * created by wenliang on 2021-08-09
 */
@Component
public class SignatureDTOConvertor {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SignatureDTO toDTO(Signature sign) {
        SignatureDTO dto = new SignatureDTO();
        dto.setId(sign.getId());
        byte[] signData = sign.getBytes();
        String signBase64 = Base64.getEncoder().encodeToString(signData);
        dto.setSignBase64(signBase64);
        dto.setCreatedAt(sign.getCreatedAt().format(formatter));
        dto.setName(sign.getName());
        dto.setImageExtention(sign.getImageExtention());
        return dto;
    }

    public Signature toSignature(SignatureDTO dto) {
        Signature sign = new Signature();
        sign.setId(dto.getId());
        LocalDateTime createdAt = LocalDateTime.parse(dto.getCreatedAt(), formatter);
        sign.setCreatedAt(createdAt);
        byte[] signData = Base64.getDecoder().decode(dto.getSignBase64());
        sign.setBytes(signData);
        sign.setName(dto.getName());
        sign.setImageExtention(dto.getImageExtention());
        return sign;
    }
}
