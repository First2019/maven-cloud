package design.first.user.dao;

import design.first.user.entity.Permission;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {

    List<Permission> getByCondition(Permission record);

    /**
     * 通过用户编号查询权限
     * @param userNo
     * @return
     */
    List<Permission> getPermByUserNo(Long userNo);

    /**
     * delete by no
     * @param permNo primaryKey
     * @return deleteCount
     */
    int deleteByNo(Long permNo);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(Permission record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Permission record);

    /**
     * select by no
     * @param permNo primary key
     * @return object by primary key
     */
    Permission selectByNo(Long permNo);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByNo(Permission record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Permission record);

    int batchInsert(@Param("list") List<Permission> list);

}