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
public class Customer {
    private Integer uid;
    private String name;
    private String gander;
    private Integer age;
    private String id;
    private String room;
    private String building;
    private String file;
    private String checkin;
    private String expiration;
    private String tel;
    private String family;
    private String accompany;
    private String assess;
    private String responsible;
    private String support;
    private String butler;
    private String memo;
    private String statue;
    private String label;
    private Integer value;
}
