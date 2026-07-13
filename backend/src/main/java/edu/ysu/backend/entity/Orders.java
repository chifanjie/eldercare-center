package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-08-27
 */
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "orderid", type = IdType.AUTO)
    private Integer orderid;

    private LocalDate orderDate;

    private String userid;

    private Integer hpid;

    private Integer smid;

    private Integer state;
    @TableField(exist = false)
    private Users users;
    @TableField(exist = false)
    private Hospital hospital;

    @TableField(exist = false)
    private Setmeal setmeal;

    public Setmeal getSetmeal() {
        return setmeal;
    }

    public void setSetmeal(Setmeal setmeal) {
        this.setmeal = setmeal;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public Integer getHpid() {
        return hpid;
    }

    public void setHpid(Integer hpid) {
        this.hpid = hpid;
    }
    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderid=" + orderid +
                ", orderDate=" + orderDate +
                ", userid='" + userid + '\'' +
                ", hpid=" + hpid +
                ", smid=" + smid +
                ", state=" + state +
                ", users=" + users +
                ", hospital=" + hospital +
                ", setmeal=" + setmeal +
                '}';
    }
}
