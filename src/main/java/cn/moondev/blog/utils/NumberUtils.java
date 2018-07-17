package cn.moondev.spider.utils;

import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.util.Objects;

public class NumberUtils {

    /**
     * 字符串转换成分，字符串必须是数字，否则会抛出异常
     *
     * @param amount 金额
     * @return
     */
    public static long convertCent(String amount) {
        if (!Strings.isNullOrEmpty(amount)) {
            return new BigDecimal(amount).multiply(new BigDecimal(100)).longValue();
        }
        return 0;
    }

    public static int parseInt(Object number) {
        if (Objects.isNull(number)) {
            return 0;
        }
        try {
            return Integer.parseInt(number.toString());
        } catch (Exception e) {
            return 0;
        }
    }
}
