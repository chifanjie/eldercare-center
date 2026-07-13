package edu.ysu.backend.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Egress {
    private Integer eid;
    private Integer uid;
    private String reason;
    private String egress;
    private String predict;
    private String really;
    private Integer id;
    private String relation;
    private String tel;
    private Integer statue;
    private Integer approver;
    private String approve;
    private String memo;
    private String name;
    private String gander;
    private Integer age;
    private String file;
    private String relationName;
    private String approverName;
}
