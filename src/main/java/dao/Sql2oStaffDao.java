package dao;

import models.Staff;
import org.sql2o.*;
import java.util.List;

public class Sql2oStaffDao implements StaffDao { //implementing our interface

    private final Sql2o sql2o;

    public Sql2oStaffDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Staff staff) {
        String sql = "INSERT INTO staff (description, departmentId) VALUES (:description, :categoryId)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(staff)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            staff.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Staff> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff") //raw sql
                    .executeAndFetch(Staff.class); //fetch a list
        }
    }

    @Override
    public Staff findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Staff.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String newDescription, int newDepartmentId){
        String sql = "UPDATE staff SET (description, departmentId) = (:description, :departmentId) WHERE id=:id";   //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("description", newDescription)
                    .addParameter("departmentId", newDepartmentId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from staff WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllStaff() {
        String sql = "DELETE from staff";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}