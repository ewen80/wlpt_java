package pw.ewen.WLPT.domains;

import java.util.HashMap;
import java.util.Map;

/**
 * created by wenliang on 2021-04-26
 * 区代码
 */
public class Region {
    public static HashMap<Integer, String> codes;

    public Region() {
        codes.put(0, "市属");
        codes.put(1,"黄浦区");
        codes.put(4,"徐汇区");
        codes.put(5,"长宁区");
        codes.put(6,"静安区");
        codes.put(7,"普陀区");
        codes.put(9,"虹口区");
        codes.put(10,"杨浦区");
        codes.put(12,"闵行区");
        codes.put(13,"宝山区");
        codes.put(14,"嘉定区");
        codes.put(15,"浦东新区");
        codes.put(17,"奉贤区");
        codes.put(18,"松江区");
        codes.put(19,"金山区");
        codes.put(20,"青浦区");
        codes.put(21,"崇明区");
    }
}
