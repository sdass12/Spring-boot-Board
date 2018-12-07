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
        int total = boardService.boardTotal();
        List<BoardVO> boardList = boardService.List();
        ModelAndView List = new ModelAndView("List");
        List.addObject("resultList", boardList); //게시글 정보(제목,내용,작성자,날짜 등)
        List.addObject("total",total);//총 게시글 갯수

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
    public String search(SearchVO search){

        List<SearchVO> searchList = BoardService.search(search);

        return null;
    }



}