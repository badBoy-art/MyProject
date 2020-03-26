package com.GsonFormat;

/**
 * @author xuedui.zhao
 * @create 2018-09-07
 */
public class GsonFormatTest {
    
    /**
     * code : 0
     * remark : 操作成功
     * result : {"userId":3,"userName":"3","userPassword":"ab88c283bf439943eb143c45adf2cde4","userSalt":"861","userType":"station_user","userStatus":1,"companyId":1,"menus":"","permissions":""}
     */
    
    private int code;
    private String remark;
    private ResultBean result;
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public ResultBean getResult() {
        return result;
    }
    
    public void setResult(ResultBean result) {
        this.result = result;
    }
    
    public static class ResultBean {
        /**
         * userId : 3
         * userName : 3
         * userPassword : ab88c283bf439943eb143c45adf2cde4
         * userSalt : 861
         * userType : station_user
         * userStatus : 1
         * companyId : 1
         * menus :
         * permissions :
         */
        
        private int userId;
        private String userName;
        private String userPassword;
        private String userSalt;
        private String userType;
        private int userStatus;
        private int companyId;
        private String menus;
        private String permissions;
        
        public int getUserId() {
            return userId;
        }
        
        public void setUserId(int userId) {
            this.userId = userId;
        }
        
        public String getUserName() {
            return userName;
        }
        
        public void setUserName(String userName) {
            this.userName = userName;
        }
        
        public String getUserPassword() {
            return userPassword;
        }
        
        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }
        
        public String getUserSalt() {
            return userSalt;
        }
        
        public void setUserSalt(String userSalt) {
            this.userSalt = userSalt;
        }
        
        public String getUserType() {
            return userType;
        }
        
        public void setUserType(String userType) {
            this.userType = userType;
        }
        
        public int getUserStatus() {
            return userStatus;
        }
        
        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }
        
        public int getCompanyId() {
            return companyId;
        }
        
        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }
        
        public String getMenus() {
            return menus;
        }
        
        public void setMenus(String menus) {
            this.menus = menus;
        }
        
        public String getPermissions() {
            return permissions;
        }
        
        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }
    }
}
