package entities;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Holiday {
    private Integer id;
    private Date startDate;
    private Date endDate;
    private Employee employee;

    private Holiday() {
        //Used by Hibernate
    }

    public Holiday(Employee employee, Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public static List<Holiday> fillEmployeeHolidays(Employee employee) {
        List<Holiday> employeeHolidays = new ArrayList<>();
        GregorianCalendar hireDate = new GregorianCalendar();
        hireDate.setTime(employee.getHireDate());
        GregorianCalendar holidayBorder = (GregorianCalendar) Calendar.getInstance();
        holidayBorder.set(Calendar.DAY_OF_MONTH, 15);
        holidayBorder.set(Calendar.MONTH, Calendar.DECEMBER);
        holidayBorder.add(Calendar.YEAR, 1);
        Random random = new Random();
        while (true) {
            GregorianCalendar holidayStartDate = (GregorianCalendar) hireDate.clone();
            hireDate.add(Calendar.MONTH, 6);
            if (hireDate.after(holidayBorder)) {
                break;
            } else {
                int minHoliday = 14;
                int maxHoliday = 21;
                holidayStartDate.add(Calendar.DAY_OF_MONTH, random.nextInt(30));
                holidayStartDate.add(Calendar.MONTH, random.nextInt(6));
                GregorianCalendar holidayEndDate = (GregorianCalendar) holidayStartDate.clone();
                int holidayLength = minHoliday + random.nextInt(maxHoliday - minHoliday);
                holidayEndDate.add(Calendar.DAY_OF_MONTH, holidayLength);
                Holiday holiday = new Holiday(employee,
                        calendarToSqlDate(holidayStartDate),
                        calendarToSqlDate(holidayEndDate));
                employeeHolidays.add(holiday);
            }
        }
        employee.setHolidays(employeeHolidays);
        return employeeHolidays;
    }

    private static Date calendarToSqlDate(Calendar calendar) {
        return new java.sql.Date(calendar.getTime().getTime());
    }

    public static java.util.Date[] crossHoliday(Holiday firstHoliday, Holiday secondHoliday) {
        java.util.Date crossDate[] = new java.util.Date[2];
        if (firstHoliday.getStartDate().before(secondHoliday.getStartDate()))
            crossDate[0] = secondHoliday.getStartDate();
        else crossDate[0] = firstHoliday.getStartDate();

        if (firstHoliday.getEndDate().after(secondHoliday.getEndDate()))
            crossDate[1] = secondHoliday.getEndDate();
        else crossDate[1] = firstHoliday.getEndDate();

        return crossDate;
    }
}

