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

@Controller
public class MainController{

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BoardService boardService;

    // 글 리스트
    @GetMapping(value = {"/board/{nowPage}/{nowBlock}","/{nowPage}/{nowBlock}"})
    public ModelAndView list(@PathVariable("nowPage")int nowPage, @PathVariable("nowBlock")int nowBlock) throws Exception{

        return boardService.List(nowPage,nowBlock);
    }

    //최초로 들어와서 페이지 정보가 없을 경우 혹은 글 쓰기,글 수정 등 특정 기능을 수행하고 페이지 정보를 잃어버렸을 경우 1페이지로 간다.
    @GetMapping(value = {"/","/board"})
    public ModelAndView f_list() throws Exception{

      return boardService.List(1,1);
    }

    // 새 글 올리기
    @PostMapping("/write")
    public String insert(BoardVO board) throws Exception{
        logger.info("POST /board : " + board.toString());
        boardService.Write(board);

            return "redirect://localhost:8080/";

    }

    // 글 수정 페이지로 이동
    @GetMapping("/update/{bno}")
    public ModelAndView update(@PathVariable("bno")int bno) throws Exception{
        ModelAndView Update = new ModelAndView("Update");
        Update.addObject("board" , boardService.View(bno));

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

        if(u_pw.equals(db_pw)){ //글 수정페이지에서 사용자가 입력한 비밀번호와 DB에 넣어놨던(글 작성 당시) 비밀번호를 비교해서 같다면 업데이트를 시킴.
            boardService.Update(board);

            return "redirect://localhost:8080/";
        }else{  //비밀번호가 다르다면 알람을 띄우고 수정페이지로 돌려보냄.
            pw.println("<script type='text/javascript'>alert('Check your password please'); history.back();</script>");
            pw.flush();
            return null;
        }
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


        return "redirect://localhost:8080/";
    }
    //글 쓰기 페이지 이동
    @GetMapping(value = {"/write","/board/write"})
    public String writeForm(Model model){
        return "Write";
    }

    //글 검색
    @PostMapping("/search/{nowPage}/{nowBlock}")
    public ModelAndView search(SearchVO search,@PathVariable("nowPage") int nowPage,@PathVariable("nowBlock")int nowBlock) throws Exception{


        return boardService.search(search,nowPage,nowBlock);
    }

}