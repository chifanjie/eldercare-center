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
 * @since 2023-08-31
 */
public class Checkitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ciid", type = IdType.AUTO)
    private Integer ciid;

    private String ciname;

    private String cicontent;

    private String meaning;

    private String remarks;

    @TableField(exist = false)
    private List<Checkitemdetailed> checkitemdetailed;

    public List<Checkitemdetailed> getCheckitemdetailed() {
        return checkitemdetailed;
    }

    public void setCheckitemdetailed(List<Checkitemdetailed> checkitemdetailed) {
        this.checkitemdetailed = checkitemdetailed;
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
    public String getCicontent() {
        return cicontent;
    }

    public void setCicontent(String cicontent) {
        this.cicontent = cicontent;
    }
    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Checkitem{" +
                "ciid=" + ciid +
                ", ciname='" + ciname + '\'' +
                ", cicontent='" + cicontent + '\'' +
                ", meaning='" + meaning + '\'' +
                ", remarks='" + remarks + '\'' +
                ", checkitemdetailed=" + checkitemdetailed +
                '}';
    }
}
