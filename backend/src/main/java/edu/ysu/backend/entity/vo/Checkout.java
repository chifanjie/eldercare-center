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
public class Checkout {
    private Integer cid;
    private Integer uid;
    private String checkout; // 退住时间
    private String reason; // 退住原因
    private String apply; // 申请时间
    private String comment; // 审核意见
    private Integer reviewer; // 审核人id
    private String review; // 审核时间
    private String memo; // 备注
    private Integer type; // 退住类型
    private Integer statue; // 状态
    private String name;
    private String gander;
    private Integer age;
    private String file;
    private String checkin;
    private String reviewerName;
}
