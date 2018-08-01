package cn.moondev.blog.configuration;

import cn.moondev.framework.model.AppException;

public enum MessageCode {

    NAME_REPEAT("000001", "名称已存在，请使用其他名称"),
    NAME_ERROR("000002", "名称格式错误：为空或者超长"),
    DESC_ERROR("000003", "描述格式错误：为空或者超长"),
    IMAGE_URL_ERROR("000004", "图片链接格式错误：长度超长或为空"),
    API_ERROR("000005", "未从豆瓣获取到数据，请检查后重试"),
    ARTICLE_NOT_EXISTS("000006", "文章不存在，操作失败");


    private String code;

    private String message;

    MessageCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static AppException ex(MessageCode messageCode) {
        return new AppException(messageCode.getCode(), messageCode.getMessage());
    }
}
