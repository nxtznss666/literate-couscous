package cn.kgc.tangcco.smbms.controller;

import cn.kgc.tangcco.smbms.pojo.Provider;
import cn.kgc.tangcco.smbms.pojo.User;
import cn.kgc.tangcco.smbms.service.provider.ProviderService;
import cn.kgc.tangcco.smbms.tools.Constants;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.net.Inet4Address;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Resource
    private ProviderService providerService;
    //显示所有供应商
    @RequestMapping("showAllProvider")
    @ResponseBody
    public JSON showAllProvider(){
        List<Provider> providerList=providerService.providerList();
        return (JSON) JSON.toJSON(providerList);
    }
    //分页显示所有供应商(可根据编号,名字查询)
    @RequestMapping("showProvider.html")
    public String showProvider(@RequestParam(value = "queryProName",required = false)String proName, @RequestParam(value = "queryProCode",required = false) Integer proCode, Model model,@RequestParam(value = "pageIndex",required = false)String pageIndex){
        try {
            int currentPageNo=0;
            if(pageIndex!=null){
                currentPageNo=Integer.valueOf(pageIndex);
            }else{
                currentPageNo=1;
            }
            PageHelper.startPage(currentPageNo, Constants.pageSize);
            List<Provider> providerList=providerService.findProviderList(proCode,proName);
            PageInfo<Provider> pageInfo=new PageInfo<>(providerList);
            model.addAttribute(providerList);
            model.addAttribute(pageInfo);
            model.addAttribute("pageIndex",currentPageNo);
            return "providerlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }
    //根据id查询供应商
    @RequestMapping(value = "getProvider.html",method = RequestMethod.GET)
    public String provider(Integer id,Model model){
        try {
            Provider provider=providerService.findProviderById(id);
            model.addAttribute(provider);
            return "providerview";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }

    }
    //根据id删除供应商
    @RequestMapping(value = "delProvider",method = RequestMethod.POST)
    @ResponseBody
    public JSON delProvider(Integer id){
        try {
            Map<String,String> resultMap=new HashMap<>();
            Integer result=providerService.delProvider(id);
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
    //根据id更新供应商

    //去更新供应商页面
    @RequestMapping(value = "toModifyProvider.html",method = RequestMethod.GET)
    public String toModifyProvider(@ModelAttribute("provider")Provider provider, Integer id, Model model){
        try {
             provider=providerService.findProviderById(id);
             model.addAttribute(provider);
             return "providermodify";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }

    //做更新操作
    @RequestMapping(value = "doModifyProvider",method = RequestMethod.POST)
    public String doModifyProvider(Provider provider, HttpSession session){
        try {
            User user= (User) session.getAttribute(Constants.USER_SESSION);
            provider.setModifyBy(user.getId());
            provider.setModifyDate(new Date());
            Integer result=providerService.modifyProvider(provider);
            if(result>0){
                return "redirect:/provider/showProvider.html";
            }else{
                return "redirect:/provider/toModifyProvider.html?id="+provider.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }

    //去添加页面
    @RequestMapping(value = "toAddProvider",method = RequestMethod.GET)
    public String toAddProvider(@ModelAttribute("provider")Provider provider){
        return "provideradd";
    }

    //做添加操作
    @RequestMapping(value = "doAddProvider",method = RequestMethod.POST)
    public String doAddProvider(Provider provider,HttpSession session){
        try {
            User user= (User) session.getAttribute(Constants.USER_SESSION);
            provider.setCreatedBy(user.getId());
            provider.setCreationDate(new Date());
            Integer result=providerService.saveProvider(provider);
            if(result>0){
                return "redirect:/provider/showProvider.html";
            }else{
                return "redirect:/provider/toAddProvider";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }

}
