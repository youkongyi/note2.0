package com.note.user.controller.impl;
import com.note.consts.CommonConsts;
import com.note.entity.UserBO;
import com.note.user.controller.IUserLoginController;
import com.note.user.service.IUserLoginService;
import com.note.utils.BeanUtils;
import com.note.utils.JsonResult;
import com.note.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29.
 */
@Controller
@RequestMapping("user")
public class UserLoginController implements IUserLoginController {
    @Resource(name="userLoginService")
    private IUserLoginService userLoginService;
    /**
     * 用户登录
     * @param paraMap
     * @return
     */
    @RequestMapping(value = "/queryUserByPassport/{passport}",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult queryUserByPassport(@RequestParam Map<Object, Object> paraMap) {

        JsonResult result = new JsonResult();
        String passport = String.valueOf(paraMap.get("passport"));
        String password = String.valueOf(paraMap.get("password"));
        if(StringUtils.isNull(passport) || StringUtils.isNull(password)){
            result.setResultState(CommonConsts.STATE_PARAMETER_ERROR);
            result.setResultMessage(CommonConsts.STATE_PARAMETER_ERROR_MESSAGE);
            return result;
        }

        try {
            UserBO userBo = (UserBO) BeanUtils.reflectionCopyJavaBean(paraMap,UserBO.class);
            result = userLoginService.queryUserByPassport(userBo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultState(CommonConsts.STATE_FAILED_ERROR);
            result.setResultMessage(CommonConsts.STATE_FAILED_ERROR_MESSAGE);
        }
        return result;
    }

}
