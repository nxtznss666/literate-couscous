package cn.kgc.tangcco.smbms.dao.provider;

import cn.kgc.tangcco.smbms.pojo.Provider;
import cn.kgc.tangcco.smbms.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
    //获得所有供应商
    List<Provider> providerList();
    //分页显示供应商
    List<Provider> getProviderList(@Param("proCode") Integer queryProCode, @Param("proName") String queryProName)throws Exception;
    //根据id删除供应商
    Integer delProvider(Integer id)throws Exception;
    //根据id更新供应商
    Provider getProviderById(Integer id)throws Exception;
    Integer updateProvider(Provider provider)throws Exception;
    //增加供应商
    Integer addProvider(Provider provider)throws Exception;
}
