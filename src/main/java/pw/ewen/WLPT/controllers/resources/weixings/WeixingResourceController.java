package pw.ewen.WLPT.controllers.resources.weixings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.MyPage;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.resources.weixing.WeixingResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.weixing.WeixingResourceDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.weixing.WeixingResourceService;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * 卫星场地
 */
@RestController
@RequestMapping(value = "/resources/weixings")
public class WeixingResourceController {

    private final WeixingResourceService weixingResourceService;
    private final WeixingResourceDTOConvertor weixingResourceDTOConvertor;
    private final UserContext userContext;

    public WeixingResourceController(WeixingResourceService weixingResourceService, WeixingResourceDTOConvertor weixingResourceDTOConvertor, UserContext userContext) {
        this.weixingResourceService = weixingResourceService;
        this.weixingResourceDTOConvertor = weixingResourceDTOConvertor;
        this.userContext = userContext;
    }


    /**
     * 获取卫星场地信息
     * @param filter 过滤器
     * @apiNote 返回结果不分页
     */
    @GetMapping(value = "/all")
    public List<WeixingResourceDTO> getResources(@RequestParam(name = "filter") String filter){
        List<WeixingResource> weixingResources = this.weixingResourceService.findAll(filter);
        List<WeixingResourceDTO> weixingResourceDTOS = new ArrayList<>();
        weixingResources.forEach((weixingResource -> weixingResourceDTOS.add(weixingResourceDTOConvertor.toDTO(weixingResource))));
        return weixingResourceDTOS;
    }

    /**
     * 获取资源
     * @param pageInfo 分页信息
     * @apiNote 结果分页
     */
    @GetMapping()
    public Page<WeixingResourceDTO> getResourcesWithPage(PageInfo pageInfo) {
        Page<WeixingResource> resourceResult;
        PageRequest pr = pageInfo.getPageRequest();
        String userId = userContext.getCurrentUser().getId();

        List<WeixingResource> allResource = pageInfo.getFilter().isEmpty() ? this.weixingResourceService.findAll() : this.weixingResourceService.findAll(pageInfo.getFilter());
        allResource.sort((w1, w2) -> Boolean.compare(w1.isReaded(userId), w2.isReaded(userId)));
        resourceResult = new MyPage<>(allResource, pr).getPage();
        return resourceResult.map(weixingResourceDTOConvertor::toDTO);
    }

    /**
     * 获取单个卫星场地信息
     * @param id 资源id
     * @apiNote  卫星场地信息
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<WeixingResourceDTO> getOne(@PathVariable(value = "id") long id) {
        return weixingResourceService.findOne(id)
                .map(weixingResource -> new ResponseEntity<>(weixingResourceDTOConvertor.toDTO(weixingResource, false), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 删除卫星场地信息
     * @param ids 资源id，多个id用逗号分隔
     */
    @DeleteMapping(value = "/{ids}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "ids") String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        idsList.forEach( (id) -> {
            long longId = Long.parseLong(id);
            try {
                weixingResourceService.findOne(longId).ifPresent(weixingResourceService::delete);
            } catch (NumberFormatException ignored) {}
        });
    }

    /**
     * 新增卫星场地
     * @param weixingResourceDTO 卫星场地信息
     * @apiNote 返回卫星场地信息
     */
    @PostMapping()
    public ResponseEntity<WeixingResourceDTO> add(@RequestBody WeixingResourceDTO weixingResourceDTO) {
        if(weixingResourceDTO.getId() == 0) {
            WeixingResource weixingResource = weixingResourceDTOConvertor.toWeixingResource(weixingResourceDTO);
            weixingResourceService.add(weixingResource);
            WeixingResourceDTO dto = weixingResourceDTOConvertor.toDTO(weixingResource);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    /**
     * 修改卫星场地
     * @param weixingResourceDTO 卫星场地信息
     */
    @PutMapping()
    public void update(@RequestBody WeixingResourceDTO weixingResourceDTO) {
        if(weixingResourceDTO.getId() > 0) {
            WeixingResource weixingResource = weixingResourceDTOConvertor.toWeixingResource(weixingResourceDTO);
            weixingResourceService.update(weixingResource);
        }
    }

    /**
     * 获取场地审核意见表pdf
     * @param weixingResourceId 卫星场地id
     */
    @GetMapping(value = "/print/{weixingResourceId}/{fieldAuditId}")
    public ResponseEntity<byte[]> print(@PathVariable long weixingResourceId, @PathVariable long fieldAuditId){
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.weixingResourceService.getFieldAuditWord(weixingResourceId, fieldAuditId, output);
        return new ResponseEntity<>(output.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * 设置已读
     * @param resourceId 场地id
     * @param userId    用户id
     */
    @PutMapping(value = "/read/{resourceId}/{userId}")
    public void read(@PathVariable long resourceId, @PathVariable String userId) {
        this.weixingResourceService.tagReaded(resourceId, userId);
    }
}
