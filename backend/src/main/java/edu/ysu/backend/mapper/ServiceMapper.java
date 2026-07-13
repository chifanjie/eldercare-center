package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Service;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ServiceMapper extends BaseMapper<Service> {

    @Select("SELECT customer.name " +
            "FROM customer,staff,service_set " +
            "WHERE customer.uid=service_set.cid " +
            "AND staff.sid=service_set.sid " +
            "AND service_set.sid=#{sid} LIMIT 3")
    List<String> selectServiceName(Integer sid);

    @Select("SELECT staff.name " +
            "FROM customer,staff,support_set " +
            "WHERE customer.uid=support_set.uid " +
            "AND staff.sid=support_set.sid " +
            "AND support_set.uid=#{uid} LIMIT 3")
    List<String> selectSupportName(Integer uid);

    @Select("SELECT staff.name " +
            "FROM staff,service_set,customer " +
            "WHERE staff.sid=service_set.sid " +
            "AND customer.uid=service_set.cid " +
            "AND customer.uid=#{uid} LIMIT 3")
    List<String> selectButlerByUid(Integer uid);

    @Select("SELECT customer.uid " +
            "FROM support_set,customer " +
            "WHERE customer.uid=support_set.uid " +
            "AND customer.del=0  " +
            "AND sid=#{sid}")
    List<Integer> selectSupportProvider(Integer sid);

    @Select("SELECT customer.uid " +
            "FROM customer,service_set " +
            "WHERE customer.uid=service_set.cid " +
            "AND customer.del=0 " +
            "AND sid=#{sid}")
    List<Integer> selectButlerProvider(Integer sid);

    @Select("SELECT cid FROM service_set WHERE sid=#{sid}")
    List<Integer> selectServiceId(Integer sid);

    @Select("SELECT support_set.sid " +
            "FROM support_set,staff " +
            "WHERE support_set.sid=staff.sid " +
            "AND staff.del=0 " +
            "AND support_set.uid=#{uid}")
    List<Integer> selectSupportId(Integer uid);

    @Select("SELECT sid FROM service_set WHERE cid=#{cid}")
    List<Integer> selectButlerId(Integer cid);

    @Delete("DELETE FROM service_set WHERE sid=#{sid}")
    Integer deleteServiceSet(Integer sid);

    @Insert("INSERT INTO service_set(sid,cid) VALUES(#{sid},#{cid})")
    Integer insertServiceSet(@Param("sid") Integer sid,@Param("cid") Integer cid);

    @Insert("INSERT INTO support_set(sid,uid) VALUES(#{sid},#{uid})")
    Integer insertSupportSet(@Param("sid") Integer sid,@Param("uid") Integer uid);

    @Select("SELECT COUNT(*) FROM service_set WHERE cid=#{cid} AND sid=#{sid}")
    Integer selectButlerExist(@Param("sid") Integer sid,@Param("cid") Integer cid);

    @Select("SELECT COUNT(*) FROM support_set WHERE uid=#{uid} AND sid=#{sid}")
    Integer selectSupportExist(@Param("sid") Integer sid,@Param("uid") Integer uid);

    @Delete("DELETE FROM service_set WHERE sid=#{sid} AND cid=#{cid}")
    Integer deleteServiceSetRecord(@Param("sid") Integer sid,@Param("cid") Integer cid);

    @Delete("DELETE FROM support_set WHERE sid=#{sid} AND uid=#{uid}")
    Integer deleteSupportSetRecord(@Param("sid") Integer sid,@Param("uid") Integer uid);
}
