package cn.kgc.tangcco.smbms.dao.role;

import cn.kgc.tangcco.smbms.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    List<Role> getRoleList()throws Exception;
}
