
import static spark.Spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DB;
import dao.DepartmentsDao;
import dao.Sql2oStaffDao;
import dao.Sql2oDepartmentsDao;

import models.Departments;
import models.Staff;
import com.google.gson.Gson;

import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class App{
    public static void main(String[] args){
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);


        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
       //Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/saf-app",username:, null);
       //Sql2o sql2o = sql2o;
        Sql2oStaffDao StaffDao = new Sql2oStaffDao(sql2o);
        Sql2oDepartmentsDao DepartmentsDao = new Sql2oDepartmentsDao(sql2o);
        Gson gson = new Gson();
        //get: show all tasks in all categories and show all categories
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> allDepartments = DepartmentsDao.getAll();
            model.put("departments", allDepartments);
            List<Staff> tasks = StaffDao.getAll();
            model.put("tasks", tasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new department
        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> departments = DepartmentsDao.getAll(); //refresh list of links for navbar
            model.put("departments", departments);
            return new ModelAndView(model, "department-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new category
        post("/departments", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Departments newDepartment = new Departments(name);
            DepartmentsDao.add(newDepartment);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete all departments and all staff
        get("/departments/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            DepartmentsDao.clearAllDepartments();
            StaffDao.clearAllStaff();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all staff
        get("/staff/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            StaffDao.clearAllStaff();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific Department(and the staff it contains)
        get("/departments/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentsToFind = Integer.parseInt(req.params("id")); //new
            Departments foundDepartments = DepartmentsDao.findById(idOfDepartmentsToFind);
            model.put("departments", foundDepartments);
            List<Staff> allStaffByDepartments = DepartmentsDao.getAllStaffByDepartments(idOfDepartmentsToFind);
            model.put("staff", allStaffByDepartments);
            model.put("departments", DepartmentsDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "department-details.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a department
        get("/departments/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editDepartment", true);
            Departments departments = DepartmentsDao.findById(Integer.parseInt(req.params("id")));
            model.put("departments", departments);
            model.put("departments", DepartmentsDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a department
        post("/departments/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newDepartmentName");
            DepartmentsDao.update(idOfDepartmentToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual staff
        get("/departments/:department_id/staff/:staff_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToDelete = Integer.parseInt(req.params("staff_id"));
            StaffDao.deleteById(idOfStaffToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new staff form
        get("/staff/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> departments = DepartmentsDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new staff form
        post("/staff", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Departments> allDepartments = DepartmentsDao.getAll();
            model.put("departments", allDepartments);
            String first_name = req.queryParams("first_name");
            String last_name = req.queryParams("last_name");
            String department = req.queryParams("department");
            Staff newStaff = new Staff(first_name, last_name, department);        //See what we did with the hard coded categoryId?
            StaffDao.add(newStaff);
//            List<Task> tasksSoFar = taskDao.getAll();
//            for (Task taskItem: tasksSoFar
//                 ) {
//                System.out.println(taskItem);
//            }
//            System.out.println(tasksSoFar);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task that is nested in a category
        get("/departments/:department/staff/:staff_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToFind = Integer.parseInt(req.params("staff_id")); //pull id - must match route segment
            Staff foundStaff = StaffDao.findById(idOfStaffToFind); //use it to find task
            int idOfDepartmentToFind = Integer.parseInt(req.params("department"));
            Departments foundDepartment = DepartmentsDao.findById(idOfDepartmentToFind);
            model.put("departments", foundDepartment);
            model.put("staff", foundStaff); //add it to model for template to display
            model.put("departments", DepartmentsDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "staff-details.hbs"); //individual staff page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a staff
        get("/staff/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> allDepartments = DepartmentsDao.getAll();
            model.put("categories", allDepartments);
            Staff staff = StaffDao.findById(Integer.parseInt(req.params("id")));
            model.put("staff", staff);
            model.put("editStaff", true);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a staff
        post("/staff/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int staffToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("last_name");
            int newDepartmentId = Integer.parseInt(req.queryParams("departmentId"));
            StaffDao.update(staffToEditId, newContent, newDepartmentId);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        get("api/list-staff", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(StaffDao.getAll());
        });

        }
    }