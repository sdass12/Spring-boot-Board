package com.hhk.board.service.Impl;


import com.hhk.board.domain.Pagination;
import com.hhk.board.domain.SearchVO;
import com.hhk.board.repository.BoardRepository;
import com.hhk.board.service.BoardService;
import com.hhk.board.domain.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public ModelAndView List(int nowPage,int nowBlock){

        if(nowPage<1){
            nowPage=1;  //현재 페이지가 1미만으로 갔을 때 1로 바꿔줌.
        }

        int total = boardRepository.boardTotal();

        double nowPage2 = (double) nowPage;
        int nowBlock2=(int) Math.ceil(nowPage2/5); //현재 페이지를 이용해 현재 블럭을 알아냄 (한 블럭당 5개를 보여주기 때문에 5로나눔. 10으로 나누면 한 페이지에 1~10,11~20 이런식으로 나옴.1111


        if(nowBlock<nowBlock2 || nowBlock>nowBlock2){//페이지를 넘겼을 때 블럭이 같이 안 올라갔을 때를 대비해 블럭도 같이 올려줌. 예)페이지는 6이지만 블럭이1이면 블럭도 2로 만들어 줌.
            nowBlock=nowBlock2;                      //반대로 블럭이 같이 안 내려갔을 때도 블럭을 같이 내려줌.
        }

        Pagination pager = new Pagination();

        pager.setTotalPage(total);  //총 페이지수를 정해준다.
        pager.setTotalBlock(total); //총 블럭수를 정해준다.
        pager.setNowBlock(nowBlock);
        pager.setNowPage(nowPage); //현재 페이지를 정해 준 후 총 페이지와 현재 페이지를 이용해 스타트 페이지를 정해준다.(setNowPage 안에 setStartPage 가 있음)


        List<Object> BlockPage = this.getBlockPage(nowBlock,pager.getTotalPage());

        ModelAndView List = new ModelAndView("List");
        List.addObject("resultList", boardRepository.List(pager)); //게시글 정보(제목,내용,작성자,날짜 등)
        List.addObject("pager", pager);
        List.addObject("total", total);
        List.addObject("blockPage",BlockPage);


        return List;
    }

    @Override
    public BoardVO View(int bno){

        return boardRepository.View(bno);
    }

    @Override
    public void Write(BoardVO board){

        boardRepository.Write(board);

    }

    @Override
    public void Update(BoardVO board){

            boardRepository.Update(board);
    }

    @Override
    public void Delete(int bno){

        boardRepository.Delete(bno);
    }

    @Override
    public String getPW(int bno){

        String PW = boardRepository.getPW(bno);
        return PW;
    }

    @Override
    public ModelAndView search(SearchVO search,int nowPage,int nowBlock){

        Pagination pager = new Pagination();

        int total = boardRepository.searchTotal(search);

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

        List<Object> BlockPage = this.getBlockPage(nowBlock,pager.getTotalPage());
        List<BoardVO> s_result;
        if(op.equals("title")){
            s_result = boardRepository.t_search(search,pager);

        }else if(op.equals("content")){
            s_result = boardRepository.c_search(search,pager);
        }else{
            s_result = boardRepository.search(search,pager);
        }

        S_List.addObject("board", s_result); //검색 결과
        S_List.addObject("total",total); //검색 결과 총 갯수
        S_List.addObject("op",op); //검색 옵션
        S_List.addObject("s_con",search.getS_con()); //검색어
        S_List.addObject("pager",pager); //페이징 정보
        S_List.addObject("blockPage",BlockPage); //페이징 정보

        return S_List;
    }

    @Override
    public int searchTotal(SearchVO search){

        String op = search.getOp();
        int s_total=0;


        if(op.equals("title")) {
            s_total = boardRepository.t_searchTotal(search);
        }else if(op.equals("content")){
            s_total = boardRepository.c_searchTotal(search);
        }else{
            s_total = boardRepository.searchTotal(search);
        }


        return s_total;
    }

    public List<Object> getBlockPage(int nowBlock,int totalPage){
        List<Object> BlockPage = new ArrayList<Object>();



        for(int i=(nowBlock-1)*5+1; i<=nowBlock*5; i++){
            if(i>totalPage){
                break;
            }
            BlockPage.add(i);

        }
        return BlockPage;

    }


}
