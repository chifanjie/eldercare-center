package edu.ysu.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Service {
    @TableId(value = "sid",type = IdType.AUTO)
    private Integer sid;
    private Integer uid;
    private Integer cid;
    private Integer last;
    private Integer num;
    private Integer residue;
    private String buy;
    private Integer statue;
    private String memo;
    private Integer frequency;
    @TableField(exist = false)
    private String customerName;
    @TableField(exist = false)
    private String contentName;
}
