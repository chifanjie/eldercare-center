package edu.ysu.backend.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class NurseRecord {
    private Integer nid;
    private Integer sid;
    private Integer uid;
    private Integer cid;
    private Integer time;
    private String date;
    private String memo;
    private String customerName;
    private String staffName;
    private String contentName;
    private List<Integer> uidList;
}
