package pw.ewen.WLPT.repositories.utils;

/**
 * created by wenliang on 2021-11-26
 * 已读资源数
 */
public interface GetReadedResourceCount {
    /**
     * 获取未读资源
     * @param userId 用户id
     * @return 未读资源数
     */
    int getReadedCount(String userId);
}
