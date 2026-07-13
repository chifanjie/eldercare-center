package edu.ysu.backend.entity.vo;

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
public class LevelContent {
    private Integer nid;
    private Integer cid;
    private Integer cycle;
    private Integer frequency;
    private Integer sort;
    private String memo;
    private Integer defaultId;
}
