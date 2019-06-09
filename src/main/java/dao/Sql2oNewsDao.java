package dao;

import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;

public class Sql2oNewsDao implements NewsDao{

    private final Sql2o sql2o;

    public Sql2oNewsDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    //deptid | newsitems | createdate
    public void add(News news) {
        String sql = "INSERT INTO news (deptid, newsitems) VALUES (:deptid, :newsitems)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            con.createQuery(sql) //make a new variable
                    .bind(news)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<News> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff") //raw sql
                    .executeAndFetch(News.class); //fetch a list
        }
    }


    @Override
    public News findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(News.class); //fetch an individual item
        }
    }

    @Override
    //deptid | newsitems | createdate
    public void update(int deptid, String newsitems, Timestamp createdate){
        String sql = "UPDATE Staff SET (deptid, newsitems, createdate) = (:deptid, :newsitems, :createdate) WHERE id=:id"; //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("newdeptid", deptid)
                    .addParameter("newnewsitems", newsitems)
                    .addParameter("sectionId", createdate)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from Staff WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllNews() {
        String sql = "DELETE from Staff";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void getAllNewssByDepartment(int departmentId){

    }
}
