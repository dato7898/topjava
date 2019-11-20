package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController { ;

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("")
    public String getBetween(Model model, @RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String startTime,
                             @RequestParam(required = false) String endDate,
                             @RequestParam(required = false) String endTime) {
        LocalDate sd = parseLocalDate(startDate);
        LocalDate ed = parseLocalDate(endDate);
        LocalTime st = parseLocalTime(startTime);
        LocalTime et = parseLocalTime(endTime);
        model.addAttribute("meals", super.getBetween(sd, st, ed, et));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam String id) {
        super.delete(Integer.parseInt(id));
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam String id) {
        Meal meal = super.get(Integer.parseInt(id));
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @PostMapping("")
    public String edit(HttpServletRequest request, @RequestParam String id, @RequestParam String dateTime,
                       @RequestParam String description, @RequestParam String calories) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        if (StringUtils.isEmpty(id)) {
            super.create(meal);
        } else {
            super.update(meal, Integer.parseInt(id));
        }
        return "redirect:/meals";
    }
}
