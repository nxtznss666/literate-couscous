package cn.kgc.tangcco.smbms.controller;

import cn.kgc.tangcco.smbms.pojo.Role;
import cn.kgc.tangcco.smbms.pojo.User;
import cn.kgc.tangcco.smbms.service.role.RoleService;
import cn.kgc.tangcco.smbms.service.user.UserService;
import cn.kgc.tangcco.smbms.tools.Constants;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tracing.dtrace.ModuleAttributes;
import net.sf.jsqlparser.expression.JsonExpression;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    private Logger logger=Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    //分页显示用户
    @RequestMapping("userList.html")
    public String getUserList(@RequestParam(value = "queryname",required = false)String userName, @RequestParam(value = "queryUserRole",required = false)Integer userRole, @RequestParam(value = "pageIndex",required = false) String pageIndex, Model model){
        int currentPageNo=1;
        if(null!=pageIndex){
            currentPageNo=Integer.valueOf(pageIndex);
        }

        try {
            PageHelper.startPage(currentPageNo, Constants.pageSize);
            List<User> userList=userService.getUserList(userName,userRole);
            PageInfo pageInfo=new PageInfo(userList);
            model.addAttribute("currentPageNo",currentPageNo);
            model.addAttribute(pageInfo);
            model.addAttribute(userList);
            List<Role> roleList=roleService.getRoleList();
            model.addAttribute("roleList",roleList);
            return "userlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:error";
        }
    }
    //根据id删除用户
    @RequestMapping("delUser")
    @ResponseBody
    public JSON delUser(Integer id){
        try {
            Map<String,String> resultMap=new HashMap<>();
            Integer result=userService.delUser(id);
            if(result>0){
                resultMap.put("result","true");
            }else{
                resultMap.put("result","false");
            }
            return (JSON) JSON.toJSON(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    //根据id查询用户
   /* @RequestMapping("viewUser")
    public String showUser(Integer id,Model model){
        try {
            User user=userService.getUserById(id);
            if(null!=user){
                model.addAttribute("user",user);
                return "userview";
            }else{
                return "redirect:/user/userList.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }*/
    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    @ResponseBody
    public JSON viewUser(@PathVariable Integer id){
        try {
            User user=userService.getUserById(id);

            return (JSON) JSON.toJSON(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //根据id去更新页面
    @RequestMapping("toupdate.html")
    public String toUpdate(@ModelAttribute("user")User user, Model model,Integer id){
        try {
            user=userService.getUserById(id);
            List<Role> roleList=roleService.getRoleList();
            if(null!=user){
                model.addAttribute(user);
                model.addAttribute(roleList);
                return "usermodify";
            }else{
                return "redirect:/user/userList.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }
    //做更新操作
    @RequestMapping("doupdateUser.html")
    public String doUpdate(User user){
        try {
            Integer result=userService.updateUser(user);
            if(result>0){
                return "redirect:/user/userList.html";
            }else{
                return "usermodify";
                /*"redirect:/user/toupdate.html?id="+user.getId();*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }

    //获取role集合
    @RequestMapping(value = "roleList",method = RequestMethod.POST)
    @ResponseBody
    public JSON getRoleList(){
        try {
            List<Role> roleList=roleService.getRoleList();
            return (JSON) JSON.toJSON(roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    //去增加页面
    @RequestMapping(value = "toAddUser.html",method = RequestMethod.GET)
    public String toAddUser(@ModelAttribute("user") User user,Model model){
        try {
            List<Role> roleList=roleService.getRoleList();
            model.addAttribute(roleList);
            return "useradd";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }

    //做增加操作
    @RequestMapping(value ="doAddUser.html",method = RequestMethod.POST)
    public String saveUser(User user, HttpSession session, HttpServletRequest request, @RequestParam(value = "attachs",required =false)MultipartFile[] attachs){
        System.out.println(user.getUserName());
        System.out.println(user.getAddress());
        // 如果你有文件上传
        // 第一件事:将文件上传到服务器中的指定路径
        //判断文件大小
        //判断文件的后缀
        //给上传的文件重新命名(防止重名)
        // 第二件事:给user对象的idPicPath属性赋值
        String errorInfo="";//作为错误信息的键
        if(null!=attachs){
            for (int i=0;i<attachs.length;i++){
                MultipartFile attach=attachs[i];
                if(null!=attach){//attach不为null说明有文件上传
                    if(i==0){
                        errorInfo="uploadFileError";
                    }else if(i==1){
                        errorInfo="uploadWpError";
                    }
                    //设置文件上传的路径
                    String path=request.getSession().getServletContext().getRealPath("statics"+ File.separator+"uploadfiles");
                    logger.info("文件上传路径:"+path);
                    //获得原始的文件名称
                    String oldFileName=attach.getOriginalFilename();
                    logger.info("上传的文件名称"+oldFileName);
                    System.out.println("上传的文件名称"+oldFileName);
                    //获得文件名的后缀
                    String suffix= FilenameUtils.getExtension(oldFileName);
                    logger.info("文件后缀"+suffix);
                    System.out.println("文件后缀"+suffix);
                    //限制文件上传的大小
                    int fileSize=500000;
                    System.out.println("上传文件大小为:"+attach.getSize());
                    if(attach.getSize()>fileSize){//判断上传文件的大小
                        request.setAttribute(errorInfo,"*文件大小不能超过500KB");
                        return "useradd";
                    }else if(suffix.equalsIgnoreCase("jpg")
                            ||suffix.equalsIgnoreCase("png")
                            ||suffix.equalsIgnoreCase("jpeg")
                            ||suffix.equalsIgnoreCase("pneg")
                    ){//后缀+大小均符合上传要求
                        //给上传的文件重命名
                        String fileName=System.currentTimeMillis()+ RandomUtils.nextInt(1000000)+attach.getName()+"_Personal.jpg";
                        logger.info("新的文件名为"+fileName+"\t\t");
                        //创建file对象
                        File targetFile=new File(path,fileName);
                        //如果上传文件路径不存在
                        if(!targetFile.exists()){
                            targetFile.mkdirs();
                        }
                        try {
                            //上传文件
                            attach.transferTo(targetFile);
                            if (i==0){//i=0说明是证件照
                                user.setIdPicPath(path+File.separator+fileName);
                            }else if(i==1){//i=1说明是工作照
                                user.setWorkPicPath(path+File.separator+fileName);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            request.setAttribute(errorInfo,"*上传失败");
                            return "useradd";
                        }
                    }else{//上传图品的格式不符合要求
                        request.setAttribute(errorInfo,"*上传图片格式不正确");
                        return "useradd";
                    }
                }
            }
        }
        user.setCreatedBy(((User)session.getAttribute("userSession")).getId());
        user.setCreationDate(new Date());

        Integer result=0;
        try {
            result = userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result>0) {
            /*重定向*/
            return "redirect:/user/userList.html";
        } else {
            return "useradd";
        }
    }
    @RequestMapping("modifyPassword")
    @ResponseBody
    public JSON modifyPassoword(String pwd,HttpSession session){
        User user= (User) session.getAttribute(Constants.USER_SESSION);
        Map<String,String> resultMap=new HashMap<>();
        if(user.getUserPassword().equals(pwd)){
            resultMap.put("result","true");
        }else{
            resultMap.put("result","false");
        }
        return (JSON) JSON.toJSON(resultMap);
    }
}
