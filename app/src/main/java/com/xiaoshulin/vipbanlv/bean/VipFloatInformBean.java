package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**保存vip无广告功能*/
public class VipFloatInformBean implements Serializable {
    private String generalizeicon;

    private String generalizeurl;

    private String generalizelocation;

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


    @Override
    public String toString() {
        return "VipFloatInformBean{" +
                "generalizeicon='" + generalizeicon + '\'' +
                ", generalizeurl='" + generalizeurl + '\'' +
                '}';
    }
}
