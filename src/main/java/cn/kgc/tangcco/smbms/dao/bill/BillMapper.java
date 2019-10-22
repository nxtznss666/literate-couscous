package cn.kgc.tangcco.smbms.dao.bill;

import cn.kgc.tangcco.smbms.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
    //订单分页
    List<Bill> getBillList(@Param("productName") String productName,@Param("providerId") Integer providerId, @Param("isPayment")Integer isPayment);

    //删除订单
    Integer delBill(Integer id);

    //查询订单
    Bill getBillById(Integer id);

    //修改订单
    Integer updateBill(Bill bill);

    //增加订单
    Integer addBill(Bill bill);
}
