package pw.ewen.WLPT.services.resources.yule;

import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwWc;
import pw.ewen.WLPT.repositories.resources.yule.YuleResourceGwWcRepository;

import java.util.Optional;

/**
 * created by wenliang on 2021/10/12
 */
@Service
public class YuleResourceGwWcService {
    private final YuleResourceGwWcRepository yuleResourceGwWcRepository;

    public YuleResourceGwWcService(YuleResourceGwWcRepository yuleResourceGwWcRepository) {
        this.yuleResourceGwWcRepository = yuleResourceGwWcRepository;
    }

    public Optional<YuleResourceGwWc> findOne(long id) {
        return this.yuleResourceGwWcRepository.findById(id);
    }
    public YuleResourceGwWc save(YuleResourceGwWc wc) {
        return this.yuleResourceGwWcRepository.save(wc);
    }
    public void delete(YuleResourceGwWc wc) {
        this.yuleResourceGwWcRepository.deleteById(wc.getId());
    }
}
