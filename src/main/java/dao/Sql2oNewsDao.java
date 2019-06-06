package dao;

import org.sql2o.Sql2o;

public class Sql2oNewsDao implements NewsDao { //don't forget to shake hands with your interface!
    private final Sql2o sql2o;
    public Sql2oNewsDao(Sql2o sql2o){ this.sql2o = sql2o; }

}
