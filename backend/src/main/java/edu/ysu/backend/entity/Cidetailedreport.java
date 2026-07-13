package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @since   
 */
public class Cidetailedreport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cidrid", type = IdType.AUTO)
    private Integer cidrid;

    private String name;

    private String unit;

    private Double minrange;

    private Double maxrange;

    private String normalvalue;

    private String normalvaluestring;

    private Integer type;

    private String value;

    private Integer iserror;

    private Integer ciid;

    private Integer orderid;

    public Integer getCidrid() {
        return cidrid;
    }

    public void setCidrid(Integer cidrid) {
        this.cidrid = cidrid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    public Double getMinrange() {
        return minrange;
    }

    public void setMinrange(Double minrange) {
        this.minrange = minrange;
    }
    public Double getMaxrange() {
        return maxrange;
    }

    public void setMaxrange(Double maxrange) {
        this.maxrange = maxrange;
    }
    public String getNormalvalue() {
        return normalvalue;
    }

    public void setNormalvalue(String normalvalue) {
        this.normalvalue = normalvalue;
    }
    public String getNormalvaluestring() {
        return normalvaluestring;
    }

    public void setNormalvaluestring(String normalvaluestring) {
        this.normalvaluestring = normalvaluestring;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public Integer getIserror() {
        return iserror;
    }

    public void setIserror(Integer iserror) {
        this.iserror = iserror;
    }
    public Integer getCiid() {
        return ciid;
    }

    public void setCiid(Integer ciid) {
        this.ciid = ciid;
    }
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Override
    public String toString() {
        return "Cidetailedreport{" +
            "cidrid=" + cidrid +
            ", name=" + name +
            ", unit=" + unit +
            ", minrange=" + minrange +
            ", maxrange=" + maxrange +
            ", normalvalue=" + normalvalue +
            ", normalvaluestring=" + normalvaluestring +
            ", type=" + type +
            ", value=" + value +
            ", iserror=" + iserror +
            ", ciid=" + ciid +
            ", orderid=" + orderid +
        "}";
    }
}
