package pw.ewen.WLPT.services.resources.yule;

import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceYyBase;
import pw.ewen.WLPT.repositories.resources.yule.YuleResourceYyBaseRepository;

import java.util.Optional;

/**
 * created by wenliang on 2021/10/6
 */
@Service
public class YuleResourceYyBaseService {
    private final YuleResourceYyBaseRepository yuleResourceYyBaseRepository;

    public YuleResourceYyBaseService(YuleResourceYyBaseRepository yuleResourceYyBaseRepository) {
        this.yuleResourceYyBaseRepository = yuleResourceYyBaseRepository;
    }

    public Optional<YuleResourceYyBase> findOne(long id) {
        return this.yuleResourceYyBaseRepository.findById(id);
    }
}
