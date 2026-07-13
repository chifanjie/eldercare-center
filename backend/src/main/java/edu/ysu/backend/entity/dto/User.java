package edu.ysu.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class User {
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer uid;
    private String username;
    private String password;
    private String email;
    private Integer role;
    private Integer del;
}
