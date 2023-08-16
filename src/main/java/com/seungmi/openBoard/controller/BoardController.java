package com.seungmi.openBoard.controller;

import com.seungmi.openBoard.domain.BoardDto;
import com.seungmi.openBoard.domain.PageHandler;
import com.seungmi.openBoard.domain.SearchCondition;
import com.seungmi.openBoard.service.BoardService;
import com.seungmi.openBoard.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    BoardService boardService;
    CommentService commentService;
    BoardController(BoardService boardService, CommentService commentService){
        this.boardService = boardService;
        this.commentService = commentService;
    }

    //신규 글 작성
    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode", "new");
        return "board"; //읽기와 쓰기에 사용하는 뷰, 쓰기에 사용할 때는 mode=new
    }

    //Post로 보낸 form의 내용을 맵핑
    @PostMapping("/write")
    //글쓴이정보는 글쓴이가 직접 입력하지 않고 현재 로그인 된 회원정보를 가져와야 하니 session이 필요함.
    //boardList.jsp 화면구성을 위해 BoardDto 객체들이 담긴 list를 가지고 있음
    //글 한줄 한줄이 하나의 BoardDto임.
    //처리 정상 또는 실패시 메시지를 띄우는데 새로고침시 계속 뜨는것을 방지하기위해 RedirectAttributes 객체 사용
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){

        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.write(boardDto);  //insert 결과를 rowCnt에 담음

            //insert가 실패하면 예외를 던짐
            if(rowCnt!=1)
                throw new Exception("Write failed");

            //세션을 이용한 일회성 저장 메세지
            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            //글쓰기에 실패하면 다시 board화면으로 돌아가는데 기존에 작성했던 내용이 사라지면 안되니
            //모델에 boardDto를 담아서 넘겨준다.
//            m.addAttribute("boardDto", boardDto);
            m.addAttribute(boardDto); // 타입의 첫문자를 소문자로 변경하여 이름으로 저장하므로 이름은 생략가능 (위와 동일 코드)
            //글쓰기 중 오류가 발생했다는 것을 메세지로 알려주기 위해 모델에 에러메세지를 담아서 넘겨줌
            m.addAttribute("msg", "WRT_ERR");

            return "board";
        }

    }

    @PostMapping("/modify")
    public String modify(Integer page, Integer pageSize, BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){

        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto);

            if(rowCnt!=1)
                throw new Exception("Modify failed");

            //세션을 이용한 일회성 저장 메세지
            rattr.addFlashAttribute("msg", "MOD_OK");

            rattr.addAttribute("page", page);
            rattr.addAttribute("pageSize", pageSize);

        } catch (Exception e) {
            e.printStackTrace();
            //글쓰기에 실패하면 다시 board화면으로 돌아가는데 기존에 작성했던 내용이 사라지면 안되니
            //모델에 boardDto를 담아서 넘겨준다.
//            m.addAttribute("boardDto", boardDto);
            m.addAttribute(boardDto); // 타입의 첫문자를 소문자로 변경하여 이름으로 저장하므로 이름은 생략가능 (위와 동일 코드)
            //글쓰기 중 오류가 발생했다는 것을 메세지로 알려주기 위해 모델에 에러메세지를 담아서 넘겨줌
            m.addAttribute("msg", "MOD_ERR");

            return "board";
        }

        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr){

        String writer = (String)session.getAttribute("id");

        try {

            rattr.addAttribute("page", page);
            rattr.addAttribute("pageSize", pageSize);

            int rowCnt = boardService.remove(bno, writer);

            if(rowCnt!=1){
                throw new Exception("board remove error");
            }

            rowCnt = commentService.removeAll(bno);
            System.out.println("rowCnt = " + rowCnt);

            if(rowCnt!=1){
                throw new Exception("comment remove error");
            }
            
            //1회성. 한번사용하고 사라짐. 페이지 새로고침시 Delete 메세지가 반복적으로 드는걸 방지
            //세션을 이용하는 것 > 한번사용 후 지우니까 부담이 없음
            rattr.addFlashAttribute("msg", "DEL_OK");

        } catch (Exception e) {
            e.printStackTrace();
            rattr.addAttribute("msg", "DEL_ERR");
        }

        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m){
        try {
            BoardDto boardDto = boardService.read(bno);
//            m.addAttribute("boardDto", boardDto); //아래 문장과 동일
            m.addAttribute(boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board";
    }

    //게시판 목록을 보여주는 메서드
    @GetMapping("/list")
    public String list(SearchCondition sc, Model m, HttpServletRequest request) {

        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        try {
                        
            //전체 게시글 수 가져오기
            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);
            //boardList.jsp에서 화면을 구성하기 위해 pageHandler로 필요한 정보 추출
            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            //offset, pageSize 정보가 담긴 map을 가지고 getPage를 호출하여 => SearchCondition으로 다시 만듬
            //board 테이블의 bno, title, content, writer, view_cnt, comment_cnt, reg_date 정보를 BoardDto에 담아
            //BoardDto가 여러개 담긴 list를 얻음
            List<BoardDto> list =  boardService.getSearchResultPage(sc);

            //모델에 boardList 화면구성에 필요한 정보들을 담아서 boardList.jsp에 넘겨줌
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서 (false는 session이 없어도 새로 생성하지 않는다. 반환값 null)
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}
