package design.parent.overall.dao;

import design.parent.overall.entity.User;

import java.util.List;

public interface Test {
    String test();
    List<User> getAll();
    User getUser(String name);
}
