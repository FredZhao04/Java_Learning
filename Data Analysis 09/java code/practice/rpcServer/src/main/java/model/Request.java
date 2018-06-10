package model;

import com.sun.tools.javac.util.RichDiagnosticFormatter;

import java.io.Serializable;

/**
 * Created by fred on 2018/6/10.
 */
public class Request implements Serializable{
    private String className;
    private String methodName;
    private Class[] parameterType;
    private Object[] parameterValues;

    public Request() {
    }

    public Request(String className, String methodName, Class[] parameterType, Object[] parameterValues) {
        this.className = className;
        this.methodName = methodName;
        this.parameterType = parameterType;
        this.parameterValues = parameterValues;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class[] parameterType) {
        this.parameterType = parameterType;
    }

    public Object[] getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(Object[] parameterValues) {
        this.parameterValues = parameterValues;
    }
}
