package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Building;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuildingMapper extends BaseMapper<Building> {

    @Select("SELECT building.`name`,room.`name` AS roomName,bed.`name` AS bedName " +
            "FROM customer_building_room_bed_contrast,building_room_bed_contrast,customer,building,room,bed " +
            "WHERE customer_building_room_bed_contrast.cid=building_room_bed_contrast.cid " +
            "AND customer_building_room_bed_contrast.uid=customer.uid " +
            "AND building_room_bed_contrast.bid=building.bid " +
            "AND building_room_bed_contrast.eid=bed.eid " +
            "AND building_room_bed_contrast.rid=room.rid " +
            "AND customer.uid=#{uid}")
    Building selectCustomerInWhere(Integer uid);

    @Select("SELECT building.bid,room.rid,bed.eid,building.name,room.name AS roomName,bed.name AS bedName " +
            "FROM building,room,bed,building_room_bed_contrast " +
            "WHERE building.bid=building_room_bed_contrast.bid " +
            "AND room.rid=building_room_bed_contrast.rid " +
            "AND bed.eid=building_room_bed_contrast.eid " +
            "AND bed.statue=0")
    List<Building> selectBuildingMenue();

    @Select("SELECT building.`name`,room.floor,room.`name` AS roomName,bed.`name` AS bedName,building.bid,room.rid,bed.eid " +
            "FROM customer,building,room,bed,customer_building_room_bed_contrast,building_room_bed_contrast " +
            "WHERE customer_building_room_bed_contrast.cid=building_room_bed_contrast.cid " +
            "AND customer_building_room_bed_contrast.uid=customer.uid " +
            "AND building_room_bed_contrast.bid=building.bid " +
            "AND building_room_bed_contrast.rid=room.rid " +
            "AND building_room_bed_contrast.eid=bed.eid " +
            "AND customer.uid=#{uid}")
    Building selectBuildingRecordByUid(Integer uid);

    @Select("SELECT building_room_bed_contrast.cid,building_room_bed_contrast.bid,building_room_bed_contrast.rid,building_room_bed_contrast.eid " +
            "FROM building_room_bed_contrast,customer_building_room_bed_contrast,customer,building,room,bed " +
            "WHERE building_room_bed_contrast.cid=customer_building_room_bed_contrast.cid " +
            "AND customer_building_room_bed_contrast.uid=customer.uid " +
            "AND building_room_bed_contrast.bid=building.bid " +
            "AND building_room_bed_contrast.rid=room.rid " +
            "AND building_room_bed_contrast.eid=bed.eid " +
            "AND ((customer.`name` LIKE CONCAT('%',#{keyword},'%') OR room.`name` LIKE CONCAT('%',#{keyword},'%')) AND building.`name`=#{round})")
    List<Building> selectBuildingDesignRecord(@Param("round") String round,@Param("keyword") String keyword);

    @Select("SELECT building_room_bed_contrast.cid,building_room_bed_contrast.bid,building_room_bed_contrast.rid,building_room_bed_contrast.eid " +
            "FROM building_room_bed_contrast,building,room,bed " +
            "WHERE building_room_bed_contrast.bid=building.bid " +
            "AND building_room_bed_contrast.rid=room.rid " +
            "AND building_room_bed_contrast.eid=bed.eid " +
            "AND building.`name`=#{round}")
    List<Building> selectBuildingDesignAllRecord(String round);

    @Select("SELECT building.`name`,room.`name` AS roomName " +
            "FROM building_room_bed_contrast,building,room,bed " +
            "WHERE building.bid=building_room_bed_contrast.bid " +
            "AND room.rid=building_room_bed_contrast.rid " +
            "AND bed.eid=building_room_bed_contrast.eid " +
            "AND bed.eid=#{eid}")
    Building selectWhereIsBed(Integer eid);

    @Select("SELECT uid FROM customer_building_room_bed_contrast WHERE cid=#{cid}")
    Integer selectBedUid(Integer cid);

    @Select("SELECT cid FROM building_room_bed_contrast WHERE eid=#{eid}")
    Integer selectContrast(Integer eid);

    @Insert("INSERT INTO customer_building_room_bed_contrast VALUES(#{uid},#{cid})")
    Integer insertIntoCustomerContact(@Param("uid") int uid,@Param("cid") int cid);

    @Select("SELECT building_room_bed_contrast.cid,building_room_bed_contrast.bid,building_room_bed_contrast.rid,building_room_bed_contrast.eid " +
            "FROM building_room_bed_contrast,customer_building_room_bed_contrast,customer " +
            "WHERE building_room_bed_contrast.cid=customer_building_room_bed_contrast.cid " +
            "AND customer.uid=customer_building_room_bed_contrast.uid " +
            "AND customer.uid=#{uid}")
    Building selectBuildingNoByUid(Integer uid);

    @Select("SELECT cid FROM customer_building_room_bed_contrast WHERE uid=#{uid}")
    Integer selectContact(Integer uid);

    @Update("UPDATE customer_building_room_bed_contrast SET cid=#{cid} WHERE uid=#{uid}")
    Integer updateContact(@Param("cid") Integer cid,@Param("uid") Integer uid);

    @Delete("DELETE FROM customer_building_room_bed_contrast WHERE uid=#{uid} AND cid=#{cid}")
    Integer deleteContact(@Param("uid") Integer uid,@Param("cid") Integer cid);

}
