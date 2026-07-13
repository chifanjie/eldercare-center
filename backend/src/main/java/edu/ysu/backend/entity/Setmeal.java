package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-08-27
 */
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "smid", type = IdType.AUTO)
    private Integer smid;

    private String name;

    private Integer type;

    private Integer price;

    @TableField(exist = false)
    private Sex sex;

    @TableField(exist = false)
    private List<Setmealdetailed> list;

    public List<Setmealdetailed> getList() {
        return list;
    }

    public void setList(List<Setmealdetailed> list) {
        this.list = list;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Setmeal{" +
                "smid=" + smid +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", sex=" + sex +
                ", list=" + list +
                '}';
    }
}
