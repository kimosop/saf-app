package models;

import jdk.vm.ci.code.site.Site;
import org.sql2o.*;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;


public class Staff {
    private String department;
    private String first_name;
    private String last_name;
    private int id;
    private ArrayList<Departments> departments;


    public Staff(String staff, String first_name, String name, String last_name) {
        this.department = name;
        this.first_name = first_name;
        this.last_name = last_name;
    }


    public void setName(String name) {
        this.department = name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return department;
    }


    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return getId() == staff.getId() &&
                Objects.equals(getDepartment(), staff.getDepartment()) &&
                Objects.equals(getFirst_name(), staff.getFirst_name()) &&
                Objects.equals(getLast_name(), staff.getLast_name()) &&
                Objects.equals(departments, staff.departments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId(), getFirst_name(), getLast_name(),getDepartment(), departments);
    }
}
