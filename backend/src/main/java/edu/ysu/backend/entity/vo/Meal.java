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
public class Meal {
    private Integer mid;
    private String name;
    private String food;
    private Integer num;
    private Integer tid;
    private Integer type;
    private Integer time;
    private double price;
    private Integer muslim;
    private String date;
    private Integer del;
    private String memo;
    private Integer week;
    private Integer beforeWeek;
    private Integer beforeTime;
    private String tagName;
    private String typeName;
    private Integer tagValue;
    private Integer typeValue;
    private String meal;
    private String position;
}
