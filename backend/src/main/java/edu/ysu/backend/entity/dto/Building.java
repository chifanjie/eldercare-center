package edu.ysu.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Building {
    @TableId(value = "bid",type = IdType.AUTO)
    private Integer bid;
    private String name;
    private Integer floor;
    private Integer room;
    private Integer del;
    private String community;
    @TableField(exist = false)
    private String roomName;
    @TableField(exist = false)
    private String bedName;
    @TableField(exist = false)
    private Integer eid;
    @TableField(exist = false)
    private Integer rid;
    @TableField(exist = false)
    private Integer cid;
}
