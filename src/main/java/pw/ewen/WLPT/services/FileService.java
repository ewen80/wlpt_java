package pw.ewen.WLPT.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pw.ewen.WLPT.configs.biz.BizConfig;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

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
     * 生成word文件。模板文件中占位符采用 ${XXX},xx,xx,xx 格式。逗号后的都是内容的选项，文字后面跟着的是  字体,大小；图像表示 宽度,高度,旋转角度
     * @param templateFile  模板文件路径
     * @param fieldTextValueMap 字符替换Map
     * @param fieldImageMap 图片替换Map
     * @param rowTextMap    行数据体替换Map
     * @param output    输出流
     */
    public void getWord(String templateFile, Map<String, String> fieldTextValueMap, Map<String, byte[]> fieldImageMap, Map<String, String[][]> rowTextMap, Map<String, byte[][]> rowImageMap, OutputStream output) throws IOException, XmlException, InvalidFormatException {
        //读取word源文件
        FileInputStream fileInputStream = new FileInputStream(templateFile);
        XWPFDocument document = new XWPFDocument(fileInputStream);
        // 获取表格
        XWPFTable table = document.getTables().get(0);
        // 读取表格第一行第一列的字体作为样板字体
        String fontFamily = table.getRow(0).getCell(0).getParagraphs().get(0).getRuns().get(0).getFontFamily();
        Double fontSize = table.getRow(0).getCell(0).getParagraphs().get(0).getRuns().get(0).getFontSizeAsDouble();

        // 是否跳出循环，直接下一行
        boolean jumpNextRow = false;

        for(int rowIndex=table.getRows().size()-1; rowIndex>=0 ; rowIndex--) {
            XWPFTableRow row = table.getRow(rowIndex);
            for(int cellIndex=0; cellIndex<row.getTableCells().size(); cellIndex++) {
                if(jumpNextRow) {
                    jumpNextRow = false;
                    break;
                }
                XWPFTableCell cell = row.getCell(cellIndex);
                for (XWPFParagraph p : cell.getParagraphs()) {
                    if(jumpNextRow) break;
                    // 遍历模板字段Map
                    for(String key : fieldTextValueMap.keySet()) {
                        if(p.getText().contains("${"+key+"}")) {
                            String oldContent = p.getText();
                            // 删除段落所有runs
                            int runSize = p.getRuns().size();
                            for(int i = 0; i < runSize; i++) {
                                p.removeRun(0);
                            }
                            XWPFRun run = p.insertNewRun(0);
                            run.setText(oldContent.replace("${"+key+"}", fieldTextValueMap.get(key)));
                            run.setFontFamily(fontFamily);
                            run.setFontSize(fontSize);
                            break;
                        }
                    }
                    // 遍历图片字段Map
                    for(String key : fieldImageMap.keySet()) {
                        if(p.getText().contains("${"+key+"}")) {
                            String content = p.getText();
                            String[] arrContent = content.split(",");
                            int width = Integer.parseInt(arrContent[1]);
                            int height = Integer.parseInt(arrContent[2]);
                            int rotation = Integer.parseInt(arrContent[3]);

                            // 删除段落所有run
                            int runSize = p.getRuns().size();
                            for(int i = 0; i < runSize; i++) {
                                p.removeRun(0);
                            }

                            // 实现图片的翻转
//                            InputStream imageStream = new ByteArrayInputStream(fieldImageMap.get(key));
//                            BufferedImage image = ImageIO.read(imageStream);
//                            final double rads = Math.toRadians(rotation);
//                            final double sin = Math.abs(Math.sin(rads));
//                            final double cos = Math.abs(Math.cos(rads));
//                            final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
//                            final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
//                            final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
//                            final AffineTransform at = new AffineTransform();
//                            at.translate(w / 2, h / 2);
//                            at.rotate(rads,0, 0);
//                            at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
//                            final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//                            rotateOp.filter(image,rotatedImage);
//
//                            ByteArrayOutputStream os = new ByteArrayOutputStream();
//                            ImageIO.write(rotatedImage, "png", os);
//
//                            InputStream is = new ByteArrayInputStream(os.toByteArray());
                            InputStream is = this.imageRotate(fieldImageMap.get(key), rotation);
                            p.insertNewRun(0).addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, "signature.png", Units.toEMU(width), Units.toEMU(height));

                            break;
                        }
                    }

                    // 插入数据行,如果不是首列，退出列查询
                    if(cellIndex > 0) {
                        break;
                    }
                    // 插入文字行
                    for(Map.Entry<String, String[][]> entry : rowTextMap.entrySet()) {
                        if(p.getText().contains("${"+entry.getKey()+"}")) {
                            // 找到要插入的行位置
                            int insRowIndex = rowIndex;
                            for(int i=0; i<entry.getValue().length; i++) {
                                // 写入新行的每个列
                                table.addRow(row, insRowIndex++);
                                XWPFTableRow newRow = table.getRow(insRowIndex);
                                for(int j=0; j<entry.getValue()[i].length; j++) {
                                    // 删除单位格所有段落文字
                                    int paragraphSize = newRow.getCell(j).getParagraphs().size();
                                    for(int ii=0; ii<paragraphSize; ii++){
                                        newRow.getCell(j).removeParagraph(0);
                                    }
                                    XWPFRun run = newRow.getCell(j).addParagraph().insertNewRun(0);
                                    run.setFontSize(fontSize);
                                    run.setFontFamily(fontFamily);
                                    run.setText(entry.getValue()[i][j]);
                                }
                            }
                            // 删除模板行,直接跳转下一行处理
                            table.removeRow(rowIndex);
                            jumpNextRow = true;
                            break;
                        }
                    }
                    // 如果已经执行插入文字行则跳过图片行插入检查
                    if(jumpNextRow) break;
                    // 插入图片行
                    for(Map.Entry<String, byte[][]> entry : rowImageMap.entrySet()) {
                        if(p.getText().contains("${"+entry.getKey()+"}")) {
                            String content = p.getText();
                            String[] arrContent = content.split(",");
                            int width = Integer.parseInt(arrContent[1]);
                            int height = Integer.parseInt(arrContent[2]);
                            int rotation = Integer.parseInt(arrContent[3]);

                            // 找到要插入的行位置
                            int insRowIndex = rowIndex;
                            for(int i=0; i<entry.getValue().length; i++) {
                                // 写入新行的每个列
                                table.addRow(row, insRowIndex++);
                                XWPFTableRow newRow = table.getRow(insRowIndex);
                                // 删除单位格所有段落文字
                                int paragraphSize = newRow.getCell(0).getParagraphs().size();
                                for(int ii=0; ii<paragraphSize; ii++){
                                    newRow.getCell(0).removeParagraph(0);
                                }

                                InputStream is = this.imageRotate(entry.getValue()[i], rotation);
                                XWPFRun run = newRow.getCell(0).addParagraph().insertNewRun(0);
                                run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, "signature.png", Units.toEMU(width), Units.toEMU(height));
                            }
                            // 删除模板行,直接跳转下一行处理
                            table.removeRow(rowIndex);
                            jumpNextRow = true;
                            break;
                        }
                    }
                }
            }
        }
        // 检查模板是否还有${}文本未替换，如果还有，则文本全部清空，行标记该行删除
        for(XWPFTableRow row : table.getRows()) {
            for(XWPFTableCell cell : row.getTableCells()) {
                for(XWPFParagraph p : cell.getParagraphs()) {
                    // 删除文本占位标记
                    if(Pattern.matches("\\S*\\$\\{\\w+\\}\\S*", p.getText())) {
                        int runSize = p.getRuns().size();
                        String oldContent = p.getText();
                        for(int i=0; i<runSize; i++) {
                            p.removeRun(0);
                        }
//                        Pattern pattern = Pattern.compile("\\$\\{\\w+\\}");
//                        Matcher matcher = pattern.matcher(oldContent);
//
//                        String newContent = matcher.replaceAll("");
                        XWPFRun run = p.insertNewRun(0);
//                        run.setText(newContent);
                        run.setText("");
                        run.setFontFamily(fontFamily);
                        run.setFontSize(fontSize);
                    }
                }
            }
        }

        fileInputStream.close();
        document.write(output);
    }

    /**
     * 旋转图片
     * @param imageBytes 图片字节数组
     * @param rotation 旋转角度
     */
    private InputStream imageRotate(byte[] imageBytes, int rotation) throws IOException {
        InputStream imageStream = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(imageStream);
        final double rads = Math.toRadians(rotation);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image,rotatedImage);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(rotatedImage, "png", os);

        return  new ByteArrayInputStream(os.toByteArray());
    }

