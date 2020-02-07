package entities;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Danya on 26.10.2015.
 */
public class Employee {
    private Integer id;
    private Date hireDate;
    private Integer salary;
    private String name;
    private Department department;
    private Department headingDep;
    private HashSet<Holiday> holidays = new HashSet<>(0);

    public Employee() {
        //Used by Hibernate
    }

    public HashSet<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(Collection<Holiday> holidays) {
        //this.holidays = holidays;
        this.holidays.addAll(holidays);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getHeadingDep() {
        return headingDep;
    }

    public void setHeadingDep(Department headingDep) {
        this.headingDep = headingDep;
    }

}
