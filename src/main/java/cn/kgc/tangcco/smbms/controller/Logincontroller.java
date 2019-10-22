package cn.kgc.tangcco.smbms.controller;

import cn.kgc.tangcco.smbms.dao.user.UserMapper;
import cn.kgc.tangcco.smbms.pojo.User;
import cn.kgc.tangcco.smbms.service.user.UserService;
import cn.kgc.tangcco.smbms.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
class LoginController {
    @Resource
    private UserService userService;
    //登陆
    @RequestMapping(value = "login.html",method = RequestMethod.POST)
    public String login(@RequestParam("userCode")String userCode, @RequestParam("userPassword")String userPassword, HttpSession session, HttpServletRequest request){
        try {
            User user=userService.getUser(userCode,userPassword);
            if(null!=user){
                session.setAttribute(Constants.USER_SESSION,user);
                return "frame";
            }else{
                return "../../login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:error";
        }

    }
    //退出
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute(Constants.USER_SESSION);
        return "login";
    }




    //错误
    @RequestMapping("error")
    public String error(){
        return "error";
    }

}
