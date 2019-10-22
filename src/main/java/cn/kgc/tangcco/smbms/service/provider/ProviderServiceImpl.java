package cn.kgc.tangcco.smbms.service.provider;

import cn.kgc.tangcco.smbms.dao.provider.ProviderMapper;
import cn.kgc.tangcco.smbms.pojo.Provider;
import cn.kgc.tangcco.smbms.pojo.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Resource
    private ProviderMapper providerMapper;
    @Override
    public List<Provider> findProviderList(Integer queryProCode, String queryProName) throws Exception {
        return providerMapper.getProviderList(queryProCode,queryProName);
    }

    @Override
    public Integer delProvider(Integer id) throws Exception {
        return providerMapper.delProvider(id);
    }

    @Override
    public Provider findProviderById(Integer id) throws Exception {
        return providerMapper.getProviderById(id);
    }

    @Override
    public Integer modifyProvider(Provider provider) throws Exception {
        return providerMapper.updateProvider(provider);
    }

    @Override
    public Integer saveProvider(Provider provider) throws Exception {
        return providerMapper.addProvider(provider);
    }

    @Override
    public List<Provider> providerList() {
        return providerMapper.providerList();
    }
}
