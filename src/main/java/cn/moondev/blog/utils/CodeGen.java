package cn.moondev.spider.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class CodeGen {

    public static void main(String[] args) throws Exception {
        createTableSQL("CompanyBaseInfo");
        mybatisMap();
        insertSQL("CompanyBaseInfo");
    }

    /**
     * 建表语句
     *
     * @param javaName
     * @throws Exception
     */
    public static void createTableSQL(String javaName) throws Exception {
        List<ModelClassInfo> cdxList = getModelList();
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append("t" + mysqlField(javaName)).append("(").append("\n");
        sb.append("        id BIGINT PRIMARY KEY AUTO_INCREMENT,");
        for (ModelClassInfo cdx : cdxList) {
            sb.append("        ");
            if ("string".equalsIgnoreCase(cdx.modifier)) {
                sb.append(cdx.mysqlField).append(" ").append("VARCHAR(64) NOT NULL DEFAULT '' COMMENT '").append(cdx.content).append("',").append("\n");
            } else if ("float".equalsIgnoreCase(cdx.modifier)) {
                sb.append(cdx.mysqlField).append(" ").append("FLOAT NOT NULL DEFAULT 0 COMMENT '").append(cdx.content).append("',").append("\n");
            } else {
                sb.append(cdx.mysqlField).append(" ").append("BIGINT NOT NULL DEFAULT 0 COMMENT '").append(cdx.content).append("',").append("\n");
            }
        }
        sb.append("        UNIQUE KEY unique_index_code(`stock_code`)").append("\n");
        sb.append(")ENGINE=INNODB DEFAULT CHARSET=utf8mb4;");
        System.out.println(sb.toString());
    }

    /**
     * 生成insert语句
     */
    public static void insertSQL(String javaName) throws Exception {
        List<ModelClassInfo> cdxList = getModelList();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append("t" + mysqlField(javaName)).append("(").append("\n");
        for (ModelClassInfo cdx : cdxList) {
            sb.append("        ");
            sb.append(cdx.mysqlField).append(",").append("\n");
        }
        sb.append(") VALUES (");
        for (ModelClassInfo cdx : cdxList) {
            sb.append("        ");
            sb.append(String.format("#{item.%s},", cdx.field)).append("\n");
        }
        sb.append(")ON DUPLICATE KEY UPDATE");
        for (ModelClassInfo cdx : cdxList) {
            sb.append("        ");
            sb.append(String.format("%s = VALUES(%s),", cdx.mysqlField, cdx.mysqlField)).append("\n");
        }
        System.out.println(sb.toString());
    }


    /**
     * 生成mybatis resultMap
     */
    public static void mybatisMap() throws Exception {
        List<ModelClassInfo> cdxList = getModelList();
        for (ModelClassInfo cdx : cdxList) {
            String format = "<result column=\"%s\" property=\"%s\"/>";
            System.out.println(String.format(format, cdx.mysqlField, cdx.field));
        }
    }

    public static List<ModelClassInfo> getModelList() throws Exception {
        File file = new File("D:\\WORKSPACE\\github.com\\east-money-spider\\src\\main\\java\\cn\\moondev\\spider\\model\\CompanyBaseInfo.java");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<ModelClassInfo> cdxList = Lists.newArrayList();
        String modifier = "";
        String content = "";
        String field = "";
        ModelClassInfo mode;
        while ((line = reader.readLine()) != null) {
            if (line.contains("//")) {
                content =  line.substring(7);
            }
            if (line.contains("public") && !line.contains("class")) {
                if (line.contains("public String")) {
                    field = line.substring(18);
                    modifier = "string";
                } else if (line.contains("public Float")){
                    field = line.substring(17);
                    modifier = "float";
                } else {
                    field = line.substring(19);
                    modifier = "integer";
                }
            }
            if (!Strings.isNullOrEmpty(content) && !Strings.isNullOrEmpty(field)) {
                mode = new ModelClassInfo(field.substring(0, field.length() - 1),content);
                mode.modifier = modifier;
                mode.mysqlField = mysqlField(field);
                cdxList.add(mode);
                content = "";
                field = "";
            }
        }
        return cdxList;
    }

    public static String mysqlField(String field) {
        StringBuilder sb = new StringBuilder();
        char[] chars = field.toCharArray();
        for (char ch : chars) {
            if (Character.isLowerCase(ch)) {
                sb.append(ch);
            } else if (Character.isUpperCase(ch)) {
                sb.append("_").append(Character.toLowerCase(ch));
            }
        }
        return sb.toString();
    }
}
