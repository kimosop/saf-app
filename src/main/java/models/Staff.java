package models;

import java.util.Objects;

public class Staff {

    private String firstname;
    private String lastname;
    private String ekno;
    private String jobdescription;
    private int deptid;
    private int id;

    public Staff(String firstname, String lastname, String ekno, String jobdescription) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.ekno = ekno;
        this.jobdescription = jobdescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEkno() {
        return ekno;
    }

    public void setEkno(String ekno) {
        this.ekno = ekno;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        Staff staff = (Staff) o;
        return getFirstname().equals(staff.getFirstname()) &&
                getLastname().equals(staff.getLastname()) &&
                getEkno().equals(staff.getEkno()) &&
                getJobdescription().equals(staff.getJobdescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getEkno(), getJobdescription());
    }
}
