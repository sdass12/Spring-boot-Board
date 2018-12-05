package com.hhk.board.service.Impl;

import com.hhk.board.Controller.MainController;
import com.hhk.board.repository.BoardRepository;
import com.hhk.board.service.BoardService;
import com.hhk.board.domain.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;


    @Override
    public List<BoardVO> List(){

       return boardRepository.List();
    }

    @Override
    public BoardVO View(int bno){

        return boardRepository.View(bno);
    }

    @Override
    public boolean Write(BoardVO board){
       String title = board.getTitle();
       String content = board.getContent();
       String writer = board.getWriter();

       if(title.length()==0 || content.length()==0 || writer.length()==0 || title.equals("")){
           return true;
       }else {
           boardRepository.Write(board);
           return false;

       }
    }

    @Override
    public void Update(BoardVO board){

        boardRepository.Update(board);
    }

    @Override
    public void Delete(int bno){

        boardRepository.Delete(bno);
    }


    public void Write(boolean empty){

        if(empty) {

        }
        else{

        }
    }


}
