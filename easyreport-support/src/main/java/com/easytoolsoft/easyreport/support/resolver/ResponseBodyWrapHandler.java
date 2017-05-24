package com.easytoolsoft.easyreport.support.resolver;

import com.easytoolsoft.easyreport.support.model.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Tom Deng
 * @date 2017-04-26
 **/
public class ResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {
    private final String basePackage;
    private final HandlerMethodReturnValueHandler delegate;

    public ResponseBodyWrapHandler(final HandlerMethodReturnValueHandler delegate, final String basePackage) {
        this.delegate = delegate;
        this.basePackage = basePackage;
    }

    @Override
    public boolean supportsReturnType(final MethodParameter returnType) {
        return this.delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, final MethodParameter returnType,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest) throws Exception {
        if (this.isWrapReturnValue(returnValue, returnType)) {
            returnValue = ResponseResult.success(returnValue);
        }
        this.delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }

    /**
     * 如果returnValue为ResponseResult类型
     * 或 basePackage为空
     * 或 不是调用类的前辍
     * 则返回false(不需要包装@ResponseBody返回值)
     *
     * @param returnValue 返回值
     * @param returnType  返回值类型
     * @return true|false
     */
    private boolean isWrapReturnValue(final Object returnValue, final MethodParameter returnType) {
        if (returnValue instanceof ResponseResult) {
            return false;
        }
        if (StringUtils.isBlank(this.basePackage)) {
            return false;
        }
        final String declaringClazzName = returnType.getMethod().getDeclaringClass().getName();
        return StringUtils.startsWith(declaringClazzName, this.basePackage);
    }
}