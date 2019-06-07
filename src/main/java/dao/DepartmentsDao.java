package dao;

import java.util.List;

public interface DepartmentsDao {

    //create
    void add(Departments departments);
    void addDepartmentsToNews(Departments departments, News news);

    //read
    List<Departments> getAll();
    List<News> getAllNewsForADepartments(int id);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}
