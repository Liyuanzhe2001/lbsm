package com.lyz.mapper;

import com.lyz.pojo.Records;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RecordsMapper {

    //查询所有操作记录
    List<Records> getAllRecords(@Param("page") Integer page, @Param("userId") Integer userId);

    //删除一条操作记录
    Integer deleteRecord(@Param("id") Integer recordId);

    //添加操作记录
    void insertRecord(@Param("user_id") Integer userId, @Param("type") String type, @Param("student_name") String studentName, @Param("position") String position, @Param("time") Date date);

    //删除记录
    void deleteRecords(@Param("user_id") Integer userId);
}
