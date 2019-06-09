package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class News {

    private int id;
    private String newsitems;
    private int deptid;
    private LocalDateTime createdAt;

    public News(String newsitems, int deptid) {
        this.newsitems = newsitems;
        this.deptid = deptid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsitems() {
        return newsitems;
    }

    public void setNewsitems(String newsitems) {
        this.newsitems = newsitems;
    }

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return getId() == news.getId() &&
                getDeptid() == news.getDeptid() &&
                getNewsitems().equals(news.getNewsitems()) &&
                getCreatedAt().equals(news.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNewsitems(), getDeptid(), getCreatedAt());
    }
}
