package com.hhk.board.Controller;

import com.hhk.board.domain.Pagination;
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
    @GetMapping(value = {"/board/{nowPage}/{nowBlock}","/{nowPage}/{nowBlock}"})
    public ModelAndView list(@PathVariable("nowPage")int nowPage, @PathVariable("nowBlock")int nowBlock) throws Exception{
        Pagination pager = new Pagination();
        if(nowPage<1){
            nowPage=1;  //현재 페이지가 1미만으로 갔을 때 1로 바꿔줌.
        }
        int total = boardService.boardTotal();
        double nowPage2 = (double) nowPage;
        int nowBlock2=(int) Math.ceil(nowPage2/5);  //페이지를 넘겼을 때 블럭이 같이 안 올라갔을 때를 대비해 블럭도 같이 올려줌. 예)페이지는 6이지만 블럭이1이면 블럭도 2로 만들어 줌.
                                                    //반대로 블럭이 같이 안 내려갔을 때도 블럭을 같이 내려줌.
        if(nowBlock<nowBlock2 || nowBlock>nowBlock2){
            nowBlock=nowBlock2;
        }

        pager.setTotalPage(total);  //총 페이지수를 정해준다.
        pager.setTotalBlock(total); //총 블럭수를 정해준다.
        pager.setNowBlock(nowBlock); //현재 블럭수를 정해준다.
        pager.setNowPage(nowPage); //현재 페이지를 정해 준 후 총 페이지와 현재 페이지를 이용해 스타트 페이지를 정해준다.(setNowPage 안에 setStartPage 가 있음)



        List<Object> BlockPage = boardService.getBlockPage(nowBlock,pager.getTotalPage());

        ModelAndView List = new ModelAndView("List");
        List.addObject("resultList", boardService.List(nowPage,total,nowBlock)); //게시글 정보(제목,내용,작성자,날짜 등)
        List.addObject("pager", pager);
        List.addObject("total", total);
        List.addObject("blockPage",BlockPage);

        return List;
    }

    //최초로 들어와서 페이지 정보가 없을 경우 혹은 글 쓰기,글 수정 등 특정 기능을 수행하고 페이지 정보를 잃어버렸을 경우 1페이지로 간다.
    @GetMapping(value = {"/","/board"})
    public ModelAndView f_list() throws Exception{
        Pagination pager = new Pagination();

        int total = boardService.boardTotal();
        int nowPage=1;
        int nowBlock=1;

        pager.setTotalPage(total);
        pager.setTotalBlock(total);
        pager.setNowPage(nowPage);
        pager.setNowBlock(nowBlock);

        List<Object> BlockPage = boardService.getBlockPage(nowBlock,pager.getTotalPage());

        ModelAndView List = new ModelAndView("List");

        List.addObject("resultList", boardService.List(nowPage,total,nowBlock)); //게시글 정보(제목,내용,작성자,날짜 등)
        List.addObject("pager", pager);
        List.addObject("total", total);
        List.addObject("blockPage",BlockPage);

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
    @GetMapping(value = {"/write","/board/write"})
    public String writeForm(Model model){
        return "Write";
    }

    //글 검색
    @PostMapping("/search/{nowPage}/{nowBlock}")
    public ModelAndView search(SearchVO search,@PathVariable("nowPage") int nowPage,@PathVariable("nowBlock")int nowBlock) throws Exception{
        Pagination pager = new Pagination();

        int total = boardService.searchTotal(search);

        String op = search.getOp();

        if(op.equals("title")){ //검색 결과를 출력해 줄 때 검색 옵션이 영문으로 나오는 것을 방지
            op = "제목";
        }else if(op.equals("content")){
            op = "내용";
        }else{
            op = "제목+내용";
        }

        if(total==0){
            ModelAndView S_List_N = new ModelAndView("S_List_N");   //검색 결과가 없을 경우 일반 검색 결과 페이지가 아닌 다른 페이지로 보낸다(페이징 관련해서 에러때문에 따로 관리)

            S_List_N.addObject("op",op);
            S_List_N.addObject("s_con",search.getS_con());

            return S_List_N;
        }

        pager.setTotalPage(total);  //총 페이지수를 정해준다.
        pager.setTotalBlock(total); //총 블럭수를 정해준다.
        pager.setNowPage(nowPage);
        pager.setNowBlock(nowBlock);
        ModelAndView S_List = new ModelAndView("S_List");

        List<Object> BlockPage = boardService.getBlockPage(nowBlock,pager.getTotalPage());

        S_List.addObject("board", boardService.search(search,nowPage,total)); //검색 결과
        S_List.addObject("total",total); //검색 결과 총 갯수
        S_List.addObject("op",op); //검색 옵션
        S_List.addObject("s_con",search.getS_con()); //검색어
        S_List.addObject("pager",pager); //페이징 정보
        S_List.addObject("blockPage",BlockPage); //페이징 정보

        return S_List;
    }

}