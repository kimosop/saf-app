import static spark.Spark.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DepartmentsDao;
import dao.Sql2oStaffDao;
import dao.Sql2oDepartmentsDao;
import jdk.vm.ci.code.site.Site;
import models.db;
import models.Staff;
import models.Departments;

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
//        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/site_maintenance", null, null);
        Sql2o sql2o = db.sql2o;
        Sql2oDao StaffDao = new Sql2oStaffDao(sql2o);
        Sql2oDepartmentsDao siteDao = new Sql2oDepartmentsDao(sql2o);

        get("/", (request, response) -> {
                    Map<String, Object> model = new HashMap<>();
                    return new ModelAndView(model, "index.hbs");
                }, new HandlebarsTemplateEngine()
        );

        //get: delete all
        get("//delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            StaffDao.clearAll(); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete an individual engineer
        get("//:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToDelete = Integer.parseInt(req.params("id"));
            StaffDao.deleteById(idOfStaffToDelete); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: show all
        get("/index", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Staff> all = StaffDao.getAll();
            model.put("", all);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show new engineer form
        get("//new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Staff>  = StaffDao.getAll();
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());


        //engineer: process new engineer form
        post("/", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String department = req.queryParams("department");

            String last_name = req.queryParams("last_name");
            String first_name = req.queryParams("first_name");
            Staff newStaff = new Staff(department, first_name, name, last_name);
            StaffDao.add(newStaff);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual engineer
        get("//:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Staff foundStaff = StaffDao.findById(idOfStaffToFind); //use it to find task
            model.put("staff", foundStaff); //add it to model for template to display
            return new ModelAndView(model, "staff-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());


        //get: show a form to update an Engineer
        get("//:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editStaff", true);
            int idOfStaffToEdit = Integer.parseInt(req.params("id"));
            Staff editStaff = staffDao.findById(idOfStaffToEdit);
            model.put("editStaff", editStaff);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());


        //engineer: process a form to update an engineer
        post("//:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToEdit = Integer.parseInt(req.params("id"));
            String newStaff = req.queryParams("name");
            String department= req.queryParams("department");
            StaffDao.update(idOfStaffToEdit, newStaff, department);
            res.redirect("index.hbs");
            return null;
        }, new HandlebarsTemplateEngine());



        // site Details


        //get: delete all departments
        get("/departments/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            DepartmentsDao.clearAllDepartments(); //change
            res.redirect("site.hbs");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete a site
        get("/departments/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToDelete = Integer.parseInt(req.params("id"));
            DepartmentsDao.deleteById(idOfDepartmentToDelete); //change
            res.redirect("site.hbs");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: show all sites
        get("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> sites = DepartmentsDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "Departments.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new Site form
        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Staff>  = StaffDao.getAll();
            model.put("", );
            return new ModelAndView(model, "departments-form.hbs");
        }, new HandlebarsTemplateEngine());


        //site: process new site form
        post("/departments", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Staff> all = StaffDao.getAll();
            model.put("", all);
            String department_name = req.queryParams("department_name");
            String hod = req.queryParams("hod");
            int staffId = Integer.parseInt(req.queryParams("staffId"));
            Department newDepartment = new Department(department_name, hod, staffId);
            DepartmentDao.add(newDepartment);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        get("//:staff_id/deparments/:department_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToFind = Integer.parseInt(req.params("department_id"));
            Site foundDepartment = DepartmentDao.findById(idOfDepartmentToFind);
            int idOfDepartmentToFind = Integer.parseInt(req.params("staff_id"));
            Staff foundStaff = StaffDao.findById(idOfStaffToFind);
            model.put("department", foundDepartment);
            model.put("staff", foundStaff);
            model.put("", staffDao.getAll());
            return new ModelAndView(model, "staff-detail.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show an individual site
        get("/departments/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Department foundDepartment = DepartmentDao.findById(idOfDepartmentToFind); //use it to find task
            model.put("departments", foundDepartment); //add it to model for template to display
            return new ModelAndView(model, "department-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());


        get("/departments/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Site foundDepartment = DepartmentsDao.findById(idOfDepartmentToFind); //use it to find task
            model.put("department", foundDepartment); //add it to model for template to display
            return new ModelAndView(model, "staff-detail"); //individual task page.
        }, new HandlebarsTemplateEngine());




        //get: show a form to update an Engineer
        get("/departments/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDeparmentToEdit = Integer.parseInt(req.params("id"));
            Department editDepartment = DepartmentsDao.findById(idOfDepartmentToEdit);
            model.put("editDepartment", editDepartment);
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());


        //site: process a form to update a site
        post("/departments/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            String newDepartment = req.queryParams("department_name");
            String hod= req.queryParams("hod");
            int idOfDepartmentToEdit = Integer.parseInt(req.params("id"));
            DepartmentDao.update(idOfDepartmentToEdit, newDepartment, hod);
            res.redirect("/department");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all  and all sites
        get("//delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            StaffDao.clearAll();
            DepartmentsDao.clearAllDepartments();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show a Department and staff
        get("//:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToFind = Integer.parseInt(req.params("id")); //new
            Department foundDepartment = DepartmentDao.findById(idOfDepartmentToFind);
            model.put("department", foundDepartment);
            List<Staff> allDepartmentsByStaff = DepartmentsDao.getAllStaffByDepartments(idOfDepartmentToFind);
            model.put("staff", allStaffByDepartment);
            model.put("", DepartmentsDao.getAll());
            return new ModelAndView(model, "department-detail.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show all sites under all  and show all
        get("/index", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Staff> all = StaffDao.getAll();
            model.put("", all);
            List<Department> departments = DepartmentsDao.getAll();
            model.put("sites", sites);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());




    }
}