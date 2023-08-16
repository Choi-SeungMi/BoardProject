package com.seungmi.openBoard.controller;

import com.seungmi.openBoard.dao.UserDao;
import com.seungmi.openBoard.domain.UserDto;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/account")
public class UserController {

    UserDao userDao;
    JavaMailSender mailSender;

    final int FAIL = 0;

    UserController(UserDao userDao, JavaMailSender mailSender){
        this.userDao = userDao;
        this.mailSender = mailSender;
    }

    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
    }

    @GetMapping("/confirm")
    public String selectUser(Model m, HttpSession session){
        String id = (String) session.getAttribute("id");

        try {
            UserDto userDto = userDao.selectUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "userConfirmForm";
    }

    @PostMapping("/confirm")
    public String confirmPwd(String id, String pwd, Model m){
        try {

            UserDto userDto = userDao.selectUser(id);
            String userPwd = userDto.getPwd().replaceAll("\\s+","");

            if(!pwd.equals(userPwd)){
                String msg = URLEncoder.encode("비밀번호가 일치하지 않습니다.", "utf-8");
                return "redirect:/account/confirm?msg="+msg;
            }

            m.addAttribute("userDto", userDto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "userInfo";

    }

    @PostMapping("/usermodify")
    public String modify(@Valid UserDto userDto, Model m){

        try {
            int rowCnt = userDao.updateUser(userDto);

                if(rowCnt==FAIL)
                    throw new Exception("Modify failed");

                m.addAttribute("resultMsg", "MOD_OK");
                    return "index";

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(userDto);
            m.addAttribute("resultMsg", "MOD_ERR");

            return "userInfo";
        }
    }
    @GetMapping("/finduser")
    public String findUser(){
        return "findInfoForm";
    }
    @PostMapping("/finduser")
    public String findUser(String email, Model m){
        int code = (int)(Math.random()*(99999-10000+1)+10000);
        String insertCode = code+"";
        System.out.println("insertCode = " + insertCode);

        try {
            UserDto userDto = userDao.selectUserByEmail(email);
            userDto.setPwd(insertCode);
            userDao.updateUser(userDto);
            System.out.println("user = " + userDto);

            if(null != userDto){

                String title = "[openboard] 계정찾기";
                String to = email; // 수신주소
                String from = "openboard82@gmail.com"; //발신주소
                String content = "<h2>안녕하세요 openboard 입니다.</h2><br>" +
                        "<p>아래의 임시비밀번호를 사용하여 비밀번호를 변경해주세요.</p><br>" +
                        "<h3>아이디 : "+ userDto.getId()+"</h3>" +
                        "<h3>임시비밀번호 :" + code +
                        "<p><a href=http://localhost/openboard/>여기</a>를 누르면 오픈보드로 이동합니다.</p>";

                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

                mailHelper.setFrom(from);

                mailHelper.setTo(to);
                mailHelper.setSubject(title);
                mailHelper.setText(content, true);

                mailSender.send(mail);

                m.addAttribute("id", userDto.getId());
                m.addAttribute("email", email);
                m.addAttribute("emailCheckMsg","FIND_OK");
                return "findInfoForm";
            }
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("emailCheckMsg","FIND_ERR");
            return "findInfoForm";
        }
        m.addAttribute("email", email);
        m.addAttribute("emailCheckMsg","FIND_ERR");
        return "findInfoForm";
    }

}
