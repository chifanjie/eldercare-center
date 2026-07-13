package edu.ysu.backend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MealManage {
    private Integer uid;
    private String habit;
    private String warn;
    private String memo;
    private String name;
    private String gander;
    private Integer age;
    private Integer cid;
    private List<Integer> mid;
}
