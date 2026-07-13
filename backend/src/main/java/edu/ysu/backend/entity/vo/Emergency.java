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
public class Emergency {
    private Integer eid;
    private Integer uid;
    private String name;
    private String gander;
    private Integer age;
    private String relation;
    private String id;
    private String address;
    private String tel;
}
