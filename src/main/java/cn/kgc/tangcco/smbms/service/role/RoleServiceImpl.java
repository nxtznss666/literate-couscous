package cn.kgc.tangcco.smbms.service.role;

import cn.kgc.tangcco.smbms.dao.role.RoleMapper;
import cn.kgc.tangcco.smbms.pojo.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleList() throws Exception {
        return roleMapper.getRoleList();
    }
}
