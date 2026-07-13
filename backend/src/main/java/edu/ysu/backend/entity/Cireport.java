package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-09-01
 */
public class Cireport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cirid", type = IdType.AUTO)
    private Integer cirid;

    private Integer ciid;

    private String ciname;

    private Integer orderid;

    public Integer getCirid() {
        return cirid;
    }

    public void setCirid(Integer cirid) {
        this.cirid = cirid;
    }
    public Integer getCiid() {
        return ciid;
    }

    public void setCiid(Integer ciid) {
        this.ciid = ciid;
    }
    public String getCiname() {
        return ciname;
    }

    public void setCiname(String ciname) {
        this.ciname = ciname;
    }
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Override
    public String toString() {
        return "Cireport{" +
            "cirid=" + cirid +
            ", ciid=" + ciid +
            ", ciname=" + ciname +
            ", orderid=" + orderid +
        "}";
    }
}
