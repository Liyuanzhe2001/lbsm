package com.lyz.service;

import com.lyz.pojo.Records;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RecordsService {

    //查询所有操作记录
    List<Records> getAllRecords(Integer page, Integer userId);

    //删除操作记录
    Integer deleteRecord(Integer recordId);

    //添加操作记录
    void insertRecord(Integer userId, String type, String studentName, String position, Date date);

    //删除记录
    void deleteRecords(Integer userId);
}
