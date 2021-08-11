package pw.ewen.WLPT.controllers.resources.weixings;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.resources.myresource.MyResourceController;
import pw.ewen.WLPT.controllers.utils.MyPage;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.DTOs.resources.myresource.MyResourceDTO;
import pw.ewen.WLPT.domains.DTOs.resources.weixing.WeixingResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.SignatureDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.weixing.WeixingResourceDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResource;
import pw.ewen.WLPT.domains.entities.resources.weixing.WeixingResource;
import pw.ewen.WLPT.services.AttachmentService;
import pw.ewen.WLPT.services.resources.weixing.WeixingResourceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 卫星现场核查
 */
@RestController
@RequestMapping(value = "resources/weixings")
public class WeixingResourceController {

    private final WeixingResourceService weixingResourceService;
    private final AttachmentService attachmentService;
    private final WeixingResourceDTOConvertor weixingResourceDTOConvertor;
    private final SignatureDTOConvertor signatureDTOConvertor;

    public WeixingResourceController(WeixingResourceService weixingResourceService, AttachmentService attachmentService, WeixingResourceDTOConvertor weixingResourceDTOConvertor, SignatureDTOConvertor signatureDTOConvertor) {
        this.weixingResourceService = weixingResourceService;
        this.attachmentService = attachmentService;
        this.weixingResourceDTOConvertor = weixingResourceDTOConvertor;
        this.signatureDTOConvertor = signatureDTOConvertor;
    }

    //将实体对象转为DTO对象的内部辅助类
    class dtoConvertor implements Converter<WeixingResource, WeixingResourceDTO> {
        @Override
        public WeixingResourceDTO convert(WeixingResource weixingResource) {
            return  weixingResourceDTOConvertor.toDTO(weixingResource);
        }
    }

    /**
     * 获取卫星场地核查信息
     * @param filter 过滤器
     * @apiNote 返回结果不分页
     */
    @GetMapping(value = "/all")
    public List<WeixingResourceDTO> getResources(@RequestParam(name = "filter", value = "") String filter){
        List<WeixingResource> weixingResources = this.weixingResourceService.findAll(filter);
        List<WeixingResourceDTO> weixingResourceDTOS = new ArrayList<>();
        weixingResources.forEach((weixingResource -> {
            weixingResourceDTOS.add(weixingResourceDTOConvertor.toDTO(weixingResource));
        }));
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

        List<WeixingResource> allResource = pageInfo.getFilter().isEmpty() ? this.weixingResourceService.findAll() : this.weixingResourceService.findAll(pageInfo.getFilter());
        resourceResult = new MyPage<>(allResource, pr).getPage();
        return resourceResult.map(new dtoConvertor());
    }

    /**
     * 获取单个卫星场地核查信息
     * @param id 资源id
     * @apiNote  卫星场地核查信息
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<WeixingResourceDTO> getOne(@PathVariable(value = "id") long id) {
        WeixingResource weixingResource = weixingResourceService.findOne(id);

        return weixingResource == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(weixingResourceDTOConvertor.toDTO(weixingResource, false), HttpStatus.OK);
    }

    /**
     * 删除卫星场地核查信息
     * @param ids 资源id，多个id用逗号分隔
     */
    @DeleteMapping(value = "/{ids}")
    public void delete(@PathVariable(value = "ids") String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        idsList.forEach( (id) -> {
            long longId = Long.parseLong(id);
            try {
                WeixingResource weixingResource = weixingResourceService.findOne(longId);
                weixingResourceService.delete(weixingResource);
            } catch (NumberFormatException ignored) {}
        });
    }

    /**
     * 保存
     * @param weixingResourceDTO 卫星场地核查信息
     * @apiNote 返回卫星场地核查信息
     */
    @PostMapping()
    public WeixingResourceDTO save(@RequestBody WeixingResourceDTO weixingResourceDTO) {
        WeixingResource weixingResource = weixingResourceDTOConvertor.toWeixingResource(weixingResourceDTO, weixingResourceService, attachmentService);
        weixingResourceService.save(weixingResource);
        return weixingResourceDTOConvertor.toDTO(weixingResource);
    }

    /**
     * 保存签名信息
     * @param signatureDTO 签名信息
     * @apiNote 签名图片后缀名不要加点号,保存 jpg 或者 png字样.base64字段只保存图片信息,不要添加data:image等前缀字符.
     */
    @PostMapping(value = "/signature/{id}")
    public WeixingResourceDTO saveSignature(@PathVariable(value = "id") long id, @RequestBody SignatureDTO signatureDTO) {
        Signature signature = signatureDTOConvertor.toSignature(signatureDTO);
        WeixingResource weixingResource = weixingResourceService.findOne(id);
        weixingResource.setSign(signature);
        weixingResourceService.save(weixingResource);
        return weixingResourceDTOConvertor.toDTO(weixingResource, false);
    }
}
