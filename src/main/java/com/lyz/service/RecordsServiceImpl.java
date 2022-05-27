package com.lyz.service;

import com.github.pagehelper.PageHelper;
import com.lyz.mapper.RecordsMapper;
import com.lyz.pojo.Records;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecordsServiceImpl implements RecordsService {

    @Autowired
    private RecordsMapper recordsMapper;

    public void setRecordsMapper(RecordsMapper recordsMapper) {
        this.recordsMapper = recordsMapper;
    }

    public List<Records> getAllRecords(Integer page, Integer userId) {
        PageHelper.startPage(page, 10);
        return recordsMapper.getAllRecords(page, userId);
    }

    public Integer deleteRecord(Integer recordId) {
        return recordsMapper.deleteRecord(recordId);
    }

    public void insertRecord(Integer userId, String type, String studentName, String position, Date date) {
        recordsMapper.insertRecord(userId, type, studentName, position, date);
    }

    public void deleteRecords(Integer userId) {
        recordsMapper.deleteRecords(userId);
    }
}
