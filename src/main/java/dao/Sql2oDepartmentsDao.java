package dao;

import models.Departments;
import models.Staff;
import models.News;
import org.sql2o.Connection;
import org.sql2o.*;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentsDao {

    private final Sql2o sql2o;
    public Sql2oDepartmentsDao(Sql2o sql2o){
        this.sql2o = sql2o;


    }

    @Override
    //public void add(Category category) { ****
    public void add(Departments departments) {

//        String sql = "INSERT INTO categories (name) VALUES (:name)"; ****
        String sql = "INSERT INTO departments (deptname,description) VALUES (:deptname, :description)";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .bind(departments)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Departments> getAll() {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Departments.class);
        }
    }

    @Override
    public Departments findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Departments.class);
        }
    }

    @Override
    public void update(int id, String newName){
        String sql = "UPDATE departments SET deptname = :deptname WHERE id=:id";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("deptname", newName)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
//        String sql = "DELETE from categories WHERE id=:id"; //raw sql ****
        String sql = "DELETE from departments WHERE id=:id"; //raw sql
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllDepartments() {
//        String sql = "DELETE from categories"; //raw sql ****
        String sql = "DELETE from departments"; //raw sql
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<News> getAllNewsByDepartments(int departmentId) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM news WHERE deptid = :departmentId")
                    .addParameter("deptid", departmentId)
                    .executeAndFetch(News.class);
        }
    }

}
