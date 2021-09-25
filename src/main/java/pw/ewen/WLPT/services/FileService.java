package pw.ewen.WLPT.services;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pw.ewen.WLPT.configs.biz.BizConfig;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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

    public void getPdf() throws IOException {
        String src = "d:\\1.pdf";
        String dest = "d:\\2.pdf";

        File file = new File(dest);
        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("name").setValue("闻亮");
        fields.get("age").setValue("40");
        fields.get("audit").setValue("许家印表示，要想业主所想、急业主所急，只有全力复工复产、恢复销售、恢复经营，才能保障业主权益，才能确保财富投资者的顺利兑付，才能逐步解决上下游合作伙伴的商票兑付，才能陆续不断归还金融机构的借款。全集团要提高认识、统一思想，把一切精力都投入到复工复产保交楼上。\n" +
                "\n" +
                "许家印说，要坚决落实好恒大财富投资者的兑付工作，这是全集团上下必须共同面对的头等大事。要以对投资者高度负责的态度，按照已经公布的三种方案全力做好兑付工作，尤其是在实物兑付方案上还要继续细化、深化。\n" +
                "\n" +
                "许家印说，集团各级领导必须坚守岗位、履行好职责，齐心协力、排除万难、共渡难关，全力以赴抓好复工复产、千方百计保交楼。\n" +
                "\n" +
                "此前，9月21日中秋节当天，许家印写给恒大全体员工的一封“家书”被曝光。在信中，许家印深情感谢员工的辛苦付出，向员工家属致敬，并坚信恒大一定能尽快走出至暗时刻，加快推进全面复工复产，实现“保交楼”目标，向购房者、投资者、合作伙伴和金融机构交出一份敢担当、负责任的答卷。");
        fields.get("name").setReadOnly(true);

        pdfDoc.close();
    }
}
