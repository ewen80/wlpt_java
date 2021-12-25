package pw.ewen.WLPT.controllers.resources;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.MyPage;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.resources.BaseResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.DTOBaseConvertor;
import pw.ewen.WLPT.domains.entities.resources.BaseResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.ResourceServiceBase;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * created by wenliang on 2021-12-24
 */
public abstract class ResourceControllerBase<T extends BaseResource, D extends BaseResourceDTO> {
    private final UserContext userContext;
    private final ResourceServiceBase<T> resourceService;
    private final DTOBaseConvertor<T, D> dtoConvertor;

    protected ResourceControllerBase(UserContext userContext, ResourceServiceBase<T> resourceService, DTOBaseConvertor<T, D> dtoConvertor) {
        this.userContext = userContext;
        this.resourceService = resourceService;
        this.dtoConvertor = dtoConvertor;
    }

    /**
     * 获取资源
     * @param pageInfo 分页信息
     * @apiNote 结果分页
     */
    @GetMapping()
    public Page<D> getResourcesWithPage(PageInfo pageInfo) {
        Page<T> resourceResult;
        PageRequest pr = pageInfo.getPageRequest();
        String userId = userContext.getCurrentUser().getId();

        List<T> allResource = pageInfo.getFilter().isEmpty() ? this.resourceService.findAll() : this.resourceService.findAll(pageInfo.getFilter());
        allResource.sort((w1, w2) -> Boolean.compare(w1.isReaded(userId), w2.isReaded(userId)));
        resourceResult = new MyPage<>(allResource, pr).getPage();
        return resourceResult.map(dtoConvertor::toDTO);
    }

    /**
     * 获取单个信息
     * @param id 资源id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<D> getOne(@PathVariable(value = "id") long id) {
        return resourceService.findOne(id)
                .map(resource -> new ResponseEntity<>(dtoConvertor.toDTO(resource, false), HttpStatus.OK))
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
                resourceService.findOne(longId).ifPresent(resourceService::delete);
            } catch (NumberFormatException ignored) {}
        });
    }

    /**
     * 新增资源
     * @param dto dto信息
     * @apiNote 返回卫星场地信息
     */
    @PostMapping()
    public ResponseEntity<D> add(@RequestBody D dto) {
        if(dto.getId() == 0) {
            T resource = dtoConvertor.toResource(dto);
            return new ResponseEntity<>(dtoConvertor.toDTO(resourceService.add(resource)), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    /**
     * 修改
     * @param dto dto信息
     */
    @PutMapping()
    public void update(@RequestBody D dto) {
        if(dto.getId() > 0) {
            T resource = dtoConvertor.toResource(dto);
            resourceService.update(resource);
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
        this.resourceService.getFieldAuditWord(resourceId, fieldAuditId, output);
        return new ResponseEntity<>(output.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * 设置已读
     * @param resourceId 场地id
     * @param userId    用户id
     */
    @PutMapping(value = "/read/{resourceId}/{userId}")
    public void read(@PathVariable long resourceId, @PathVariable String userId) {
        this.resourceService.tagReaded(resourceId, userId);
    }
}
