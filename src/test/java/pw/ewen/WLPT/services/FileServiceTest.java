package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * created by wenliang on 2021/9/23
 */
@SpringBootTest
class FileServiceTest {

    @Autowired
    FileService fileService;

//    @Test
//    void getWord() throws IOException, XmlException, InvalidFormatException {
//        Map<String, String> textMap = new HashMap<>();
//        Map<String, byte[]> imageMap = new HashMap<>();
//        Map<String, String[][]> rowMap = new HashMap<>();
//
//        imageMap.put("signature", Files.readAllBytes(Paths.get("E:\\MyVIFs\\场地查看表单\\gps.png")));
//        textMap.put("dwmc", "单位1");
////        textMap.put("auditDate", "2021-11-1");
//        String[][] row1Data = {{"1","2","3","4","5","6","7"},{"a","b","c","d","e","f","g"}};
//        String[][] row2Data = {{"1","2","3","4","5"},{"a","b","c","d","e"}};
//        rowMap.put("row1", row1Data);
////        rowMap.put("row2", row2Data);
//
//        OutputStream output = new FileOutputStream("E:\\MyVIFs\\场地查看表单\\娱乐场所-1.docx");
//        fileService.getWord("E:\\MyVIFs\\场地查看表单\\娱乐场所.docx", textMap, imageMap, rowMap, output);
////        fileService.getWord("E:\\MyVIFs\\场地查看表单\\addRow.docx", textMap, imageMap, rowMap, output);
//        output.close();
//    }
}