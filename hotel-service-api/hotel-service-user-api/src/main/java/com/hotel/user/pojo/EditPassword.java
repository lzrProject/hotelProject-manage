package com.hotel.user.pojo;

public class EditPassword {
    /**
     * 旧密码
     * */
    private String oldPassword;

    /**
     * 新密码
     * */
    private String newPassword;

    /**
     * 确认密码
     * */
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
