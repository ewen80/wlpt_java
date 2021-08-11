package pw.ewen.WLPT.repositories.resources.myresource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResourceRoom;

import java.util.List;

/**
 * created by wenliang on 2021-05-11
 */
public interface RoomRepository extends JpaRepository<MyResourceRoom, Long>, JpaSpecificationExecutor<MyResourceRoom> {
    List<MyResourceRoom> findByMyResource_id(long myResourceId);
    List<MyResourceRoom> findByIdIn(List<Long> roomIds);
}
