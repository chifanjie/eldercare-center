package edu.ysu.backend.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ExcelUtil {

    public static void exportExcel(HttpServletResponse response,Class<?> clazz,List<?> data){
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition","attachment;filename=nurse_record.xlsx");
        ExcelWriterBuilder writeWork = null;
        try {
            writeWork = EasyExcel.write(response.getOutputStream(), clazz);
            ExcelWriterSheetBuilder sheet = writeWork.sheet();
            sheet.doWrite(data);
            log.info("导出xlsx成功");
        } catch (IOException e) {
            log.error("生成Excel失败");
        }
    }

}
