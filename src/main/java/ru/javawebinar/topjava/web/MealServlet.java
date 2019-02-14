package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.DefaultMealService;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealService mealService = new DefaultMealService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = req.getParameter("action");
        if (action==null){
            action = "list";
        }
        switch (action){
            case "delete": {
                long mealId = Integer.parseInt(req.getParameter("mealId"));
                mealService.delete(mealId);
                resp.sendRedirect("meals");
                break;
            }
            case "update": {
                long mealId = Integer.parseInt(req.getParameter("mealId"));
                Meal meal = mealService.getMealById(mealId);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/mealForm.jsp").forward(req, resp);
                break;
            }
            case "list":
                req.setAttribute("meals", MealsUtil.getFilteredWithExcess(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String mealId = req.getParameter("id");
        String mealDateTime = req.getParameter("date");
        String mealDescription = req.getParameter("description");
        String mealCalories = req.getParameter("calories");

        Meal meal = new Meal(LocalDateTime.parse(mealDateTime), mealDescription, Integer.parseInt(mealCalories));

        if (mealId.equals("")) {
            mealService.add(meal);
        }
        else {
            meal.setId(Long.parseLong(mealId));
            mealService.update(Long.parseLong(mealId), meal);
        }
        req.setAttribute("meals", MealsUtil.getFilteredWithExcess(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
