package pw.ewen.WLPT.security.acl;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

/**
 * created by wenliang on 2021/5/1
 * 场地核查系统的新增权限
 */
public class ChangdiPermission extends BasePermission {

    public static  final Permission FINISH = new ChangdiPermission(1 << 5, 'F'); // 32

    public ChangdiPermission(int mask) {
        super(mask);
    }

    public ChangdiPermission(int mask, char code) {
        super(mask, code);
    }

}
