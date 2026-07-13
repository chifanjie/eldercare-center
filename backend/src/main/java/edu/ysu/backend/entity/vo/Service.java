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
public class Service {
    private Integer sid;
    private Integer uid;
    private Integer cid;
    private Integer last;
    private Integer num;
    private Integer residue;
    private Integer frequency;
    private String buy;
    private Integer statue;
    private String memo;
    private String customerName;
    private String contentName;
}
