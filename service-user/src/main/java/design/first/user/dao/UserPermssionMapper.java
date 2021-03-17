package design.first.user.dao;

import design.first.user.entity.UserPermssion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPermssionMapper {

    /**
     * delete by primary key
     * @param upNo primaryKey
     * @return deleteCount
     */
    int deleteByNo(Long upNo);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(UserPermssion record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(UserPermssion record);

    /**
     * select by primary key
     * @param upId primary key
     * @return object by primary key
     */
    UserPermssion selectByPrimaryKey(Integer upId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByNoSelective(UserPermssion record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(UserPermssion record);

    int batchInsert(@Param("list") List<UserPermssion> list);
}