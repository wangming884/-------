package com.library.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.common.PageResult;
import com.library.entity.SysLog;
import com.library.mapper.SysLogMapper;
import com.library.service.SysLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    private final SysLogMapper logMapper;

    public SysLogServiceImpl(SysLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public void log(Long adminId, String adminName, String operation, String method, String params, String ip, Long duration, int status) {
        SysLog log = new SysLog();
        log.setAdminId(adminId);
        log.setAdminName(adminName);
        log.setOperation(operation);
        log.setMethod(method);
        log.setParams(params);
        log.setIp(ip);
        log.setDuration(duration);
        log.setStatus(status);
        logMapper.insert(log);
    }

    @Override
    public PageResult<SysLog> listPage(int page, int size, String keyword, Integer status) {
        return listPage(page, size, keyword, status, null, null, null, null);
    }

    @Override
    public PageResult<SysLog> listPage(int page, int size, String keyword, Integer status,
                                       String operator, String operation, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startTime = startDate == null ? null : startDate.atStartOfDay();
        LocalDateTime endTime = endDate == null ? null : endDate.plusDays(1).atStartOfDay();
        Page<SysLog> result = logMapper.selectLogPage(new Page<>(page, size), keyword, status,
                operator, operation, startTime, endTime);
        return new PageResult<>(result.getRecords(), result.getTotal(), page, size);
    }
}
