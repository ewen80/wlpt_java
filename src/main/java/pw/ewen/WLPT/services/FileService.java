package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pw.ewen.WLPT.configs.biz.BizConfig;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

/**
 * created by wenliang on 2021-05-21
 * 文件io服务，用于附件上传，删除等
 */
@Service
public class FileService {

    private final BizConfig bizConfig;

    @Autowired
    public FileService(BizConfig bizConfig) {
        this.bizConfig = bizConfig;
    }

    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);

        String fileRootPath = bizConfig.getFile().getFileUploadRootPath();
        // 获取当前系统日期(年、月）
        LocalDate today = LocalDate.now();
        String year = String.valueOf(today.getYear());
        String month = String.valueOf(today.getMonthValue());
        // 根据年月计算文件保存路径
        String dirPath = year + File.separatorChar + month + File.separatorChar;
        String filePath = fileRootPath + dirPath;

        // 目标目录
        File destDir = new File(filePath);
        // 目录不存在就创建
        if(!destDir.exists()) {
            destDir.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + '.' + fileType;
        File destFile = new File(filePath + fileName);
        file.transferTo(destFile);

        return dirPath + fileName;
    }

    public boolean delete(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }
}
