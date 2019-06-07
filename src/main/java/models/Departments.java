package models;

import java.util.Objects;

public class Departments {
    private String department_name;
    private String hod;
    private int id;

    public String getHod() {
        return hod;
    }

    public void setHod(String hod) {
        this.hod = hod;
    }


    public Departments(String department_name) {
        this.department_name = department_name;
    }

    public String getName() {
        return department_name;
    }

    public int getId() {
        return id;
    }

    public void setName(String department_name) {
        this.department_name = department_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departments)) return false;
        Departments department = (Departments) o;
        return id == department.id &&
                Objects.equals(department_name, department.department_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department_name, id);
    }

}
