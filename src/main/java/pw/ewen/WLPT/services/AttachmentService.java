package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.AttachmentRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * created by wenliang on 2021/5/17
 */
@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Optional<Attachment> findOne(String id) {
        return this.attachmentRepository.findById(id);
    }

    public List<Attachment> findAll(String filter) {
        SearchSpecificationsBuilder<Attachment> builder = new SearchSpecificationsBuilder<>();
        return this.attachmentRepository.findAll(builder.build(filter));
    }
}
