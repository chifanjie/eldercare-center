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
public class Content {
    private Integer cid;
    private String no;
    private String name;
    private String price;
    private String description;
    private String value;
    private String statue;
    private String memo;
    private String del;
}
