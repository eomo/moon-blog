package cn.moondev.spider.utils;

/**
 * HTML标签解析
 */
public class HtmlParse {

    /**
     * 从页面代码中解析出PDF的下载地址
     *
     * @param content
     * @return
     */
    public static String pdfUrl(String content) {
        int index = content.indexOf("http://pdf.dfcfw.com");
        if (index < 0) {
            return "";
        }
        content = content.substring(index);
        index = content.indexOf(".pdf");
        return content.substring(0, index + 4);
    }
}
