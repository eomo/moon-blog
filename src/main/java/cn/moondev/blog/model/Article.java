package cn.moondev.blog.model;

import cn.moondev.framework.provider.random.RandomStringUtils;
import com.google.common.base.Strings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Article {

    public String id;

    /**
     * 文章标题
     */
    public String title;

    /**
     * 文章图片
     */
    public String image = "";
    // 首页图片格式
    public String mformat;
    // 归档页图片格式
    public String aformat;

    /**
     * 文章内容
     */
    public String content;

    /**
     * 文章摘要
     */
    public String summary = "";

    /**
     * 文章作者，暂未使用
     */
    public String author = "";

    /**
     * 创建时间
     */
    public LocalDateTime createdTime;

    /**
     * 更新时间
     */
    public LocalDateTime updatedTime;

    /**
     * 发布时间
     */
    public LocalDateTime publishTime;
    public String publishTimeDesc;

    /**
     * 文章类型：0草稿，1已发布
     */
    public int status;

    /**
     * 所属专题
     */
    public String topicId;
    public String topicName;

    /**
     * 所属分类
     */
    public String categoryId;
    public String categoryCode;
    public String categoryName;

    /**
     * 浏览量
     */
    public long viewCount;

    /**
     * 评论数
     */
    public long commentCount;

    /**
     * 置顶标志：1表示置顶，0表示未置顶
     */
    public int stick;

    /**
     * 页面标识，比如：ABOUTME，普通文章属性为空
     */
    public String badge;

    public String month;

    public String day;

    public String year;

    public String getYear() {
        if (Objects.nonNull(publishTime)) {
            return String.valueOf(publishTime.getYear());
        }
        return "2019";
    }

    public Article() {
        this.title = "";
        this.content = "";
    }

    public static String genId() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + RandomStringUtils.randomNumeric(2);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public String getPublishTime() {
        return publishTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    public int getStatus() {
        return status;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public String getBadge() {
        return badge;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public long getViewCount() {
        return viewCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public int getStick() {
        return stick;
    }

    public String getPublishTimeDesc() {
        return publishTimeDesc;
    }

    public String getMformat() {
        return mformat;
    }

    public String getAformat() {
        return aformat;
    }

    public String getMonth() {
        int month = publishTime.getMonthValue();
        if (month < 10) {
            return "0" + month;
        }
        return String.valueOf(month);
    }

    public String getDay() {
        int day = publishTime.getDayOfMonth();
        if (day < 10) {
            return "0" + day;
        }
        return String.valueOf(day);
    }
}
