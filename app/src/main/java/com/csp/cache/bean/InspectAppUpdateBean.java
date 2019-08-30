package com.csp.cache.bean;

import java.io.Serializable;

/**
 * Created by kjt on 2019/3/27
 */
public class InspectAppUpdateBean implements Serializable {


    /**
     * installPackage : www.xxx.com
     * updateType : UN_FORCE
     * lastVersion : 2.0.1.2
     * message :
     * needUpdate : true
     * updateContent : 更新内容XXX
     */

    private String lastVersion;
    private String message;
    private boolean needUpdate;
    private String updateContent;
    private String installPackage;
    private String updateType;

    public String getLastVersion() {
        return lastVersion;
    }

    public String getMessage() {
        return message;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public String getInstallPackage() {
        return installPackage;
    }

    public String getUpdateType() {
        return updateType;
    }

    @Override
    public String toString() {
        return "installPackage：" + installPackage + "\nlastVersion：" + lastVersion + "\nmessage：" + message + "\nneedUpdate：" + needUpdate + "\nupdateContent：" + updateContent + "\nupdateType：" + updateType;
    }
}
