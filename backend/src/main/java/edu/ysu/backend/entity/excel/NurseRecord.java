package edu.ysu.backend.entity.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class NurseRecord implements Serializable {

    @ColumnWidth(15)
    @ExcelProperty(value = "序号",index = 0)
    private Integer no;
    @ColumnWidth(15)
    @ExcelProperty(value = "老人姓名",index = 1)
    private String customerName;
    @ColumnWidth(15)
    @ExcelProperty(value = "护工姓名",index = 2)
    private String staffName;
    @ColumnWidth(30)
    @ExcelProperty(value = "护理服务名称",index = 3)
    private String serviceName;
    @ColumnWidth(30)
    @ExcelProperty(value = "护理内容",index = 4)
    private String serviceContent;
    @ColumnWidth(15)
    @ExcelProperty(value = "服务次数",index = 5)
    private Integer serviceTime;
    @ColumnWidth(30)
    @ExcelProperty(value = "提供服务时间",index = 6)
    private String serviceDate;

}
