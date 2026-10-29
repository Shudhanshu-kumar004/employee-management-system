package myemsproject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularForwardController {

    @RequestMapping(value = {
            "/",
            "/viewEmployee/1",
            "/employees",
            "/dashboard",
            "/updateEmployee/{id}",
            "/addEmployee"
    })
    public String forwardAngularRoutes() {
        return "forward:/index.html";
    }
}