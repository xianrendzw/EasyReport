package com.easytoolsoft.easyreport.web.spring;

import com.easytoolsoft.easyreport.web.viewmodel.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * JsonResult<T> 序列化输出转换类
 *
 * @author Tom Deng
 */
@Slf4j
public class JsonResult2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public JsonResult2HttpMessageConverter() {
        log.debug("load {}", this.getClass().getName());
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        if (object instanceof JsonResult) {
            super.writeInternal(object, type, outputMessage);
        } else {
            JsonResult<Object> jsonResult = new JsonResult<>(object);
            super.writeInternal(jsonResult, type, outputMessage);
        }
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        this.writeInternal(object, null, outputMessage);
    }
}
