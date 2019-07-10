package cn.edu.mju.web.servlet;

import cn.edu.mju.utils.ValidateCodeUtils;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/validateCodeServlet")
public class ValidateCodeServlet extends BaseServlet {

    protected void validateCodeServlet(HttpServletRequest request, HttpServletResponse response) {
        //制作验证码
        ValidateCodeUtils.makeCheckCode();
        //获取验证码图片
        BufferedImage image = ValidateCodeUtils.getImage();
        //获取验证码
        String validateCode = ValidateCodeUtils.getValidateCode();
        request.getSession().setAttribute("validateCode",validateCode);
        try {
            //将完整验证码图片响应给客户端
            ImageIO.write(image,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
