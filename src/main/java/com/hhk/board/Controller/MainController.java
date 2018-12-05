package com.hhk.board.Controller;

import com.hhk.board.service.BoardService;
import com.hhk.board.domain.BoardVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class MainController{

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BoardService boardService;

    // 글 리스트
    @GetMapping(value = {"/board","/"})
    public ModelAndView list() throws Exception{
        List<BoardVO> boardList = boardService.List();
        ModelAndView List = new ModelAndView("List");
        List.addObject("resultList", boardList);
        return List;
    }

    // 새 글 올리기
    @PostMapping("/write")
    public String insert(BoardVO board) throws Exception{
        logger.info("POST /board : " + board.toString());
        boolean emptyCheck = boardService.Write(board);

        if(emptyCheck){
            return null;
        }else {
            return "redirect://localhost:8080/board";
        }
    }

    // 글 수정
    @GetMapping("/update/{bno}")
    public ModelAndView update(@PathVariable("bno")int bno) throws Exception{
        ModelAndView Update = new ModelAndView("Update");
        Update.addObject("board", boardService.View(bno));

        return Update;
    }

    @PostMapping("/update/{bno}")
    public String update_ok(BoardVO board) throws Exception{
        logger.info("PUT data : " + board.toString());
         boolean emptyCheck = boardService.Update(board);

         if(emptyCheck){
             return null;
         }else{
             return "redirect://localhost:8080/board";
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

        return "redirect://localhost:8080/board";
    }
    //글 쓰기 페이지 이동
    @GetMapping("/write")
    public String writeForm(Model model){
        return "Write";
    }



}