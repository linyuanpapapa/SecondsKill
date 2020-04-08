package com.yuan.SecondsKill.Service;

import com.yuan.SecondsKill.domain.User;

public interface UserService {
    public User getById(int id);

    public boolean tx();
}
