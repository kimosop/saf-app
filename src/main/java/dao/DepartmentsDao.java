package dao;

import models.Departments;
import models.News;
import models.Staff;
//import models.Staff;
import java.util.List;

public interface DepartmentsDao {

    //LIST
    List<Departments> getAll();

    //CREATE

    void add (Departments departments); //****

    //READs
    Departments findById(int id);
    List<News> getAllNewsByDepartment(int departmentId);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllDepartments();

    List<News> getAllNewsByDepartments(int departmentId);
}

