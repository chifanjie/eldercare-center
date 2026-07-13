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
public class NurseContent {
    @TableId(value = "cid",type = IdType.AUTO)
    private Integer cid;
    private String no;
    private String name;
    private String price;
    private String description;
    private Integer value;
    private Integer statue;
    private String memo;
    private Integer del;
}
