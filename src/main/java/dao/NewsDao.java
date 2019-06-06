package dao;

import java.util.List;

public interface NewsDao {

    //create
    void add(News news);
    void addNewsToDepartments(News news, Departments departments);

    //read
    List<News> getAll();
    List<Departments> getAllDepartmentsForANews(int id);

    //update
    //omit for now

    //delete
//    void deleteById(int id);
//    void clearAll();
}
