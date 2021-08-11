package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.SerialNumber;
import pw.ewen.WLPT.repositories.SerialNumberRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by wenliang on 2021-07-28
 */
@Service
public class SerialNumberService {

    private final SerialNumberRepository serialNumberRepository;

    @Autowired
    public SerialNumberService(SerialNumberRepository serialNumberRepository) {
        this.serialNumberRepository = serialNumberRepository;
    }

    /**
     * 生成序列号
     * @param name 模板名
     * @param basisTemplate 生成依据，格式为类似 AABB{3},[date:yyyy]{3}=2021{3}
     * @return 序列号
     */
    public String generate(String name, String basisTemplate) throws RuntimeException {
        String basis = this.getBasis(basisTemplate);
        SerialNumber serialNumber = serialNumberRepository.findByNameAndBasis(name,basis);
        String newSerialNumber = "";
        // 查找和参数basis匹配的记录，如果没有则新增一条记录
        if(serialNumber != null) {
            int number = serialNumber.getNumber();

            try {
                newSerialNumber = this.generateSerialNumber(basis, ++number);
                serialNumber.setNumber(number);
                serialNumberRepository.save(serialNumber);
            } catch(RuntimeException e) {
                throw e;
            }

        } else {
            // 没有找到依据，往数据库添加新的依据，并返回序列号
            serialNumber = new SerialNumber();
            serialNumber.setName(name);
            serialNumber.setBasis(basis);
            serialNumber.setNumber(1);

            try {
                newSerialNumber = this.generateSerialNumber(basis, 1);
                serialNumberRepository.save(serialNumber);
            } catch(RuntimeException e) {
                throw e;
            }
        }
        return newSerialNumber;
    }

    // 解析依据文本， [date:yyyy] 当前日期的四位年份
    private String getBasis(String basis) {
        // 分析basis字符串，分解出模式和格式 date是模式  yyyy是格式
        Pattern pattern = Pattern.compile("\\[(\\w+):(\\w+)\\]");
        Matcher matcher = pattern.matcher(basis);
        while(matcher.find()) {
            String mode = matcher.group(1);
            String format = matcher.group(2);

            switch (mode) {
                case "date":
                    switch (format) {
                        case "yyyy":
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
                            String yearString = formatter.format(LocalDate.now());
                            return matcher.replaceAll(yearString);
                    }
                    break;
            }
        }
        // 没有匹配到模式依据
        return basis;
    }

    // 获取序列号指定长度
    private int getSerialNumberLength(String basis) throws NumberFormatException {
        int beginIndex = basis.indexOf('{');
        int endIndex = basis.lastIndexOf('}');
        String numberLengthStr = basis.substring(beginIndex+1, endIndex);
        return Integer.parseInt(numberLengthStr);
    }

    private String generateSerialNumber(String basis, int number) throws RuntimeException {
        try {
            int numberLength = this.getSerialNumberLength(basis);
            String numberFormat = "%0" + numberLength + "d";
            String formatedNumber = String.format(numberFormat, number);
            return basis.replaceAll("\\{\\d\\}", formatedNumber);
        } catch (NumberFormatException e) {
            throw new RuntimeException("序列号生成依据字符串格式有误，请检查{}内是否是数字。");
        }

    }

}
