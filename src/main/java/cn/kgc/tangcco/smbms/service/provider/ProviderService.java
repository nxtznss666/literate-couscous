package cn.kgc.tangcco.smbms.service.provider;

import cn.kgc.tangcco.smbms.pojo.Provider;
import cn.kgc.tangcco.smbms.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderService {
    //分页显示供应商
    List<Provider> findProviderList(@Param("proCode") Integer queryProCode, @Param("ProName") String queryProName)throws Exception;
    //根据id删除供应商
    Integer delProvider(Integer id)throws Exception;
    //根据id更新供应商
    Provider findProviderById(Integer id)throws Exception;
    Integer modifyProvider(Provider provider)throws Exception;
    //增加供应商
    Integer saveProvider(Provider provider)throws Exception;
    //获得所有供应商
    List<Provider> providerList();
}
