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
 * @since 2023-09-05
 */
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "docid", type = IdType.AUTO)
    private Integer docid;

    private String doccode;

    private String realname;

    @TableField("password")
    private String credential;

    private String sex;

    private String deptno;
    private Integer state;
    @TableField(exist = false)
    private  String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }
    public String getDoccode() {
        return doccode;
    }

    public void setDoccode(String doccode) {
        this.doccode = doccode;
    }
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getPassword() {
        return credential;
    }

    public void setPassword(String password) {
        this.credential = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    @Override
    public String toString() {
        return "Doctor{}";
    }
}
