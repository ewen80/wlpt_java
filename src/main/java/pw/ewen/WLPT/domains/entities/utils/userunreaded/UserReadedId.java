package pw.ewen.WLPT.domains.entities.utils.userunreaded;

import java.io.Serializable;

/**
 * created by wenliang on 2021-11-26
 * UserUnreaded类的符合id
 */
public class UserReadedId implements Serializable {
    private static final long serialVersionUID = -6827714838464078152L;

    private String userId;
    private String resourceTypeClassName;

    public UserReadedId() {
    }

    public UserReadedId(String userId, String resourceTypeClassName) {
        this.userId = userId;
        this.resourceTypeClassName = resourceTypeClassName;
    }
}
