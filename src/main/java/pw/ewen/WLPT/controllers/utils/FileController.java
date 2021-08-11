package pw.ewen.WLPT.controllers.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.domains.DTOs.SignatureDTO;
import pw.ewen.WLPT.services.FileService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * 文件上传，删除等操作
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {

    private static class UploadReturn {
        public String name;
        public String status;
        public String date;
    }

    private final FileService fileService;
    private final BizConfig bizConfig;

    @Autowired
    public FileController(FileService fileService, BizConfig bizConfig) {
        this.fileService = fileService;
        this.bizConfig = bizConfig;
    }

    /**
     * 上传文件按照年月分文件夹存储
     * @param file 文件流
     * @apiNote  {name: "filename", status: "result", date: "上传时间"}, result: "done"完成 "error"错误
     */
    @PostMapping(value = "/upload", produces = "application/json")
    public UploadReturn upload(@RequestParam("file") MultipartFile file)  {
        UploadReturn uploadReturn = new UploadReturn();
        try{
            uploadReturn.name = fileService.upload(file);
            uploadReturn.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            uploadReturn.status = "done";
        }catch (IOException e) {
            uploadReturn.status = "error";
        }
        return uploadReturn;
    }

    /**
     * 删除文件
     * @param fileName 文件名
     */
    @DeleteMapping("/delete")
    public boolean delete(@RequestBody String fileName) {
        return fileService.delete(bizConfig.getFile().getFileUploadRootPath() + fileName);
    }
}
