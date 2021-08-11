package pw.ewen.WLPT.controllers.resources.myresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.MyPage;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.domains.DTOs.resources.myresource.MyResourceDTO;
import pw.ewen.WLPT.domains.dtoconvertors.resources.SignatureDTOConvertor;
import pw.ewen.WLPT.domains.dtoconvertors.resources.myresource.MyResourceDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.Signature;
import pw.ewen.WLPT.domains.entities.resources.myresource.MyResource;
import pw.ewen.WLPT.services.AttachmentService;
import pw.ewen.WLPT.services.resources.myresource.MyResourceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的资源
 * @apiNote 范例
 */
@RestController
@RequestMapping(value = "/resources/myresources")
public class MyResourceController {
    private final MyResourceService myResourceService;
    private final AttachmentService attachmentService;
    private final MyResourceDTOConvertor myResourceDTOConvertor;
    private final SignatureDTOConvertor signatureDTOConvertor;

    @Autowired
    public MyResourceController(MyResourceService myResourceService,
                                AttachmentService attachmentService,
                                MyResourceDTOConvertor myResourceDTOConvertor, SignatureDTOConvertor signatureDTOConvertor){
        this.myResourceService = myResourceService;
        this.attachmentService = attachmentService;
        this.myResourceDTOConvertor = myResourceDTOConvertor;
        this.signatureDTOConvertor = signatureDTOConvertor;
    }

    //将实体对象转为DTO对象的内部辅助类
    class dtoConvertor implements Converter<MyResource, MyResourceDTO> {
        @Override
        public MyResourceDTO convert(MyResource myResource) {
            return  myResourceDTOConvertor.toDTO(myResource);
        }
    }

    /**
     * 获取资源
     * @param filter 过滤器
     * @apiNote 结果不分页
     */
    @GetMapping(value = "/all")
    public List<MyResourceDTO> getResources(@RequestParam(name = "filter", value = "") String filter){
        List<MyResource> myResources = this.myResourceService.findAll(filter);
        List<MyResourceDTO> myResourceDTOS = new ArrayList<>();
        myResources.forEach((myResource -> {
            myResourceDTOS.add(myResourceDTOConvertor.toDTO(myResource));
        }));
        return myResourceDTOS;
    }

    /**
     * 获取资源
     * @param pageInfo 分页信息
     * @apiNote 结果分页
     */
    @GetMapping()
    public Page<MyResourceDTO> getResourcesWithPage(PageInfo pageInfo) {
        Page<MyResource> resourceResult;
        PageRequest pr = pageInfo.getPageRequest();

        List<MyResource> allResource = pageInfo.getFilter().isEmpty() ? this.myResourceService.findAll() : this.myResourceService.findAll(pageInfo.getFilter());
        resourceResult = new MyPage<>(allResource, pr).getPage();
        return resourceResult.map(new dtoConvertor());
    }

    /**
     * 获取单个资源
     * @param id 资源id
     * @apiNote 如果找不到资源返回404
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<MyResourceDTO> getOne(@PathVariable(value = "id") long id) {
        MyResource myResource = myResourceService.findOne(id);
        return myResource == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(myResourceDTOConvertor.toDTO(myResource, false), HttpStatus.OK);
    }

    /**
     * 删除资源
     * @param ids 资源id,多个id用逗号分隔
     */
    @DeleteMapping(value = "/{ids}")
    public void delete(@PathVariable(value = "ids") String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        idsList.forEach( (id) -> {
            long longId = Long.parseLong(id);
            try {
                MyResource myResource = myResourceService.findOne(longId);
                myResourceService.delete(myResource);
            } catch (NumberFormatException ignored) {}
        });
    }

    /**
     * 保存资源
     * @param myResourceDTO 资源DTO
     */
    @PostMapping()
    public MyResourceDTO save(@RequestBody MyResourceDTO myResourceDTO) {
        MyResource myResource = myResourceDTOConvertor.toMyResource(myResourceDTO, myResourceService, attachmentService);
        myResourceService.save(myResource);
        return myResourceDTOConvertor.toDTO(myResource, false);
    }

    /**
     * 保存签名信息
     * @param signatureDTO 签名信息
     * @apiNote 签名图片后缀名不要加点号,保存 jpg 或者 png字样.base64字段只保存图片信息,不要添加data:image等前缀字符.
     */
    @PostMapping(value = "/signature/{id}")
    public MyResourceDTO saveSignature(@PathVariable(value = "id") long id, @RequestBody  SignatureDTO signatureDTO) {
        Signature signature = signatureDTOConvertor.toSignature(signatureDTO);
        MyResource myResource = myResourceService.findOne(id);
        myResource.setSign(signature);
        myResourceService.save(myResource);
        return myResourceDTOConvertor.toDTO(myResource, false);
    }

    /**
     * 办结资源
     * @param id 资源id
     */
    @PutMapping(value = "/finish/{id}")
    public void finish(@PathVariable(value = "id") long id) {
        MyResource myResource = myResourceService.findOne(id);
        myResourceService.finish(myResource);
    }
}
