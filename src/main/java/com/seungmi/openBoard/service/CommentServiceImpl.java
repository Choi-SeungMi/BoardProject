package com.seungmi.openBoard.service;

import com.seungmi.openBoard.dao.*;
import com.seungmi.openBoard.domain.*;
import com.seungmi.openBoard.dao.BoardDao;
import com.seungmi.openBoard.dao.CommentDao;
import com.seungmi.openBoard.domain.CommentDto;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
//    //comment 개수가 board 테이블의 comment_cnt에 영향을 주기 때문에
//    //BoardDao, CommentDao 두개를 주입 받아야 함

//    > instance 변수에 주입받는것보다 생성자에 주입받는 것이 좋다.
//    왜냐하면, 인스턴스변수 각각 @Autowired를 붙여줘야 하는데 누락하는 실수를 하기 쉽기 때문.

//    @Autowired
    BoardDao boardDao;
//    @Autowired
    CommentDao commentDao;

//    @Autowired > 생성자가 하나일 때 매개변수를 통해서 주입 받기 때문에 @Autowired가 필요없음
    public CommentServiceImpl(CommentDao commentDao, BoardDao boardDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }

    @Override
    public int getCount(Integer bno) throws Exception {
        return commentDao.count(bno);
    }

    @Override
    //2가지 작업을 하나의 메서드로 처리하므로 트랜젝션으로 처리
    //기본적으로 런타임 오류는 rallback을 하는데 컴파일 오류는 rollback을 하지 않으므로 
    //(rollbackFor = Exception.class) 붙여주기
    @Transactional(rollbackFor = Exception.class) 
    public int remove(Integer cno, Integer bno, String commenter) throws Exception {
        //1. 해당 글번호의 comment_cnt를 -1함
        int rowCnt = boardDao.updateCommentCnt(bno, -1);
        System.out.println("updateCommentCnt - rowCnt = " + rowCnt);
//        throw new Exception("test");
        //2. 해당 코멘트 번호와 코멘트작성자가 일치하는 코멘트를 삭제함
        rowCnt = commentDao.delete(cno, commenter);
        System.out.println("rowCnt = " + rowCnt);
        return rowCnt;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
        boardDao.updateCommentCnt(commentDto.getBno(), 1);
//                throw new Exception("test");
        return commentDao.insert(commentDto);
    }

    @Override
    public List<CommentDto> getList(Integer bno) throws Exception {
//        throw new Exception("test");
        return commentDao.selectAll(bno);
    }

    @Override
    public CommentDto read(Integer cno) throws Exception {
        return commentDao.select(cno);
    }

    @Override
    public int modify(CommentDto commentDto) throws Exception {
        return commentDao.update(commentDto);
    }
}