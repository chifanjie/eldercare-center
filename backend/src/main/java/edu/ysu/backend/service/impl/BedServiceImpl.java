package edu.ysu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.ysu.backend.entity.dto.Bed;
import edu.ysu.backend.entity.dto.Building;
import edu.ysu.backend.entity.dto.Customer;
import edu.ysu.backend.entity.dto.Emergency;
import edu.ysu.backend.entity.dto.Room;
import edu.ysu.backend.entity.pojo.Result;
import edu.ysu.backend.entity.vo.Design;
import edu.ysu.backend.entity.vo.Exchange;
import edu.ysu.backend.mapper.*;
import edu.ysu.backend.service.BedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private BedMapper bedMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private EmergencySetMapper emergencySetMapper;

    @Override
    public Result getBedRecordPaged(String keyword, int pageNum, int pageSize) {
        Page<Customer> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
        if (keyword==null||keyword.equals("")){
            queryWrapper.eq("del",0);
        }else {
            queryWrapper.like("name",keyword).and(condition->condition.eq("del",0));
        }
        IPage<Customer> iPage=customerMapper.selectPage(page,queryWrapper);
        for (Customer customer:iPage.getRecords()){
            Building building=buildingMapper.selectCustomerInWhere(customer.getUid());
            customer.setBedInfo(building.getName()+building.getRoomName()+"-"+building.getBedName());
        }
        return Result.ok("查询床位总览记录成功").data("recordP",iPage);
    }

    @Override
    public Result getCustomerBedRecord(String uid) {
        Building buildingRecord=buildingMapper.selectBuildingRecordByUid(Integer.valueOf(uid));
        return Result.ok("查询客户床位记录成功").data("records",buildingRecord);
    }

    @Override
    public Result exchangeCustomerBedRecord(Exchange exchange) {
        Building beforeBuilding=buildingMapper.selectBuildingRecordByUid(Integer.valueOf(exchange.getBefore()));
        Building afterBuilding=buildingMapper.selectBuildingRecordByUid(Integer.valueOf(exchange.getAfter()));
        Bed before=bedMapper.selectOne(new QueryWrapper<Bed>().eq("eid",beforeBuilding.getEid()));
        Bed after=bedMapper.selectOne(new QueryWrapper<Bed>().eq("eid",afterBuilding.getEid()));
        Bed beforeUpdate=Bed.builder()
                .eid(before.getEid())
                .statue(after.getStatue()).build();
        Bed afterUpdate=Bed.builder()
                .eid(after.getEid())
                .statue(before.getStatue()).build();
        bedMapper.updateById(beforeUpdate);
        bedMapper.updateById(afterUpdate);
        Integer beforeCid=buildingMapper.selectContact(Integer.valueOf(exchange.getBefore()));
        Integer afterCid=buildingMapper.selectContact(Integer.valueOf(exchange.getAfter()));
        Integer temp;
        temp=beforeCid;
        beforeCid=afterCid;
        afterCid=temp;
        Integer updateBefore = buildingMapper.updateContact(beforeCid, Integer.valueOf(exchange.getBefore()));
        Integer updateAfter = buildingMapper.updateContact(afterCid, Integer.valueOf(exchange.getAfter()));
        return updateBefore>0&&updateAfter>0?Result.ok("床位调换成功"):Result.fail("床位调换失败");
    }

    @Override
    public Result resetCustomerRecord(Exchange exchange) {
        Integer eid= Integer.valueOf(exchange.getBuilding().substring(exchange.getBuilding().indexOf("-")+1));
        QueryWrapper<Bed> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("eid",eid);
        Bed bed = bedMapper.selectOne(queryWrapper);
        if (bed.getStatue()!=0){
            return Result.fail("该床位已经有人了").data("err",500);
        }else {
            Integer cid = buildingMapper.selectContrast(eid);
            Bed beforeBed=bedMapper.selectOne(new QueryWrapper<Bed>().eq("eid",exchange.getSetData().getEid()));
            bedMapper.updateById(Bed.builder().eid(beforeBed.getEid()).statue(bed.getStatue()).build());
            int updateBed = bedMapper.updateById(Bed.builder().eid(eid).statue(beforeBed.getStatue()).build());
            Integer updateContact = buildingMapper.updateContact(cid, Integer.valueOf(exchange.getSet()));
            return updateBed>0&&updateContact>0?Result.ok("床位调换成功"):Result.fail("床位调换失败");
        }
    }

    @Override
    public Result getBedDesignRecord(String round, String keyword) {
        List<Building> baseRecord=new ArrayList<>();
        if (keyword==null||"".equals(keyword)){
            baseRecord=buildingMapper.selectBuildingDesignAllRecord(round);
        }else {
            baseRecord=buildingMapper.selectBuildingDesignRecord(round,keyword);
        }
        if (baseRecord.isEmpty()){
            return Result.ok("没有记录").data("record",null);
        }else {
            QueryWrapper<Building> buildingQueryWrapper=new QueryWrapper<>();
            buildingQueryWrapper.eq("name",round);
            Building building = buildingMapper.selectOne(buildingQueryWrapper);
            Design record= Design.builder()
                    .building(building.getBid())
                    .buildingName(building.getName())
                    .room(new HashSet<>()).build();
            Set<Integer> roomId=new HashSet<>();
            for (Building ids:baseRecord){
                roomId.add(ids.getRid());
            }
            for (Building data:baseRecord){
                if (record.getRoom().isEmpty()){
                    QueryWrapper<Room> roomQueryWrapper=new QueryWrapper<>();
                    roomQueryWrapper.eq("rid",data.getRid());
                    Room roomRecord=roomMapper.selectOne(roomQueryWrapper);
                    edu.ysu.backend.entity.vo.Room room= edu.ysu.backend.entity.vo.Room.builder()
                            .rid(roomRecord.getRid())
                            .roomName(roomRecord.getName())
                            .bed(new HashSet<>()).build();
                    record.getRoom().add(room);
                }else {
                    for (Integer room:roomId){
                        if (room.equals(data.getRid())){
                            QueryWrapper<Room> roomQueryWrapper=new QueryWrapper<>();
                            roomQueryWrapper.eq("rid",data.getRid());
                            Room roomRecordEntity=roomMapper.selectOne(roomQueryWrapper);
                            edu.ysu.backend.entity.vo.Room roomRecord= edu.ysu.backend.entity.vo.Room.builder()
                                    .rid(roomRecordEntity.getRid())
                                    .roomName(roomRecordEntity.getName())
                                    .bed(new HashSet<>()).build();
                            record.getRoom().add(roomRecord);
                        }
                    }
                }
            }
            for (Building data:baseRecord){
                for (edu.ysu.backend.entity.vo.Room room:record.getRoom()){
                    if (room.getRid().equals(data.getRid())){
                        QueryWrapper<Bed> bedQueryWrapper=new QueryWrapper<>();
                        bedQueryWrapper.eq("eid",data.getEid());
                        Bed bedRecord=bedMapper.selectOne(bedQueryWrapper);
                        edu.ysu.backend.entity.vo.Bed bed= edu.ysu.backend.entity.vo.Bed.builder()
                                .bedName(bedRecord.getName())
                                .eid(bedRecord.getEid())
                                .uid(buildingMapper.selectBedUid(data.getCid()))
                                .statue(bedRecord.getStatue()).build();
                        room.getBed().add(bed);
                    }
                }
            }
            return Result.ok("查询成功").data("record",record);
        }
    }

    @Override
    public Result getBedCustomer(Integer uid) {
        QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
        customerQueryWrapper.eq("uid",uid);
        Customer customer = customerMapper.selectOne(customerQueryWrapper);
        QueryWrapper<Emergency> emergencyQueryWrapper=new QueryWrapper<>();
        emergencyQueryWrapper.eq("uid",customer.getUid());
        List<Emergency> emergencies = emergencySetMapper.selectList(emergencyQueryWrapper);
        if (!emergencies.isEmpty()){
            customer.setEmergency(emergencies.get((int)(Math.random()*emergencies.size())));
        }else {
            Emergency emergency=Emergency.builder()
                    .name("")
                    .tel("").build();
            customer.setEmergency(emergency);
        }
        return Result.ok("查询床位用户信息成功").data("customer",customer);
    }
}
