package design.parent.overall.dao;


import design.parent.overall.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface TestDao {
    User tParam(@Param("str") String str, @Param("map") Map<String, Object> map);
    User paging(@Param("offset") int offset, @Param("size") int size, @Param("condition") String condition,
                @Param("map") Map<String, Object> sorted, @Param("params") Object... params);
    User tP(@Param("params") Object... params);
    void pro_user(Map<String, Object> sorted);
}
