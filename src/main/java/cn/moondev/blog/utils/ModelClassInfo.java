package cn.moondev.spider.utils;

public class ModelClassInfo {

    public String field;
    public String mysqlField;
    public String content;
    public String jsonFiled;
    public String modifier;

    public ModelClassInfo(){

    }
    public ModelClassInfo(String field,String content) {
        this.field = field;
        this.jsonFiled = jsonFiled;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ModelClassInfo{" +
                "field='" + field + '\'' +
                ", mysqlField='" + mysqlField + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
