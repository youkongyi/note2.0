package com.note.user.dao;

import com.note.entity.UserBO;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */
public interface IUserLoginDAO {
    /**
     * 根据账号查找用户信息
     * @param passport
     * @return
     */
    UserBO queryUserByPassport(String passport);


}
