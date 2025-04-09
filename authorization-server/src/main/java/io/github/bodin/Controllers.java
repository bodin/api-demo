package io.github.bodin;

import io.github.bodin.domain.LocalClientRepository;
import io.github.bodin.domain.NewClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Component
public class Controllers {

    @Controller
    public static class SiteController {

        @Autowired
        private LocalClientRepository clients;

        @GetMapping("/site/docs")
        public String docs() {
            return "docs";
        }

        @GetMapping("/site/index")
        public String index(HttpServletRequest request, Model m) {
            NewClient nc = (NewClient)request.getSession().getAttribute("new_client");
            request.getSession().removeAttribute("new_client");

            m.addAttribute("new_client", nc);
            m.addAttribute("clients", clients.getAll());

            return "index";
        }

        @GetMapping("/site/new")
        public String new_client(Model m) {
            m.addAttribute("clients", clients.getAll());
            return "clients/partials/add";
        }

        @PostMapping("/site/add")
        public String add_client(
                @RequestParam("name") String name,
                @RequestParam("expiration") String expiration,
                @RequestParam("subscription") String subscription,
                @RequestParam(name="scope", required = false) String [] scopes,
                HttpServletRequest request
        ) {
            int days = switch(expiration){
                case "m" -> 30;
                case "y" -> 365;
                default -> 1;
            };

            NewClient nc = this.clients.add(name,
                    OffsetDateTime.now().plusDays(days),
                    subscription, Arrays.asList(scopes == null ? new String [0] : scopes));

            request.getSession().setAttribute("new_client", nc);

            return "redirect:/site/index";
        }

        @PostMapping("/site/delete")
        public String delete(@RequestParam("id") String id) {
            this.clients.delete(id);
            return "redirect:/site/delete/success";
        }

        @GetMapping("/site/delete/success")
        public String delete_success(Model m) {
            return "clients/partials/deleted";
        }

    }
}
