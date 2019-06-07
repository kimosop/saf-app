package dao;

import java.util.List;

public interface StaffDao {
    //create
    void add(Staff staff);
    void addStaffToNews(Staff staff, Departments departmentss);


    //read
    List<Staff> getAll();


    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}
