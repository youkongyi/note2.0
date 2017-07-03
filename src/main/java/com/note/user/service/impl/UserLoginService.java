package com.note.user.service.impl;

import com.note.consts.CommonConsts;
import com.note.consts.UserConsts;
import com.note.entity.UserBO;
import com.note.user.dao.IUserLoginDAO;
import com.note.user.service.IUserLoginService;
import com.note.utils.JsonResult;
import com.note.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/29.
 */
@Service("userLoginService")
public class UserLoginService implements IUserLoginService {
    @Resource(name="IUserLoginDAO")
    private IUserLoginDAO userLoginDAO;

    public JsonResult queryUserByPassport(UserBO userBO) {
        JsonResult result = new JsonResult();
        String passport = userBO.getPassport();
        String password = userBO.getPassword();
        UserBO userbo = userLoginDAO.queryUserByPassport(passport);

        if(StringUtils.isNull(userbo)){
            result.setResultState(CommonConsts.STATE_FAILED_ERROR);
            result.setResultMessage(UserConsts.USER_NOT_EXIST_MESSAGE);
            return result;
        }
        if(password.equals(userbo.getPassword())){
            if(UserConsts.USER_STATE_DISABLE.equals(userbo.getState())){
                result.setResultState(CommonConsts.STATE_FAILED_ERROR);
                result.setResultMessage(UserConsts.USER_STATE_DISABLE_MESSAGE);
                return result;
            }

            if(UserConsts.USER_STATE_ENABLED.equals(userbo.getState())){
                result.setResultData(userbo);
                result.setResultState(CommonConsts.STATE_SUCCESS);
                result.setResultMessage(UserConsts.USER_SUCCESS_MESSAGE);
                return result;
            }
        } else {
            result.setResultState(CommonConsts.STATE_FAILED_ERROR);
            result.setResultMessage(UserConsts.USER_PASSWORD_ERROR_MESSAGE);
        }
        return result;
    }
}
