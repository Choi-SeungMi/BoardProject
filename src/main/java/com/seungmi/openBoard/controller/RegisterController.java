package com.seungmi.openBoard.controller;


import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import com.seungmi.openBoard.dao.UserDao;
import com.seungmi.openBoard.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    UserDao userDao;
    JavaMailSender mailSender;

    RegisterController(UserDao userDao, JavaMailSender mailSender){
        this.userDao = userDao;
        this.mailSender = mailSender;
    }

    final int FAIL = 0;
    private int code = 0;

    @InitBinder
    //RegisterForm데이터를 User객체에 바인딩
    public void toDate(WebDataBinder binder) {
        //날짜형식 타입변환
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
    }

    @GetMapping("/add")
    public String register() {
        return "registerForm"; // WEB-INF/views/registerForm.jsp
    }

    @PostMapping("/add")
    //@Valid로 User객체에 RegisterForm의 Form 데이터가 담길 수 있는데 @InitBinder 때문에 가능.
    public String save(@Valid UserDto userDto, BindingResult result, Model m) {


        try {
            // User객체를 검증한 결과 에러가 있으면, registerForm을 이용해서 에러를 보여줘야 함.
            if(!result.hasErrors()) {
                // DB에 신규회원 정보를 저장
                if(null != userDao.selectUserByEmail(userDto.getEmail())){
                    m.addAttribute(userDto);
                    m.addAttribute("resultMsg", "REG_EMAIL_ERR");
                    return "registerForm";
                }
                int rowCnt = userDao.insertUser(userDto);

                if(rowCnt!=FAIL) {
                    m.addAttribute("resultMsg", "REG_OK");
                    return "index";
                }
            }
        }catch (SQLIntegrityConstraintViolationException e){
            m.addAttribute("user", userDto);


        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        m.addAttribute(userDto);
        m.addAttribute("resultMsg", "REG_ERR");
        return "registerForm";
    }

    @GetMapping("/idcheck")
    public String idCheck(String id, Model m){

        System.out.println("id = " + id);
        
        try {
            if(null != userDao.selectUser(id)){
                System.out.println("userDao.selectUser(id) = " + userDao.selectUser(id));
                m.addAttribute("id", id);
                m.addAttribute("idCheckMsg","false");
                return "idCheckForm";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        m.addAttribute("id", id);
        m.addAttribute("idCheckMsg","true");
        return "idCheckForm";
    }

    @PostMapping("/emailcheck")
    public ResponseEntity<String> sendEmail(String email){

        code = (int)(Math.random()*(99999-10000+1)+10000);
        String title = "[openboard] 이메일 주소 확인을 위한 인증 코드";
        String to = email; // 수신주소
        String from = "openboard82@gmail.com"; //발신주소
        String content = "<h2>안녕하세요 openboard 입니다.</h2><br>" +
                "<p>아래의 인증번호로 회원가입을 완료해주세요.</p><br>" +
                "<h3>인증번호 : "+code+"</h3>" ;

        try {

            if (null != userDao.selectUserByEmail(email))
                return new ResponseEntity<>("EMAIL_ALREADY", HttpStatus.BAD_REQUEST);

                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
                // true는 멀티파트 메세지를 사용하겠다는 의미

                /*
                 * 단순한 텍스트 메세지만 사용시엔 아래의 코드도 사용 가능
                 * MimeMessageHelper mailHelper = new MimeMessageHelper(mail,"UTF-8");
                 */

                mailHelper.setFrom(from);
                // 빈에 아이디 설정한 것은 단순히 smtp 인증을 받기 위해 사용 따라서 보내는이(setFrom())반드시 필요
                // 보내는이와 메일주소를 수신하는이가 볼때 모두 표기 되게 하려면
                //mailHelper.setFrom("보내는이 이름 <보내는이 아이디@도메인주소>");
                mailHelper.setTo(to);
                mailHelper.setSubject(title);
                mailHelper.setText(content, true);
                // true는 html을 사용하겠다는 의미

                /*
                 * 단순한 텍스트만 사용 mailHelper.setText(content);
                 */

                mailSender.send(mail);

                return new ResponseEntity<>("EMAIL_OK", HttpStatus.OK);

            } catch(Exception e){
                e.printStackTrace();
                return new ResponseEntity<>("EMAIL_ERR", HttpStatus.BAD_REQUEST);
            }
        }


}
