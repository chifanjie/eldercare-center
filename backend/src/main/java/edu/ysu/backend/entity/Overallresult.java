package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-09-03
 */
public class Overallresult implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "orid", type = IdType.AUTO)
    private Integer orid;

    private String title;

    private String content;

    private Integer orderid;

    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOrid() {
        return orid;
    }

    public void setOrid(Integer orid) {
        this.orid = orid;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Override
    public String toString() {
        return "Overallresult{" +
                "orid=" + orid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", orderid=" + orderid +
                ", state=" + state +
                '}';
    }
}
