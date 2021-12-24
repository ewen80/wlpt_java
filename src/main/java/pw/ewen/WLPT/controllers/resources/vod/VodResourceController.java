package pw.ewen.WLPT.controllers.resources.vod;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.MyPage;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.resources.vod.VodResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.vod.VodResourceDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.vod.VodResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.vod.VodResourceService;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 视频点播
 */
@RestController
@RequestMapping(value = "/resources/vods")
public class VodResourceController {
    private final UserContext userContext;
    private final VodResourceService vodResourceService;
    private final VodResourceDTOConvertor vodResourceDTOConvertor;

    public VodResourceController(UserContext userContext, VodResourceService vodResourceService, VodResourceDTOConvertor vodResourceDTOConvertor) {
        this.userContext = userContext;
        this.vodResourceService = vodResourceService;
        this.vodResourceDTOConvertor = vodResourceDTOConvertor;
    }

    /**
     * 获取资源
     * @param pageInfo 分页信息
     * @apiNote 结果分页
     */
    @GetMapping()
    public Page<VodResourceDTO> getResourcesWithPage(PageInfo pageInfo) {
        Page<VodResource> resourceResult;
        PageRequest pr = pageInfo.getPageRequest();
        String userId = userContext.getCurrentUser().getId();

        List<VodResource> allResource = pageInfo.getFilter().isEmpty() ? this.vodResourceService.findAll() : this.vodResourceService.findAll(pageInfo.getFilter());
        allResource.sort((w1, w2) -> Boolean.compare(w1.isReaded(userId), w2.isReaded(userId)));
        resourceResult = new MyPage<>(allResource, pr).getPage();
        return resourceResult.map(vodResourceDTOConvertor::toDTO);
    }

    /**
     * 获取单个信息
     * @param id 资源id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<VodResourceDTO> getOne(@PathVariable(value = "id") long id) {
        return vodResourceService.findOne(id)
                .map(vodResource -> new ResponseEntity<>(vodResourceDTOConvertor.toDTO(vodResource, false), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 删除信息
     * @param ids 资源id，多个id用逗号分隔
     */
    @DeleteMapping(value = "/{ids}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "ids") String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        idsList.forEach( (id) -> {
            long longId = Long.parseLong(id);
            try {
                vodResourceService.findOne(longId).ifPresent(vodResourceService::delete);
            } catch (NumberFormatException ignored) {}
        });
    }

    /**
     * 新增资源
     * @param dto dto信息
     * @apiNote 返回卫星场地信息
     */
    @PostMapping()
    public ResponseEntity<VodResourceDTO> add(@RequestBody VodResourceDTO dto) {
        if(dto.getId() == 0) {
            VodResource vodResource = vodResourceDTOConvertor.toVodResource(dto);
            return new ResponseEntity<>(vodResourceDTOConvertor.toDTO(vodResourceService.add(vodResource)), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    /**
     * 修改
     * @param dto dto信息
     */
    @PutMapping()
    public void update(@RequestBody VodResourceDTO dto) {
        if(dto.getId() > 0) {
            VodResource vodResource = vodResourceDTOConvertor.toVodResource(dto);
            vodResourceService.update(vodResource);
        }
    }

    /**
     * 打印场地审核意见表
     * @param resourceId 资源id
     */
    @GetMapping(value = "/print/{resourceId}/{fieldAuditId}")
    public ResponseEntity<byte[]> print(@PathVariable long resourceId, @PathVariable long fieldAuditId){
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.vodResourceService.getFieldAuditWord(resourceId, fieldAuditId, output);
        return new ResponseEntity<>(output.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * 设置已读
     * @param resourceId 场地id
     * @param userId    用户id
     */
    @PutMapping(value = "/read/{resourceId}/{userId}")
    public void read(@PathVariable long resourceId, @PathVariable String userId) {
        this.vodResourceService.tagReaded(resourceId, userId);
    }
}
