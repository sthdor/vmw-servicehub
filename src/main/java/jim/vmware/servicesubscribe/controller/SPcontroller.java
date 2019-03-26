package jim.vmware.servicesubscribe.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jim.vmware.servicesubscribe.model.Servize;
import jim.vmware.servicesubscribe.model.Subscription;
import jim.vmware.servicesubscribe.model.User;
import jim.vmware.servicesubscribe.service.SPserviceImpl;

@Controller
@RequestMapping(path="/servicehub") 
public class SPcontroller {
    @Autowired
    private SPserviceImpl spService;
    
    @GetMapping(path = {"", "/service"})
    public String getSubscribedServiceByUser(@RequestParam(required=false) String username, 
            Model model) {
        List<Servize> services = null;
        if(StringUtils.isBlank(username)) {
            model.addAttribute("username", "All services");
            services = spService.getAllServices().stream().
                    collect(Collectors.toList());
        } else {
            model.addAttribute("username", username+" subscribed services");
            services = spService.getUserSubscribedService(username).stream().
                    collect(Collectors.toList());
        }
        model.addAttribute("services", services);
        return "service";
    }
    
    @PostMapping(value = "/service")
    public String subscribeService(@RequestParam("username") String username,
                    @RequestParam("servicename") String servicename) {
            try {
                spService.userSubscribeService(username, servicename);
            } catch(Exception e) {
                System.out.println("Subscribe service error, u:"+username+", s:"+servicename);
            } 
            return "redirect:/servicehub/service?username="+username;
    }
    
    @PostMapping(value = "/service/delete")
    public String unsubscribeService(@RequestParam("username") String username,
                    @RequestParam("servicename") String servicename) {
            try {
                spService.userUnsubscribeService(username, servicename);
            } catch(Exception e) {
                System.out.println("Un-subscribe service error, u:"+username+", s:"+servicename);
            } 
            return "redirect:/servicehub/service?username="+username;
    }
    
    @GetMapping(path="/api/user/add") 
    public @ResponseBody String addUser (@RequestParam String name) {
        spService.addUser(name);
        return "user added";
    }
    
    @GetMapping(path="/api/service/add") 
    public @ResponseBody String addService (@RequestParam String name, @RequestParam String url) {
        spService.addService(name, url);
        return "service added";
    }
    
    @GetMapping(path="/api/subscription/add") 
    public @ResponseBody String subscribe (@RequestParam String username, 
            @RequestParam String servicename) {
        spService.userSubscribeService(username, servicename);
        return "subscribed";
    }
    
    @GetMapping(path="/api/user")
    public @ResponseBody List<User> getAllUsers() {
        return spService.getAllUsers();
    }
    
    @GetMapping(path="/api/service")
    public @ResponseBody List<Servize> getAllServices(@RequestParam(required = false) String username) {
        if(StringUtils.isBlank(username))
            return spService.getAllServices();
        else 
            return spService.getUserSubscribedService(username);
    }
    
    @GetMapping(path="/api/subscription")
    public @ResponseBody List<Subscription> getAllSubscriptions() {
        return spService.getAllSubscriptions();
    }

}
