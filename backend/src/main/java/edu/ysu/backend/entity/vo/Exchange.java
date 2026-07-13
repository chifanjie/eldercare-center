package edu.ysu.backend.entity.vo;

import edu.ysu.backend.entity.dto.Building;
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
public class Exchange {
    private String before;
    private String after;
    private String set;
    private String building;
    private edu.ysu.backend.entity.dto.Building beforeData;
    private edu.ysu.backend.entity.dto.Building afterData;
    private Building setData;
}
