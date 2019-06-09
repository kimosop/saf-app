
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.*;
import models.Departments;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;
import com.google.gson.Gson;

import dao.Sql2oDepartmentsDao;
import exceptions.ApiException;
import models.News;
import models.Staff;
//import org.sql2o.Sql2o;

public class App {
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        // This tells our app that if Heroku sets a port for us, we need to use that port.
        // Otherwise, if they do not, continue using port 4567.

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);


        staticFileLocation("/public");
        Sql2oStaffDao StaffDao = new Sql2oStaffDao(DB.sql2o);
        Sql2oDepartmentsDao DepartmentsDao = new Sql2oDepartmentsDao(DB.sql2o);
        Sql2oNewsDao newsDao = new Sql2oNewsDao(DB.sql2o);
        Gson gson = new Gson();



        //get: show all tasks in all department and show all sections
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> allDepartments = DepartmentsDao.getAll();
            model.put("departments", allDepartments);
            List<Staff> staffs = StaffDao.getAll();
            model.put("staffs", staffs);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("api/list-departments", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(DepartmentsDao.getAll());
        });

        get("api/list-staff", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(StaffDao.getAll());
        });

        post("api/add-department", "application/json", (req, res) -> {
            Departments newDept = gson.fromJson(req.body(), Departments.class);

            if(newDept.getDeptname() != null){
                throw new ApiException(801, String.format("Error, Department:\"%s\"  already exists!", newDept.getDeptname()));
            } else
                DepartmentsDao.add(newDept);
            res.status(201);
            return gson.toJson(newDept);
        });
        post("api/add-staff", "application/json", (req, res) -> {
            Staff newStaff = gson.fromJson(req.body(), Staff.class);
            if(newStaff.getEkno() != null){
                throw new ApiException(801,String.format("Error, Department:\"%s\"  already exists! ",newStaff.getEkno()));
            }else
                StaffDao.add(newStaff);
            res.status(201);
            return gson.toJson(newStaff);
        });

        post("api/add-news", "application/json", (req, res) -> {
            News newItem = gson.fromJson(req.body(), News.class);
            if(newItem.getNewsitems() != null){
                throw new ApiException(801,String.format("Error, News item:\"%s\"  already exists!",newItem.getNewsitems()));
            }else
                newsDao.add(newItem);
            res.status(201);
            return gson.toJson(newItem);
        });
        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = (ApiException)  exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


    }
}