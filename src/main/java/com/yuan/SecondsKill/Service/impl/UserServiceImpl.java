package com.yuan.SecondsKill.Service.impl;

import com.yuan.SecondsKill.Service.UserService;
import com.yuan.SecondsKill.domain.User;
import com.yuan.SecondsKill.dao.Userdao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired(required=false)
    private Userdao userDao;

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    //@Transactional
    public boolean tx() {
        User u1=new User();
        u1.setId(2);
        u1.setName("lili");
        userDao.insert(u1);

        User u2=new User();
        u2.setId(1);
        u2.setName("xixi");
        userDao.insert(u2);
        return true;
    }
}
