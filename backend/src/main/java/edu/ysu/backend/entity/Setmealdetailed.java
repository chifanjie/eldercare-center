package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-08-30
 */
public class Setmealdetailed implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sdid", type = IdType.AUTO)
    private Integer sdid;

    private Integer smid;

    private Integer ciid;

    @TableField(exist = false)
    private Checkitem checkitem;

    public Checkitem getCheckitem() {
        return checkitem;
    }

    public void setCheckitem(Checkitem checkitem) {
        this.checkitem = checkitem;
    }

    public Integer getSdid() {
        return sdid;
    }

    public void setSdid(Integer sdid) {
        this.sdid = sdid;
    }
    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }
    public Integer getCiid() {
        return ciid;
    }

    public void setCiid(Integer ciid) {
        this.ciid = ciid;
    }

    @Override
    public String toString() {
        return "Setmealdetailed{" +
                "sdid=" + sdid +
                ", smid=" + smid +
                ", ciid=" + ciid +
                ", checkitem=" + checkitem +
                '}';
    }
}
