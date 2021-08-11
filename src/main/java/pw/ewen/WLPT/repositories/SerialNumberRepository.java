package pw.ewen.WLPT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.SerialNumber;

/**
 * created by wenliang on 2021-07-28
 */
@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber, String> {
    SerialNumber findByNameAndBasis(String name, String basis);
}
