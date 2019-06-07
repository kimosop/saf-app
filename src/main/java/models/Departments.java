package models;

import org.sql2o.*;

import java.util.List;

public class Departments{
    private String department_name;
    private String node_name;
    private String hod;

    private String staff;
    private int id;

    public Site(String node_name, String site_id, String site_name,  String technology, String status, int engineerId) {
        this.site_name = site_name;
        this.staff = staff;
        this.node_name = node_name;
        this.site_id = site_id;
        this.technology = technology;
        this.status = status;
        this.engineerId = engineerId;
        this.id = id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setSite_name(String site_name){
        this.site_name = site_name;
    }

    public void setStaff(String staff){
        this.staff = staff;
    }

    public void setNode_name(String node_name){
        this.node_name = node_name;
    }

    public void setSite_id(String site_id){
        this.site_id = site_id;
    }

    public void setTechnology(String technology){
        this.technology = technology;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setEngineerId(int engineerId){
        this.engineerId = engineerId;
    }


    public String getSite_name(){
        return site_name;
    }

    public String getStaff(){
        return staff;
    }

    public int getId(){
        return id;
    }

    public String getNode_name(){
        return node_name;
    }

    public String getSite_id(){
        return site_id;
    }

    public String getTechnology(){
        return technology;
    }

    public String getStatus(){
        return status;
    }

    public int getEngineerId(){
        return engineerId;
    }

    @Override
    public boolean equals(Object otherSite){
        if (!(otherSite instanceof Site)) {
            return false;
        } else {
            Site newSite = (Site) otherSite;
            return this.getSite_name().equals(newSite.getSite_name()) &&
                    this.getEngineerId() == newSite.getEngineerId();
        }
    }

}