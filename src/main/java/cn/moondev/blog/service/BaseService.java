package cn.moondev.blog.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BaseService {

    /**
     * 回复时间距当前时间的描述，比如：5分钟前，23天前，1个月前，1年前
     */
    protected String replyTimeDesc(LocalDateTime createdTime) {
        LocalDateTime now = LocalDateTime.now();
        long period = createdTime.until(now, ChronoUnit.YEARS);
        if (period > 0) {
            return String.format(" %s 年前", period);
        }
        period = createdTime.until(now, ChronoUnit.MONTHS);
        if (period > 0) {
            return String.format(" %s 月前", period);
        }
        period = createdTime.until(now, ChronoUnit.WEEKS);
        if (period > 0) {
            return String.format(" %s 周前", period);
        }
        period = createdTime.until(now, ChronoUnit.DAYS);
        if (period > 0) {
            return String.format(" %s 天前", period);
        }
        period = createdTime.until(now, ChronoUnit.HOURS);
        if (period > 0) {
            return String.format(" %s 小时前", period);
        }
        period = createdTime.until(now, ChronoUnit.MINUTES);
        if (period > 0) {
            return String.format(" %s 分钟前", period);
        }
        return String.format(" %s 秒前", period);
    }
}
