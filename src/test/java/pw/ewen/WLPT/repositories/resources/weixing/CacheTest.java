package pw.ewen.WLPT.repositories.resources.weixing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pw.ewen.WLPT.services.resources.weixing.WeixingResourceService;

/**
 * created by wenliang on 2021/11/30
 */
@SpringBootTest
public class CacheTest {

    private final WeixingResourceRepository weixingResourceRepository;

    @Autowired
    public CacheTest(WeixingResourceRepository weixingResourceRepository) {
        this.weixingResourceRepository = weixingResourceRepository;
    }

    @Test
//    @Transactional
    public void hibernateCache() {
//        WeixingResource weixing = new WeixingResource();
//        weixing = weixingResourceRepository.save(weixing);
        weixingResourceRepository.findAll();
        weixingResourceRepository.findAll();
    }
}
