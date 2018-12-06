package com.hhk.board.service.Impl;

import com.hhk.board.Controller.MainController;
import com.hhk.board.repository.BoardRepository;
import com.hhk.board.service.BoardService;
import com.hhk.board.domain.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


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
    public int boardTotal(){

        return boardRepository.boardTotal();
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



}
