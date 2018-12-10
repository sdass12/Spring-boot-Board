package com.hhk.board.Controller;

import com.hhk.board.domain.SearchVO;
import com.hhk.board.service.BoardService;
import com.hhk.board.domain.BoardVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MainController{

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BoardService boardService;

    // 글 리스트
    @GetMapping(value = {"/board","/"})
    public ModelAndView list() throws Exception{

        ModelAndView List = new ModelAndView("List");
        List.addObject("resultList", boardService.List()); //게시글 정보(제목,내용,작성자,날짜 등)
        List.addObject("total", boardService.boardTotal());//총 게시글 갯수

        return List;
    }

    // 새 글 올리기
    @PostMapping("/write")
    public String insert(BoardVO board) throws Exception{
        logger.info("POST /board : " + board.toString());
        boardService.Write(board);

            return "redirect://localhost:8080/board";

    }

    // 글 수정 페이지로 이동
    @GetMapping("/update/{bno}")
    public ModelAndView update(@PathVariable("bno")int bno) throws Exception{
        ModelAndView Update = new ModelAndView("Update");
        Update.addObject("board", boardService.View(bno));

        return Update;
    }

    //글 수정
    @PostMapping("/update/{bno}")
    public String update_ok(BoardVO board, HttpServletResponse httpServletResponse) throws Exception{
        logger.info("PUT data : " + board.toString());

        int bno = board.getBno();
        String u_pw = board.getPassword();

        String db_pw = boardService.getPW(bno);

        PrintWriter pw = httpServletResponse.getWriter();

        if(u_pw.equals(db_pw)){
            boardService.Update(board);

            return "redirect://localhost:8080/board";
        }else{
            pw.println("<script type='text/javascript' charset='utf-8'>alert('Check your password please'); history.back();</script>");
            pw.flush();
        }
        return null;
    }

    // 글 상세보기
    @GetMapping("/view/{bno}")
    public ModelAndView view(@PathVariable("bno") int bno)throws Exception{
        ModelAndView View = new ModelAndView("View");
        View.addObject("board", boardService.View(bno));

        return View;
    }

    // 글 삭제
    @PostMapping("/delete/{bno}")
    public String delete(@PathVariable("bno") int bno) throws Exception{
        logger.info("DELETE bno : " + bno);
        boardService.Delete(bno);


        return "redirect://localhost:8080/board";
    }
    //글 쓰기 페이지 이동
    @GetMapping("/write")
    public String writeForm(Model model){
        return "Write";
    }

    //글 검색
    @PostMapping("/search")
    public ModelAndView search(SearchVO search) throws Exception{

        ModelAndView S_List = new ModelAndView("S_List");
        String op = search.getOp();
        if(op.equals("title")){
            op = "제목";
        }else if(op.equals("content")){
            op = "내용";
        }else{
            op = "제목+내용";
        }

        S_List.addObject("board", boardService.search(search)); //검색 결과
        S_List.addObject("total",boardService.searchTotal(search)); //검색 결과 총 갯수
        S_List.addObject("op",op);
        S_List.addObject("s_con",search.getS_con());

        return S_List;
    }



}