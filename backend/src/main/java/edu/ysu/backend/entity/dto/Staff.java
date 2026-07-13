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
public class Staff {
    @TableId(value = "sid",type = IdType.INPUT)
    private Integer sid;
    private String name;
    private String gander;
    private String age;
    private String avatar;
    private Integer del;
    private String memo;
    private String tel;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String password;
    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private Integer role;
    @TableField(exist = false)
    private String roleName;
    @TableField(exist = false)
    private List<String> serviceName;
}
