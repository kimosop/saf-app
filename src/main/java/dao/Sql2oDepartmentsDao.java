package dao;

import org.sql2o.Sql2o;

public class Sql2oDepartmentsDao implements DepartmentsDao { //don't forget to shake hands with your interface!
    private final Sql2o sql2o;
    public Sql2oDepartmentsDao(Sql2o sql2o){ this.sql2o = sql2o; }
}
