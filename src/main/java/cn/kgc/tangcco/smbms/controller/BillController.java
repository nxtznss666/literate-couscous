package cn.kgc.tangcco.smbms.controller;

import cn.kgc.tangcco.smbms.pojo.Bill;
import cn.kgc.tangcco.smbms.pojo.Provider;
import cn.kgc.tangcco.smbms.pojo.User;
import cn.kgc.tangcco.smbms.service.bill.BillService;
import cn.kgc.tangcco.smbms.service.provider.ProviderService;
import cn.kgc.tangcco.smbms.tools.Constants;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("bill")
public class BillController {
    @Resource
    private BillService billService;
    @Resource
    private ProviderService providerService;
    /*分页*/
    @RequestMapping("showBillList.html")
    public String showBillList(@RequestParam(value = "queryProductName",required = false)String productName, @RequestParam(value = "queryProviderId",required = false)Integer providerId, @RequestParam(value = "queryIsPayment",required = false)Integer isPayment, Model model,@RequestParam(value = "pageIndex",required = false)String pageIndex){
        int currentPageNo=1;
        if(pageIndex!=null){
            currentPageNo=Integer.valueOf(pageIndex);
        }


        try {
            PageHelper.startPage(currentPageNo, Constants.pageSize);
            List<Bill> billList = billService.findBillList(productName,providerId,isPayment);
            PageInfo<Bill> pageInfo=new PageInfo<>(billList);
            model.addAttribute(billList);
            model.addAttribute("currentPageNo",currentPageNo);
            model.addAttribute(pageInfo);
            return "billlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }

    }
    /*根据id查询订单*/
    @RequestMapping(value = "showBill.html",method = RequestMethod.GET)
    public String showBill(Integer id, Model model){
        try {
            Bill bill=billService.findBillById(id);
            if(null!=bill){
                model.addAttribute(bill);
                return "billview";
            }else{
                return "redirect:../error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }
    /*去更新页面*/
    @RequestMapping(value = "toUpdateBill.html",method = RequestMethod.GET)
    public String toUpdateBill(Integer id, @ModelAttribute("bill")Bill bill,Model model){
        try {
            List<Provider> providerList=providerService.providerList();
            bill=billService.findBillById(id);
            if(null!=bill){
                model.addAttribute(providerList);
                model.addAttribute(bill);
                return "billmodify";
            }else{
                return "redirect:../error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }
    /*做修改操作*/
    @RequestMapping(value = "doUpdateBill.html",method = RequestMethod.POST)
    public String doUpdateBill(Bill bill, HttpSession session){
        try {
            User user= (User) session.getAttribute(Constants.USER_SESSION);
            bill.setModifyBy(user.getId());
            bill.setModifyDate(new Date());
            Integer result=billService.modifyBill(bill);
            if(result>0){
                return "redirect:/bill/showBillList.html";
            }else{
                return "redirect:../error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }
    /*删除订单*/
    @RequestMapping(value = "delBill",method = RequestMethod.POST)
    @ResponseBody
    public JSON delBill(Integer id){
        try {
            Map<String,String> resultMap=new HashMap<>();
            Integer result=billService.delBill(id);
            if(result>0){
                resultMap.put("result","true");
            }else{
                resultMap.put("result","false");
            }
            return (JSON) JSON.toJSON(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }
    /*增加订单*/
    @RequestMapping("toAddBill")
    public String toAddBill(@ModelAttribute("bill")Bill bill){
        return "billadd";
    }

    /*做增加操作*/
    @RequestMapping("doAddBill")
    public String doAddBill(Bill bill,HttpSession session){
        try {
            User user= (User) session.getAttribute(Constants.USER_SESSION);
            user.setCreatedBy(user.getId());
            user.setCreationDate(new Date());
            Integer result=billService.saveBill(bill);
            if(result>0){
                return "redirect:/bill/showBillList.html";
            }else{
                return "redirect:../error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:../error";
        }
    }
}
