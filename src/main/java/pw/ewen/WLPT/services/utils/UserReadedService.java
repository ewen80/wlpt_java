package pw.ewen.WLPT.services.utils;

import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.utils.userunreaded.UserReaded;
import pw.ewen.WLPT.repositories.utils.UserReadedRepository;

import java.util.List;

/**
 * created by wenliang on 2021/11/27
 */
@Service
public class UserReadedService {

    private final UserReadedRepository userReadedRepository;

    public UserReadedService(UserReadedRepository userReadedRepository) {
        this.userReadedRepository = userReadedRepository;
    }

    public List<UserReaded> findByUserId(String userId) {
        return userReadedRepository.findByUserId(userId);
    }
}
