package ru.alex.task_managemen_system.service;

import ru.alex.task_managemen_system.model.user.User;

import java.util.List;

public interface AdminService {

    public List<User> getUsers() ;

    public Boolean isBlockUser(String id);
}
