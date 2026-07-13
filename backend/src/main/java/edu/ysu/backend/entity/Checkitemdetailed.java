package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-08-31
 */
public class Checkitemdetailed implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cdid", type = IdType.AUTO)
    private Integer cdid;

    private String name;

    private String unit;

    private Double minrange;

    private Double maxrange;

    private String normalvalue;

    private String normalvaluestring;

    private Integer type;

    private Integer ciid;

    private String remarks;

    public Integer getCdid() {
        return cdid;
    }

    public void setCdid(Integer cdid) {
        this.cdid = cdid;
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
    public Integer getCiid() {
        return ciid;
    }

    public void setCiid(Integer ciid) {
        this.ciid = ciid;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Checkitemdetailed{" +
            "cdid=" + cdid +
            ", name=" + name +
            ", unit=" + unit +
            ", minrange=" + minrange +
            ", maxrange=" + maxrange +
            ", normalvalue=" + normalvalue +
            ", normalvaluestring=" + normalvaluestring +
            ", type=" + type +
            ", ciid=" + ciid +
            ", remarks=" + remarks +
        "}";
    }
}
