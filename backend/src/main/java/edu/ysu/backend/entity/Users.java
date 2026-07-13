package edu.ysu.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-08-26
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userid")
    private String userid;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @TableField("password")
    private String credential;

    private String realname;

    private Integer sex;

    private String identitycard;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Integer usertype;
    @TableField(exist = false)
    private Usertype type;
    @TableField(exist = false)
    private Sex s;
    @TableField(exist = false)
    private  String token;

    public Usertype getType() {
        return type;
    }

    public void setType(Usertype type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Sex getS() {
        return s;
    }

    public void setS(Sex s) {
        this.s = s;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getPassword() {
        return credential;
    }

    public void setPassword(String password) {
        this.credential = password;
    }
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getIdentitycard() {
        return identitycard;
    }

    public void setIdentitycard(String identitycard) {
        this.identitycard = identitycard;
    }
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public Integer getUserType() {
        return usertype;
    }

    public void setUserType(Integer userType) {
        this.usertype = userType;
    }

    @Override
    public String toString() {
        return "Users{}";
    }
}
