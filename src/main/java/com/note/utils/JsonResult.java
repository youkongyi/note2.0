package com.note.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/6/29.
 */

@Getter
@Setter
public class JsonResult {
    /**
     * 状态码
     */
    private int resultState;
    /**
     * 状态消息
     */
    private String resultMessage;
    /**
     * 返回对象
     */
    private Object resultData;
}
