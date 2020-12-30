package design.parent.overall.entity;
import lombok.Data;

@Data
public class Roles {
    String roleId;
    String roleRemark;

    public Roles() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }
}
