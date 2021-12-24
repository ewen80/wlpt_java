package pw.ewen.WLPT.services.resources.vod;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.entities.resources.ResourceCheckIn;
import pw.ewen.WLPT.domains.entities.resources.ResourceReadInfo;
import pw.ewen.WLPT.domains.entities.resources.vod.VodResource;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.resources.vod.VodResourceRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.FileService;
import pw.ewen.WLPT.services.SerialNumberService;
import pw.ewen.WLPT.services.resources.ResourceServiceFunction;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * created by wenliang on 2021-12-21
 */
@Service
public class VodResourceService implements ResourceServiceFunction<VodResource> {

    private final VodResourceRepository vodResourceRepository;
    private final UserContext userContext;
    private final UserRepository userRepository;
    private final BizConfig bizConfig;
    private final FileService fileService;
    private final SerialNumberService serialNumberService;

    public VodResourceService(VodResourceRepository vodResourceRepository, UserContext userContext, UserRepository userRepository, BizConfig bizConfig, FileService fileService, SerialNumberService serialNumberService) {
        this.vodResourceRepository = vodResourceRepository;
        this.userContext = userContext;
        this.userRepository = userRepository;
        this.bizConfig = bizConfig;
        this.fileService = fileService;
        this.serialNumberService = serialNumberService;
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<VodResource> findAll() {
        return vodResourceRepository.findAll();
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<VodResource> findAll(String filter) {
        SearchSpecificationsBuilder<VodResource> builder = new SearchSpecificationsBuilder<>();
        return this.vodResourceRepository.findAll(builder.build(filter));
    }

    @Override
    @PostAuthorize("hasPermission(returnObject.orElse(null), 'read')")
    public Optional<VodResource> findOne(long id) {
        return this.vodResourceRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasPermission(#vodResource, 'create')")
    public VodResource add(VodResource vodResource) {
        // 新增生成一条新的ResourceCheckIn记录
        if(vodResource.getId() == 0) {
            // 生成编号
            String serialNumber = serialNumberService.generate(bizConfig.getSerialNumber().getVodName(), bizConfig.getSerialNumber().getVodBasis());
            vodResource.setBh(serialNumber);

            ResourceCheckIn resourceCheckIn = new ResourceCheckIn(LocalDateTime.now(), userContext.getCurrentUser());
            vodResource.setResourceCheckIn(resourceCheckIn);

            this.vodResourceRepository.save(vodResource);
            return vodResource;
        } else {
            return null;
        }
    }

    @Override
    @PreAuthorize("hasPermission(#vodResource, 'write')")
    public void update(VodResource vodResource) {
        if(vodResource.getId() > 0) {
            this.vodResourceRepository.save(vodResource);
        }
    }

    @Override
    @PreAuthorize("hasPermission(#vodResource, 'delete')")
    public void delete(VodResource vodResource) {
        this.vodResourceRepository.deleteById(vodResource.getId());
    }

    @Override
    public void tagReaded(long resourceId, String userId) {
        this.vodResourceRepository.findById(resourceId).ifPresent(vod -> {
            ResourceReadInfo readInfo = new ResourceReadInfo();
            readInfo.setReadAt(LocalDateTime.now());
            userRepository.findById(userId).ifPresent(readInfo::setUser);
            vod.getReadInfoList().add(readInfo);
            this.vodResourceRepository.save(vod);
        });
    }

    @Override
    public void getFieldAuditWord(long resourceId, long fieldAuditId, OutputStream output) {
        this.findOne(resourceId).ifPresent(vod -> {
            Map<String, String> textFieldMap = new HashMap<>();
            Map<String, byte[]> imageFieldMap = new HashMap<>();
            Map<String, byte[][]> rowImageMap = new HashMap<>();

            textFieldMap.put("sysName", vod.getSysName());
            textFieldMap.put("deviceName", vod.getDeviceName());
            textFieldMap.put("manufacturer", vod.getManufacturer());
            textFieldMap.put("deviceModel", vod.getDeviceModel());
            textFieldMap.put("samplingMethod", vod.getSamplingMethod());
            textFieldMap.put("detectLocation", vod.getDetectLocation());
            textFieldMap.put("detectUnit", vod.getDetectUnit());
            textFieldMap.put("sysExplanation", vod.getSysExplanation());
            textFieldMap.put("detectBasis", vod.getDetectBasis());
            textFieldMap.put("detectOverview", vod.getDetectOverview());

            vod.getFieldAudits()
                    .stream()
                    .filter(audit -> audit.getId() == fieldAuditId)
                    .findFirst()
                    .ifPresent(audit -> {
                        textFieldMap.put("auditContent", audit.getContent());
                        textFieldMap.put("auditer", audit.getUser().getName());
                        textFieldMap.put("auditDepartment", audit.getAuditDepartment());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(bizConfig.getDateFormat().getPrintDateFormat());
                        textFieldMap.put("auditDate", audit.getAuditDate().format(formatter));
                        if (audit.getFzrSignature() != null && audit.getFzrSignature().getBytes() != null) {
                            imageFieldMap.put("signature", audit.getFzrSignature().getBytes());
                        }

                    });
            String template = bizConfig.getFile().getVodFieldAuditTemplate();

            try {
                this.fileService.getWord(template, textFieldMap, imageFieldMap, new HashMap<>(), rowImageMap, output);
            } catch (IOException | XmlException | InvalidFormatException ignored) {

            }

        });    }
}
