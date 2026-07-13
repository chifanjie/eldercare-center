package edu.ysu.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("emergency_set")
public class Emergency {
    @TableId(value = "eid",type = IdType.AUTO)
    private Integer eid;
    private Integer uid;
    private String name;
    private String gander;
    private Integer age;
    private String relation;
    private String id;
    private String address;
    private String tel;
}
