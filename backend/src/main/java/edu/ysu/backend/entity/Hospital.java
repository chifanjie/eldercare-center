package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-08-25
 */
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "hpid", type = IdType.AUTO)
    private Integer hpid;

    private String name;

    private String picture;

    private String telephone;

    private String address;

    private String businesshours;

    private String deadline;

    private String rule;

    private Integer state;

    public Integer getHpid() {
        return hpid;
    }

    public void setHpid(Integer hpid) {
        this.hpid = hpid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getBusinesshours() {
        return businesshours;
    }

    public void setBusinesshours(String businesshours) {
        this.businesshours = businesshours;
    }
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Hospital{" +
            "hpid=" + hpid +
            ", name=" + name +
            ", picture=" + picture +
            ", telephone=" + telephone +
            ", address=" + address +
            ", businesshours=" + businesshours +
            ", deadline=" + deadline +
            ", rule=" + rule +
            ", state=" + state +
        "}";
    }
}
