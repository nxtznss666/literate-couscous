package cn.kgc.tangcco.smbms.service.bill;

import cn.kgc.tangcco.smbms.dao.bill.BillMapper;
import cn.kgc.tangcco.smbms.pojo.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Resource
    private BillMapper billMapper;


    @Override
    public List<Bill> findBillList(String productName, Integer providerId, Integer isPayment)throws Exception {
        return billMapper.getBillList(productName,providerId,isPayment);
    }

    @Override
    public Integer delBill(Integer id)throws Exception {
        return billMapper.delBill(id);
    }

    @Override
    public Bill findBillById(Integer id)throws Exception {
        return billMapper.getBillById(id);
    }

    @Override
    public Integer modifyBill(Bill bill)throws Exception {
        return billMapper.updateBill(bill);
    }

    @Override
    public Integer saveBill(Bill bill)throws Exception {
        return billMapper.addBill(bill);
    }
}
