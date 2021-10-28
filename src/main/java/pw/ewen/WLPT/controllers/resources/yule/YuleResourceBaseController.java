package pw.ewen.WLPT.controllers.resources.yule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.MyPage;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.resources.yule.YuleResourceBaseDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.yule.YuleResourceBaseDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.yule.YuleResourceBase;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.resources.yule.YuleResourceBaseService;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 娱乐场所
 */
@RestController
@RequestMapping(value = "/resources/yules")
public class YuleResourceBaseController {
    private final YuleResourceBaseService yuleResourceBaseService;
    private final YuleResourceBaseDTOConvertor yuleResourceBaseDTOConvertor;
    private final UserContext userContext;

    public YuleResourceBaseController(YuleResourceBaseService yuleResourceBaseService, YuleResourceBaseDTOConvertor yuleResourceBaseDTOConvertor, UserContext userContext) {
        this.yuleResourceBaseService = yuleResourceBaseService;
        this.yuleResourceBaseDTOConvertor = yuleResourceBaseDTOConvertor;
        this.userContext = userContext;
    }

    /**
     * 获取娱乐场所分页数据
     * @param pageInfo  分页信息
     */
    @GetMapping()
    public Page<YuleResourceBaseDTO> getResourcesWithPage(PageInfo pageInfo) {
        Page<YuleResourceBase> resourceResult;
        PageRequest pr = pageInfo.getPageRequest();
        String userId = userContext.getCurrentUser().getId();

        List<YuleResourceBase> allResource = pageInfo.getFilter().isEmpty() ? this.yuleResourceBaseService.findAll() : this.yuleResourceBaseService.findAll(pageInfo.getFilter());
        allResource.sort((y1,y2)->Boolean.compare(y1.isReaded(userId),y2.isReaded(userId)));
        resourceResult = new MyPage<>(allResource, pr).getPage();
        return resourceResult.map(yuleResourceBaseDTOConvertor::toDTO);
    }

    /**
     * 获取单个娱乐场地信息
     * @param id 资源id
     * @apiNote  卫星场地信息
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<YuleResourceBaseDTO> getOne(@PathVariable(value = "id") long id) {
        return yuleResourceBaseService.findOne(id)
                .map(yule -> new ResponseEntity<>(yuleResourceBaseDTOConvertor.toDTO(yule, false), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 新增娱乐场地
     * @param yuleResourceBaseDTO 娱乐场地信息
     * @apiNote 返回卫星场地信息
     */
    @PostMapping()
    public ResponseEntity<YuleResourceBaseDTO> add(@RequestBody YuleResourceBaseDTO yuleResourceBaseDTO) {
        if(yuleResourceBaseDTO.getId() == 0) {
            YuleResourceBase yuleResourceBase = yuleResourceBaseDTOConvertor.toYuleBase(yuleResourceBaseDTO);
            yuleResourceBaseService.add(yuleResourceBase);
            YuleResourceBaseDTO dto = yuleResourceBaseDTOConvertor.toDTO(yuleResourceBase);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    /**
     * 修改卫星场地
     * @param yuleResourceBaseDTO 娱乐场地信息
     */
    @PutMapping()
    public void update(@RequestBody YuleResourceBaseDTO yuleResourceBaseDTO) {
        if(yuleResourceBaseDTO.getId() > 0) {
            YuleResourceBase yuleResourceBase = yuleResourceBaseDTOConvertor.toYuleBase(yuleResourceBaseDTO);
            yuleResourceBaseService.update(yuleResourceBase);
        }
    }

    /**
     * 删除娱乐场地信息
     * @param ids 资源id，多个id用逗号分隔
     */
    @DeleteMapping(value = "/{ids}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "ids") String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        idsList.forEach( (id) -> {
            long longId = Long.parseLong(id);
            try {
                yuleResourceBaseService.findOne(longId).ifPresent(yuleResourceBaseService::delete);
            } catch (NumberFormatException ignored) {}
        });
    }

    /**
     * 获取场地审核意见表pdf
     * @param resourceId 卫星场地id
     * @param fieldAuditId 场地审查id
     */
    @GetMapping(value = "/print/{resourceId}/{fieldAuditId}")
    public ResponseEntity<byte[]> print(@PathVariable long resourceId, @PathVariable long fieldAuditId){
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.asMediaType());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.yuleResourceBaseService.getFieldAuditWord(resourceId, fieldAuditId, output);
        return new ResponseEntity<>(output.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * 设置已读
     * @param resourceId 场地id
     * @param userId    用户id
     */
    @PutMapping(value = "/read/{resourceId}/{userId}")
    public void read(@PathVariable long resourceId, @PathVariable String userId) {
        this.yuleResourceBaseService.tagReaded(resourceId, userId);
    }
}
