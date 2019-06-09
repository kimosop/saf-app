package dao;

import models.News;

import java.sql.Timestamp;
import java.util.List;

public interface NewsDao {
    //LIST
    List<News> getAll();

    //CREATE

    void add(News news); //****

    //READ
    News findById(int id);
    void getAllNewssByDepartment(int departmentId);

    //UPDATE
    //deptid | newsitems | createdate
    void update(int deptid, String newsitems, Timestamp createdate);

    //DELETE
    void deleteById(int id);
    void clearAllNews();
}