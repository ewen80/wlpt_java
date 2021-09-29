package pw.ewen.WLPT.services;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pw.ewen.WLPT.configs.biz.BizConfig;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Map;
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

    /**
     * 文件上传
     * @param file 上传的文件
     * @return 文件路径
     */
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

    /**
     * 删除文件
     * @param fileName 文件路径
     * @return 是否已删除
     */
    public boolean delete(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 生成pdf
     * @param templateFile 模板文件
     * @param fieldTextValueMap 表单文本值
     * @param fieldImageMap 表单图片值，图片使用base64保存
     * @param output pdf文件输出流
     */
    public void getPdf(String templateFile, Map<String, String> fieldTextValueMap, Map<String, byte[]> fieldImageMap, OutputStream output) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(templateFile), new PdfWriter(output));
        Document doc = new Document(pdfDoc);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        // 替换文本字段
        fieldTextValueMap.forEach((key,value)-> {
            if(value != null) {
                fields.get(key).setValue(value);
            }
            fields.get(key).setReadOnly(true);
        });
        // 替换图片字段
        fieldImageMap.forEach((key,value)->{
            String base64 = Base64.getEncoder().encodeToString(value);
            PdfArray position = fields.get(key).getWidgets().get(0).getRectangle();
            PdfPage page = fields.get(key).getWidgets().get(0).getPage();
            int pageNumber = pdfDoc.getPageNumber(page);

            ImageData imgData = ImageDataFactory.create(value);
            Image img = new Image(imgData).scaleAbsolute(100,100).setFixedPosition(pageNumber, position.toRectangle().getLeft(), position.toRectangle().getBottom());
            doc.add(img);

        });
        pdfDoc.close();
    }

    public void getPdf(String templateFile, Map<String,String> fieldTextValueMap, OutputStream output) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(templateFile), new PdfWriter(output));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        // 替换文本字段
        fieldTextValueMap.forEach((key,value)-> {
            fields.get(key).setValue(value);
            fields.get(key).setReadOnly(true);
        });
        pdfDoc.close();
    }

}
