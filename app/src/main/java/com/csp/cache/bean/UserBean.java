package com.csp.cache.bean;

/**
 * Created by kjt on 2019-08-30
 */
public class UserBean {

    /**
     * cellPhone : 15612340022
     * email : 12345682@qq.com
     * idCardNo : 1100***********222
     * gender : 男
     * nationality : 汉
     * birthday : 1995-05-05
     * address : 北京市西城区
     * name : 爱新觉罗
     */

    private String cellPhone;
    private String email;
    private String idCardNo;
    private String gender;
    private String nationality;
    private String birthday;
    private String address;
    private String name;

    public String getCellPhone() {
        return cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name：" + name + "\ngender：" + gender + "\nnationality：" + nationality + "\nidCardNo：" + idCardNo + "\nbirthday：" + birthday + "\ncellPhone：" + cellPhone + "\naddress：" + address + "\nemail：" + email;
    }
}
