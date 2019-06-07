package models;

import jdk.vm.ci.code.site.Site;
import org.sql2o.*;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;


public class Staff {
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Staff(String department, String first_name, String last_name) {
        this.department = department;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    private String department;
    private String first_name;
    private String last_name;
    private int id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        Staff staff = (Staff) o;
        return last_name == staff.last_name &&
                id == staff.id &&
                Objects.equals(last_name, staff.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name,last_name, department, id);
    }
}
