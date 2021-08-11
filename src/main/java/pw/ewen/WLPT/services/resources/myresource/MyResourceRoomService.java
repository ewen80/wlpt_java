package pw.ewen.WLPT.services.resources.myresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.Attachment;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResourceRoom;
import pw.ewen.WLPT.repositories.resources.myresource.RoomRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.FileService;

import java.util.List;

/**
 * created by wenliang on 2021-05-11
 */
@Service
public class MyResourceRoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public MyResourceRoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public MyResourceRoom findOne(long id) {
        return  this.roomRepository.findOne(id);
    }

    public List<MyResourceRoom> findAll() {
        return this.roomRepository.findAll();
    }

    public List<MyResourceRoom> findAll(String filter) {
        SearchSpecificationsBuilder<MyResourceRoom> builder = new SearchSpecificationsBuilder<>();
        return this.roomRepository.findAll(builder.build(filter));
    }

    public List<MyResourceRoom> findByMyResourceId(long myResourceId) {
        return this.roomRepository.findByMyResource_id(myResourceId);
    }

    public List<MyResourceRoom> findByIdIn(List<Long> roomIds) {
        return this.roomRepository.findByIdIn(roomIds);
    }

    public MyResourceRoom save(MyResourceRoom room) {
        return this.roomRepository.save(room);
    }

    public void delete(long roomId) {
        this.roomRepository.delete(roomId);
    }
}
