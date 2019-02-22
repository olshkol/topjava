package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController restController;

    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        restController = appCtx.getBean(MealRestController.class);

    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Optional<String> actionFilter = Optional.ofNullable(request.getParameter("actionFilter"));
        Optional<String> cancelFilter = Optional.ofNullable(request.getParameter("cancelFilter"));
        if (actionFilter.isPresent()) {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            MealsUtil.startDate = startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate);
            MealsUtil.endDate = endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate);
            MealsUtil.startTime = startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
            MealsUtil.endTime = endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
        }
        else if (cancelFilter.isPresent()) {
            MealsUtil.startDate = LocalDate.MIN;
            MealsUtil.endDate = LocalDate.MAX;
            MealsUtil.startTime = LocalTime.MIN;
            MealsUtil.endTime = LocalTime.MAX;
        }
        else {
            String id = request.getParameter("id");
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (meal.isNew()) {
                log.info("Create {}", meal);
                restController.create(meal);
            } else {
                log.info("Update {}", meal);
                restController.update(meal, Integer.parseInt(id));
            }
            //response.sendRedirect("meals");
        }
        request.setAttribute("startDate", MealsUtil.startDate);
        request.setAttribute("endDate", MealsUtil.endDate);
        request.setAttribute("startTime", MealsUtil.startTime);
        request.setAttribute("endTime", MealsUtil.endTime);
        request.setAttribute("meals", restController.getAllFiltered());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                restController.delete(id);
               // response.sendRedirect("meals");
                request.setAttribute("startDate", MealsUtil.startDate);
                request.setAttribute("endDate", MealsUtil.endDate);
                request.setAttribute("startTime", MealsUtil.startTime);
                request.setAttribute("endTime", MealsUtil.endTime);
                request.setAttribute("meals", restController.getAllFiltered());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        restController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAllFiltered");
                request.setAttribute("meals", restController.getAllFiltered());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
