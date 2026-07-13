package edu.ysu.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class LevelContentContrast {
    private Integer nid;
    private Integer cid;
    private Integer cycle;
    private Integer frequency;
    private Integer sort;
    private String memo;
    @TableField(exist = false)
    private String content;
    @TableField(exist = false)
    private String level;
}
