package design.parent.overall.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String name;
    private String pwd;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
