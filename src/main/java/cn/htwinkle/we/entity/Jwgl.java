package cn.htwinkle.we.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author twinkle
 * @since 2019-05-01
 */
public class Jwgl extends Model<Jwgl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer code;

    private String admin;

    private String pass;

    private String name;

    private String cookies;

    private String veri_code;

    private String tip;

    private LocalDateTime dates;


    public String getVeri_code() {
        return veri_code;
    }

    public Jwgl setVeri_code(String veri_code) {
        this.veri_code = veri_code;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Jwgl setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Jwgl setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getAdmin() {
        return admin;
    }

    public Jwgl setAdmin(String admin) {
        this.admin = admin;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public Jwgl setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public String getName() {
        return name;
    }

    public Jwgl setName(String name) {
        this.name = name;
        return this;
    }

    public String getCookies() {
        return cookies;
    }

    public Jwgl setCookies(String cookies) {
        this.cookies = cookies;
        return this;
    }

    public String getTip() {
        return tip;
    }

    public Jwgl setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public LocalDateTime getDates() {
        return dates;
    }

    public Jwgl setDates(LocalDateTime dates) {
        this.dates = dates;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Jwgl{" +
                "id=" + id +
                ", code=" + code +
                ", admin=" + admin +
                ", pass=" + pass +
                ", name=" + name +
                ", cookies=" + cookies +
                ", tip=" + tip +
                ", dates=" + dates +
                "}";
    }
}
