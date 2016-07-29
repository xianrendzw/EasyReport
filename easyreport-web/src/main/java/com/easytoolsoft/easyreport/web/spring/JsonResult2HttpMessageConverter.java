package com.easytoolsoft.easyreport.web.spring;

import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * JsonResult<T> 序列化输出转换类
 *
 * @author Tom Deng
 */
public class JsonResult2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        if (object instanceof JsonResult) {
            super.writeInternal(object, outputMessage);
        } else {
            JsonResult<Object> jsonResult = new JsonResult<>(object);
            super.writeInternal(jsonResult, outputMessage);
        }
    }
}
