import entities.Employee;
import entities.Holiday;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.util.*;

/**
 * Created by Danya on 26.10.2015.
 */
public class Loader {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        //Запросы реализованные с помощью HQL
        String salaryHead = "SELECT d.depHead FROM Department d WHERE d.depHead.salary < 115000";
        String monthHead = "SELECT d.depHead FROM Department d WHERE month(d.depHead.hireDate) < 3";
        String headJobPlace = "SELECT d.depHead FROM Department d WHERE d.depHead.department.id != d.id";

        setUp();

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
//--------------------------------------------------------------------------------------------------------------------
//          Урок 3. ORM: объекты и записи
            List<Employee> salaryHeadEmp = (List<Employee>) session.createQuery(salaryHead).list();
            List<Employee> monthHeadEmp = (List<Employee>) session.createQuery(monthHead).list();
            List<Employee> headJobPlaceEmp = (List<Employee>) session.createQuery(headJobPlace).list();

            System.out.println("Зарплата менее 115000:");
            for (Employee employee : salaryHeadEmp){
                System.out.println(employee.getName());
            }
            System.out.println();


            System.out.println("Вышли на работу до марта:");
            for (Employee employee : monthHeadEmp){
                System.out.println(employee.getName());
            }
            System.out.println();


            System.out.println("Работают в одном отделе, а руководят другим:");
            for (Employee employee : headJobPlaceEmp){
                System.out.println(employee.getName());
            }
            System.out.println();


//--------------------------------------------------------------------------------------------------------------------
            //Урок 4. Добавление, удаление и обновление
            Long recordCount = (Long) session.createQuery("SELECT count(h.id) FROM Holiday h").uniqueResult();
            if (Long.valueOf(0).equals(recordCount)) {
                List<Employee> employees = (List<Employee>) session.createQuery("FROM Employee e").list();
                for (Employee employee : employees) {
                    List<Holiday> holidays = Holiday.fillEmployeeHolidays(employee);
                    for (Holiday holiday : holidays) {
                        session.save(holiday);
                    }
                }
            }

            String crossQuery =
                    "SELECT h1, h2 " +
                            "FROM Holiday h1, Holiday h2 " +
                            "WHERE h1.startDate <= h2.endDate AND h2.startDate <= h1.endDate AND h1.id != h2.id AND h1.id < h2.id " +
                            "AND h1.employee.department = h2.employee.department " +
                            "ORDER BY h1.employee.department.id, h1.id";
            List<Object[]> crossing = session.createQuery(crossQuery).list();
            if (!crossing.isEmpty()) {
                int depId = -1;
                System.out.println("Пересечения отпусков:");
                for (Object[] item : crossing) {
                    Holiday firstHoliday = (Holiday) item[0];
                    Holiday secondHoliday = (Holiday) item[1];
                    if (firstHoliday.getEmployee().getDepartment().getId() != depId) {
                        depId = firstHoliday.getEmployee().getDepartment().getId();
                        System.out.println(firstHoliday.getEmployee().getDepartment().getName() + ":");
                    }
                    Date crossDate[] = Holiday.crossHoliday(firstHoliday, secondHoliday);
                    System.out.print("с " + crossDate[0] + " по " + crossDate[1] + " - ");
                    System.out.println(firstHoliday.getEmployee().getName() + " и " + secondHoliday.getEmployee().getName());
                }
            } else {
                System.out.println("Пересечений не обнаружено.");
            }

            session.getTransaction().commit();
        } catch (Throwable throwable) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }


        //==================================================================
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    //=====================================================================

    private static void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(new File("src/config/hibernate.cfg.xml")) // configures settings from hibernate.config.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }
}
