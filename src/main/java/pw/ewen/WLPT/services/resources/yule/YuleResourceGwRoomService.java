package pw.ewen.WLPT.services.resources.yule;

import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceGwRoom;
import pw.ewen.WLPT.repositories.resources.yule.YuleResourceGwRoomRepository;

import java.util.Optional;

/**
 * created by wenliang on 2021/10/6
 */
@Service
public class YuleResourceGwRoomService {
    private final YuleResourceGwRoomRepository yuleResourceGwRoomRepository;

    public YuleResourceGwRoomService(YuleResourceGwRoomRepository yuleResourceGwRoomRepository) {
        this.yuleResourceGwRoomRepository = yuleResourceGwRoomRepository;
    }

    public Optional<YuleResourceGwRoom> findOne(long id) {
        return this.yuleResourceGwRoomRepository.findById(id);
    }
    public YuleResourceGwRoom save(YuleResourceGwRoom room) {
        return this.yuleResourceGwRoomRepository.save(room);
    }
    public void delete(YuleResourceGwRoom room) {
        this.yuleResourceGwRoomRepository.deleteById(room.getId());
    }
}
