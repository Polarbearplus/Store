package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
//相当于 @Controller 以及在每一个方法上加一个
//@RequestBody  //表示此方法的响应结果以json格式进行数据的响应给前端
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;
    /**
     * 1、接收数据方式：请求处理方法的参数列表设置为pojo类型来接受前端的数据
     * springboot会将前端的url地址中的参数名和pojo类的属性名进行比较，
     * 名称相同自动注入pojo类中相应的属性上
     * 2、接收数据方式：请求处理方法的参数列表设置为非pojo类型
     * springboot直接将请求的参数名和方法的参数名直接进行比较，如果名称相同
     * 则自动完成值的注入
     */

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session) {
        User data = userService.login(username,password);
        //向session对象中完成数据绑定
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        //System.out.println(data);
        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session){
        userService.changePassword(getUidFromSession(session),
                getUsernameFromSession(session),
                oldPassword,
                newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session){
        userService.changeInfo(user,
                getUsernameFromSession(session),
                getUidFromSession(session));
    return new JsonResult<>(OK);
    }
    //SpringMVC 提供MultipartFile接口传递文件
    // 它可以接受任何类型文件
    //@RequestParam("f") MultipartFile file解决前后端参数名不一致 把表单中名为f
    // 的数据扔给file

    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;//文件大小上限
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("images/jpeg");
        AVATAR_TYPE.add("images/png");
        AVATAR_TYPE.add("images/bmp");
        AVATAR_TYPE.add("images/gif");
    }//文件类型

    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           MultipartFile file){
        //判断是否为空
        if ( file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if ( file.getSize() > AVATAR_MAX_SIZE ) {
            throw new FileSizeException("文件超出限制");
        }
        if ( AVATAR_TYPE.contains(file.getContentType()) ) {
            throw new FileTypeException("文件类型不支持");
        }
        //上传的文件 : .../upload/文件.png

        String parent =
                "D:/IDEA/Workplace/store/src/main/resources/static/upload";
        //File对象指向这个路径
        //System.out.println(parent);
        File dir = new File(parent);
        if(!dir.exists()){ //检查目录是否存在，不存在创建新的目录
            dir.mkdirs();
        }
        //获取文件名称 UUID工具类生成一个新的字符串作为文件名
        // （用户上传文件名相同就会覆盖）
        String originalFilename = file.getOriginalFilename();
        //System.out.println("oldFilename="+originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);//存储后缀 因为上边获取文件名的时候 后缀也会存在string中
        String filename = UUID.randomUUID().toString().toUpperCase()
                + suffix;
        //System.out.println("newFilename="+filename);
        File dest = new File(dir, filename);//创建一个空文件
        //参数file中的数据写入上一步创建的文件中（前提是文件后缀一致）
        try {
            file.transferTo(dest);
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw  new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //头像路径 /upload/....png
        String avatar = "../upload/" +filename;
        userService.updateAvatarByUid(uid,avatar,username);
        //System.out.println(avatar);
        //返回用户头像用于展示
        return new JsonResult<>(OK,avatar);
    }
    /**
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();

        try{
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e ) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e ) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        /**
         * 上述方法实在过于麻烦，要在每一个方法中都判断异常  所以 我们抽离出一个父类
        return result;
    }
    */
}
