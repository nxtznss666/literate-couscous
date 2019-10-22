package cn.kgc.tangcco.smbms.service.user;

import cn.kgc.tangcco.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    //登陆
    User getUser(String userCode,String pwd)throws Exception;
    //分页显示用户(根据名字模糊查询,权限)
    List<User> getUserList(@Param("userName")String userCode, @Param("userRole")Integer userRole)throws Exception;
    //根据id删除用户
    Integer delUser(Integer id)throws Exception;
    //根据id查询用户
    User getUserById(Integer id)throws Exception;
    //根据id更新用户
    User getUserByUserCode(String userCode)throws Exception;
    Integer updateUser(User user)throws Exception;
    //创建用户
    Integer addUser(User user)throws Exception;
}
