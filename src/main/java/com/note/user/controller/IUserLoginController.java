package com.note.user.controller;

import com.note.utils.JsonResult;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/29.
 */
public interface IUserLoginController {
    /**
     * 用户登录功能接口
     * @param paraMap
     * @return
     */
    JsonResult queryUserByPassport(Map<Object, Object> paraMap);
}
