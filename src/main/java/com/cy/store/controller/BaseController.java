package com.cy.store.controller;

import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/** 控制层类的基类 */
public class BaseController {
    //操作成功状态码
    public static final int OK = 200;
    /*
    请求处理方法，这个方法的返回值就是需要传递给前端的数据
    自动将异常处理对象传递给此方法的参数列表上
    当项目中产生了异常，被统一拦截到此方法中，这个方法此时就充当的是请求处理方法，
        方法的返回值直接给到前端*/
    @ExceptionHandler({ServiceException.class,FileUploadException.class}) //用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        }else if (e instanceof InsertException ){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }else if ( e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户数据不存在");
        }else if ( e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("密码错误");
        }else if ( e instanceof UpdateException){
            result.setState(6000);
            result.setMessage("更新数据时产生未知异常");
        }else if ( e instanceof AddressNotFoundException){
            result.setState(6001);
            result.setMessage("地址不存在");
        }else if ( e instanceof AccessDeniedException){
            result.setState(6002);
            result.setMessage("非法访问");
        }else if ( e instanceof FileUploadIOException){
            result.setState(7000);
            result.setMessage("更新数据时产生未知异常");
        }else if ( e instanceof FileEmptyException){
            result.setState(7001);
            result.setMessage("更新数据时产生未知异常");
        }else if ( e instanceof FileSizeException){
            result.setState(7002);
            result.setMessage("更新数据时产生未知异常");
        }else if ( e instanceof FileStateException){
            result.setState(7003);
            result.setMessage("更新数据时产生未知异常");
        }else if ( e instanceof FileTypeException){
            result.setState(7004);
            result.setMessage("更新数据时产生未知异常");
        }else if ( e instanceof AddressCountLimitException){
            result.setState(8000);
            result.setMessage("用户收货地址上限");
        }else if ( e instanceof DeleteException){
            result.setState(9000);
            result.setMessage("删除时产生未知异常");
        }else if ( e instanceof ProductNotFoundException){
            result.setState(10001);
            result.setMessage("商品不存在");
        }
        return result;
    }
    //session 因为很多界面都需要用到用户数据，所以我们把用户登录后的数据存在服务器端
    //方便后续界面的搭建

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return  当前用户uid
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
