package cn.moondev.spider.utils;

import com.google.common.base.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 字符串特殊处理，常用的处理请直接调用guava.Strings即可，不要在此类中添加方法
 */
public class StringUtils {

    /**
     * 日期处理，爬虫爬取的数据中，日期的格式为：2018/6/5 0:00:00
     * 这里直接转换成格式：yyyy-MM-dd
     *
     * @param reportDate
     * @return
     */
    public static String convertReportDate(String reportDate) {
        if (reportDate.contains("-")) {
            return reportDate;
        }
        reportDate = reportDate.substring(0, reportDate.length() - 8);
        LocalDate date = LocalDate.parse(reportDate, DateTimeFormatter.ofPattern("yyyy/M/d"));
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 返回非空的字符串
     *
     * @param strs
     * @return
     */
    public static String notEmptyStr(String... strs) {
        for (String string : strs) {
            if (!Strings.isNullOrEmpty(string)) {
                return string;
            }
        }
        return "";
    }
}
