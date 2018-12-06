package com.hhk.board.service;

import java.util.List;

import com.hhk.board.domain.BoardVO;

public interface BoardService {

    public List<BoardVO> List(); //인덱스 페이지 리스트 출력
    public int boardTotal();
    public BoardVO View(int bno); //글 상세보기
    public void Write(BoardVO board); //글 쓰기
    public void Update(BoardVO board); //글 수정
    public void Delete(int bno); //글 삭제

}
