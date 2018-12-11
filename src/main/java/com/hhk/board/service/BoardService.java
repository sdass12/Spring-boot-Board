package com.hhk.board.service;

import java.util.List;

import com.hhk.board.domain.BoardVO;
import com.hhk.board.domain.SearchVO;

public interface BoardService {

    public List<BoardVO> List(int nowPage); //인덱스 페이지 리스트 출력
    public int boardTotal();
    public BoardVO View(int bno); //글 상세보기
    public void Write(BoardVO board); //글 쓰기
    public void Update(BoardVO board); //글 수정
    public void Delete(int bno); //글 삭제
    public String getPW(int bno); //글 수정을 할 때 비밀번호 조회에 사용
    public List<BoardVO> search(SearchVO search); //글 검색
    public int searchTotal(SearchVO search);


}
