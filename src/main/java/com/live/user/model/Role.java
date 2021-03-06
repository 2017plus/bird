package com.live.user.model;

public class Role {
    /** serialVersionUID. */
    private static final long serialVersionUID =1493049839167L;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.role_id
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.role_name
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.role_type
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    private Integer roleType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.role_id
     *
     * @return the value of role.role_id
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.role_id
     *
     * @param roleId the value for role.role_id
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.role_name
     *
     * @return the value of role.role_name
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.role_name
     *
     * @param roleName the value for role.role_name
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.role_type
     *
     * @return the value of role.role_type
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public Integer getRoleType() {
        return roleType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.role_type
     *
     * @param roleType the value for role.role_type
     *
     * @mbg.generated Fri Oct 12 16:12:19 CST 2018
     */
    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}