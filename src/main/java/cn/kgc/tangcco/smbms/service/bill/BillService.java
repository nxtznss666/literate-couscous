package cn.kgc.tangcco.smbms.service.bill;

import cn.kgc.tangcco.smbms.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillService {
    //订单分页
    List<Bill> findBillList(@Param("productName") String productName, @Param("providerId") Integer providerId, @Param("isPayment")Integer isPayment)throws Exception;

    //删除订单
    Integer delBill(Integer id)throws Exception;

    //查询订单
    Bill findBillById(Integer id)throws Exception;

    //修改订单
    Integer modifyBill(Bill bill)throws Exception;

    //增加订单
    Integer saveBill(Bill bill)throws Exception;
}
