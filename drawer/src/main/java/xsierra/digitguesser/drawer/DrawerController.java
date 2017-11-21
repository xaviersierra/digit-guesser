package xsierra.digitguesser.drawer;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DrawerController {

    @RequestMapping("/")
    public String getIndex() {
        return "index.html";
    }

}
