package edu.ysu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ysu.backend.entity.dto.Checkout;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckoutMapper extends BaseMapper<Checkout> {
}