//    /**
//     * 生成pdf
//     * @param templateFile 模板文件
//     * @param fieldTextValueMap 表单文本值
//     * @param fieldImageMap 表单图片值，图片使用base64保存
//     * @param output pdf文件输出流
//     */
//    public void getPdf(String templateFile, Map<String, String> fieldTextValueMap, Map<String, byte[]> fieldImageMap, OutputStream output) throws IOException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(templateFile), new PdfWriter(output));
//        Document doc = new Document(pdfDoc);
//        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
//        Map<String, PdfFormField> fields = form.getFormFields();
//        // 替换文本字段
//        fieldTextValueMap.forEach((key,value)-> {
//            if(value != null) {
//                fields.get(key).setValue(value);
//            }
//            fields.get(key).setReadOnly(true);
//        });
//        // 替换图片字段
//        fieldImageMap.forEach((key,value)->{
//            String base64 = Base64.getEncoder().encodeToString(value);
//            PdfArray position = fields.get(key).getWidgets().get(0).getRectangle();
//            PdfPage page = fields.get(key).getWidgets().get(0).getPage();
//            int pageNumber = pdfDoc.getPageNumber(page);
//
//            ImageData imgData = ImageDataFactory.create(value);
//            Image img = new Image(imgData).scaleAbsolute(100,100).setFixedPosition(pageNumber, position.toRectangle().getLeft(), position.toRectangle().getBottom());
//            doc.add(img);
//
//        });
//        pdfDoc.close();
//    }
//
//    public void getPdf(String templateFile, Map<String,String> fieldTextValueMap, OutputStream output) throws IOException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(templateFile), new PdfWriter(output));
//        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
//        Map<String, PdfFormField> fields = form.getFormFields();
//        // 替换文本字段
//        fieldTextValueMap.forEach((key,value)-> {
//            fields.get(key).setValue(value);
//            fields.get(key).setReadOnly(true);
//        });
//        pdfDoc.close();
//    }

}
