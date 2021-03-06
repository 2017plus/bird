package com.live.user.model;

public class Permission {
    /** serialVersionUID. */
    private static final long serialVersionUID =1493049839167L;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_id
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    private Integer permissionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_name
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    private String permissionName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_action
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    private String permissionAction;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_id
     *
     * @return the value of permission.permission_id
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_id
     *
     * @param permissionId the value for permission.permission_id
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_name
     *
     * @return the value of permission.permission_name
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_name
     *
     * @param permissionName the value for permission.permission_name
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_action
     *
     * @return the value of permission.permission_action
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public String getPermissionAction() {
        return permissionAction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_action
     *
     * @param permissionAction the value for permission.permission_action
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public void setPermissionAction(String permissionAction) {
        this.permissionAction = permissionAction == null ? null : permissionAction.trim();
    }
}