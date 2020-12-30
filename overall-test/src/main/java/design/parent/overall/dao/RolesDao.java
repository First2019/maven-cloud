package design.parent.overall.dao;


import design.parent.overall.entity.Roles;

import java.util.List;

public interface RolesDao {
    List<Roles> getAllRoles();
    /**
     * 数据库新增角色
     * @param roles
     * @return
     */
    Integer addRoles(Roles roles);

    /**
     * 通过id删除角色
     * @param id
     * @return
     */
    Integer delRoles(String id);

    /**
     * 通过id查找角色
     * @param roleId
     * @return
     */
    Roles findRolesByRoleId(String roleId);
}
