package com.xiaoshulin.vipbanlv.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * CheckVisionBean
 *
 * @author jipeng
 * @date 2018/8/15
 */
public class CheckVisionBean implements Serializable{

    /**
     * returncode : -1
     * vision :
     * v-money :
     * v-type : 2
     * v-rank :
     * u-type :
     * an_versions : 1.0.3
     */

    private String returncode;
    private String vision;
    @SerializedName("v-money")
    private String vmoney;
    @SerializedName("v-type")
    private String vtype;  //ios需要的
    @SerializedName("v-rank")
    private String vrank;
    @SerializedName("u-type")
    private String utype;   //对应uidtoken
    private String an_versions;

    private String downloadlink;
    private String constraint;
    private String updateexplain;

    private String sunshinediscount;

    private String generalizeicon;

    private String generalizeurl;

    private String generalizelocation;


    public String getSunshinediscount() {
        return sunshinediscount;
    }

    public void setSunshinediscount(String sunshinediscount) {
        this.sunshinediscount = sunshinediscount;
    }

    public String getGeneralizeicon() {
        return generalizeicon;
    }

    public void setGeneralizeicon(String generalizeicon) {
        this.generalizeicon = generalizeicon;
    }

    public String getGeneralizeurl() {
        return generalizeurl;
    }

    public void setGeneralizeurl(String generalizeurl) {
        this.generalizeurl = generalizeurl;
    }

    public String getGeneralizelocation() {
        return generalizelocation;
    }

    public void setGeneralizelocation(String generalizelocation) {
        this.generalizelocation = generalizelocation;
    }

    public String getUpdateexplain() {
        return updateexplain;
    }

    public void setUpdateexplain(String updateexplain) {
        this.updateexplain = updateexplain;
    }

    public String getDownloadlink() {
        return downloadlink;
    }

    public void setDownloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getVmoney() {
        return vmoney;
    }

    public void setVmoney(String vmoney) {
        this.vmoney = vmoney;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public String getVrank() {
        return vrank;
    }

    public void setVrank(String vrank) {
        this.vrank = vrank;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getAn_versions() {
        return an_versions;
    }

    public void setAn_versions(String an_versions) {
        this.an_versions = an_versions;
    }
}
