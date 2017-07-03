package com.note.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/6/29.
 */
@Setter
@Getter
public class UserBO {
    /**
     * 用户ID
     */
    private String id;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 账号
     */
    private String passport;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话
     */
    private String telPhone;
    /**
     * 创建日期
     */
    private String createDate;
    /**
     * 最近更新日期
     */
    private String changeDate;
    /**
     * 更新次数
     */
    private String changeNum;
    /**
     * 状态 ( 0: 启动 1: 停用 )
     */
    private String state;
}
