package com.meijm.demo.controller;

import com.meijm.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class IndexController{

    @GetMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @PostMapping("/login")
    public ModelAndView login(Model model,String username, HttpServletRequest request){
        User user = new User(username);
        request.getSession().setAttribute("user",user);
        Set<User> users = (Set<User>)request.getServletContext().getAttribute("users");
        if(users == null){
            users = new HashSet<>();
        }

        if(users.contains(user)){
            model.addAttribute("msg","用户名重复");
            return new ModelAndView("index");
        }else{
            users.add(user);
            request.getServletContext().setAttribute("users",users);
            return new ModelAndView("redirect:/chat/toChat");
        }
    }
}
