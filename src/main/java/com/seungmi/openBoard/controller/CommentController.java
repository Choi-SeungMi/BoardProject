package com.seungmi.openBoard.controller;

import com.seungmi.openBoard.domain.CommentDto;
import com.seungmi.openBoard.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@ResponseBody 메서드들 마다 붙여줘야 하는 @ResponseBody를 클래스에 붙여주면 모든 메서드에 붙이는 것과 같음
//@Controller
@RestController //@ResponseBody + @Controller
public class CommentController {

    CommentService service;

    CommentController(CommentService service){
        this.service = service;
    }

    //댓글을 수정하는 메서드
    @PatchMapping("/comments/{cno}")   //openboard/comments/1 (PATCH)
    //Post방식으로 JSON으로 요청된 내용을 자바 객체로 받기 위해 @RequestBody 사용
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session){

        //session에서 ID를 가져온다.
        String commenter = (String)session.getAttribute("id");

        dto.setCommenter(commenter);

        dto.setCno(cno);

        System.out.println("dto = " + dto);

        try {
            if(service.modify(dto)!=1)
                throw new Exception("Modify failed.");
            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    //댓글을 등록하는 메서드
    @PostMapping("/comments")   //openboard/comments?bno=1085 (POST)
    //Post방식으로 JSON으로 요청된 내용을 자바 객체로 받기 위해 @RequestBody 사용
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session){
        String commenter = (String)session.getAttribute("id");

        dto.setCommenter(commenter);
        dto.setBno(bno);

        try {
            if(service.write(dto)!=1)
                throw new Exception("Write failed.");
            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }


    }

    //지정된 댓글을 삭제하는 메서드
    //HTTP의 DELETE 메서드를 맵핑함 (POST, GET 같은 종류) > 직접 테스트가 어려워서 POSTMAN 이용
    @DeleteMapping("/comments/{cno}")   // /comments/1 (삭제할 댓글 번호)
    //URL에 쿼리스트링으로 된 매개변수는 그냥 적어주어도 되는데
    //@DeleteMapping("/comments/{cno}") 의 {cno}는 맵핑 URI 자체이기 때문에
    //매개변수에 @PathVariable을 붙여줘야 한다. /comments/1?bno=1085
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session){
        String commenter = (String)session.getAttribute("id");

        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    //지정된 게시물의 모든 댓글을 가져오는 메서드
    @GetMapping("/comments")    // /comments?bno=  (GET)
    //메서드의 결과인 ResponseEntity<List<CommentDto>>객체를 JSON문자열로 응답해주기 위해
    //@ResponseBody를 붙임
    public ResponseEntity<List<CommentDto>> list(Integer bno){

        List<CommentDto> list = null;

        try {
            list = service.getList(bno);
            //요청작업이 실패해도 200번대 상태코드가 전송되는것을 다른 상태코드로 변경시켜주기 위해
            //ResponseEntity를 사용
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST);
        }
    }
}
