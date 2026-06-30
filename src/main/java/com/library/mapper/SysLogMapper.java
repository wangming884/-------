package com.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.library.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    @Select({
            "<script>",
            "SELECT id, admin_id, admin_name, operation, method, params, ip, duration, status, create_time, update_time",
            "FROM sys_log",
            "WHERE 1 = 1",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (operation LIKE CONCAT('%', #{keyword}, '%')",
            "OR admin_name LIKE CONCAT('%', #{keyword}, '%')",
            "OR method LIKE CONCAT('%', #{keyword}, '%')",
            "OR params LIKE CONCAT('%', #{keyword}, '%'))",
            "</if>",
            "<if test='operator != null and operator != \"\"'>AND admin_name LIKE CONCAT('%', #{operator}, '%')</if>",
            "<if test='operation != null and operation != \"\"'>AND operation LIKE CONCAT('%', #{operation}, '%')</if>",
            "<if test='status != null'>AND status = #{status}</if>",
            "<if test='startTime != null'>AND create_time &gt;= #{startTime}</if>",
            "<if test='endTime != null'>AND create_time &lt; #{endTime}</if>",
            "ORDER BY create_time DESC",
            "</script>"
    })
    Page<SysLog> selectLogPage(Page<SysLog> page,
                               @Param("keyword") String keyword,
                               @Param("status") Integer status,
                               @Param("operator") String operator,
                               @Param("operation") String operation,
                               @Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime);
}
