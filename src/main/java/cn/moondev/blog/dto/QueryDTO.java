package cn.moondev.blog.dto;

public class QueryDTO {

    public int pager = 1;

    public int size = 10;

    public String title;

    public String author;

    public String person;

    public String year;

    public String categoryId;

    public String topicId;

    public long offset;

    public QueryDTO() {

    }

    public QueryDTO(String topicId, String categoryId) {
        this.topicId = topicId;
        this.categoryId = categoryId;
    }
}
