package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.AttachmentBag;
import pw.ewen.WLPT.repositories.AttachmentBagRepository;

import java.util.List;
import java.util.Optional;

/**
 * created by wenliang on 2021/9/19
 */
@Service
public class AttachmentBagService {
    private final AttachmentBagRepository attachmentBagRepository;

    @Autowired
    public AttachmentBagService(AttachmentBagRepository attachmentBagRepository) {
        this.attachmentBagRepository = attachmentBagRepository;
    }

    public Optional<AttachmentBag> findOne(long bagId) {
        return this.attachmentBagRepository.findById(bagId);
    }

    public AttachmentBag save(AttachmentBag attachmentBag) {
        return this.attachmentBagRepository.save(attachmentBag);
    }

}
