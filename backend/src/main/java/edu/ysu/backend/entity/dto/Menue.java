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
public class Menue {
    @TableId(value = "mid",type = IdType.AUTO)
    private Integer mid;
    private String name;
    private String path;
    private Integer parent;
    private String memo;
    private Integer del;
    private String icon;
}
