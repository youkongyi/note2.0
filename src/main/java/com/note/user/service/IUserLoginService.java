package com.note.user.service;

import com.note.entity.UserBO;
import com.note.utils.JsonResult;

/**
 * Created by Administrator on 2017/6/29.
 */
public interface IUserLoginService {
    JsonResult queryUserByPassport(UserBO userBO);
}
