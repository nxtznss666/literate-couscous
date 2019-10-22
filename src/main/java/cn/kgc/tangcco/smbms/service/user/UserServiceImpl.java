package cn.kgc.tangcco.smbms.service.user;

import cn.kgc.tangcco.smbms.dao.user.UserMapper;
import cn.kgc.tangcco.smbms.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getUser(String userCode,String pwd) throws Exception {
        User user=userMapper.getUser(userCode);
        if(null==user&&!user.getUserPassword().equals(pwd)){
            return null;
        }
        return user;
    }

    @Override
    public List<User> getUserList(String userCode, Integer userRole) throws Exception {

        return userMapper.getUserList(userCode,userRole);
    }

    @Override
    public Integer delUser(Integer id) throws Exception {
        return userMapper.delUser(id);
    }

    @Override
    public User getUserById(Integer id) throws Exception {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByUserCode(String userCode) throws Exception {
        return userMapper.getUserByUserCode(userCode);
    }

    @Override
    public Integer updateUser(User user) throws Exception {
        return userMapper.updateUser(user);
    }

    @Override
    public Integer addUser(User user) throws Exception {
        return userMapper.addUser(user);
    }
}
