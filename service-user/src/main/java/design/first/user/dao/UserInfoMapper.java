package design.first.user.dao;

import design.first.user.entity.UserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {
    /**
     * delete by primary key
     * @param userId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(UserInfo record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(UserInfo record);

    /**
     * select by primary key
     * @param userLoginAccount 账号
     * @return object by primary key
     */
    UserInfo selectByAccount(String userLoginAccount);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByUserNo(UserInfo record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(UserInfo record);

    int batchInsert(@Param("list") List<UserInfo> list);

    List<UserInfo> selectListByCondition(UserInfo userInfo);
}