package com.hhk.board.repository;


import com.hhk.board.domain.BoardVO;
import com.hhk.board.domain.SearchVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardRepository {

    @Select("SELECT * FROM board ORDER BY bno DESC")
    List<BoardVO> List();

    @Select("SELECT * FROM board WHERE bno=#{bno}")
    BoardVO View(@Param("bno") int bno);

    @Select("SELECT COUNT(*) FROM board")
    int boardTotal();

    @Select("SELECT password FROM board WHERE bno=#{bno}")
    String getPW(@Param("bno") int bno);

    @Insert("INSERT " +
            "INTO " +
            "board " +
            "(content," +
            "title, " +
            "writer," +
            "password," +
            "date)" +
            "VALUES " +
            "(#{content}, " +
            "#{title}, " +
            "#{writer}, " +
            "#{password}, " +
            "now())"
            )
    void Write(BoardVO board);

    @Update("UPDATE " +
            "board " +
            "SET " +
            "title = #{title}," +
            "content = #{content}" +
            "WHERE " +
            "bno = #{bno}")
    void Update(BoardVO board);

    @Delete("DELETE " +
            "FROM " +
            "board " +
            "WHERE " +
            "bno = #{bno}")
    void Delete(@Param("bno") int bno);

    @Select("SELECT * FROM board ORDER BY bno DESC") //조건문 바꿔야됨. 이거는 제목으로 검색하는거니 WHERE절에 like 이용해서 추가할것
    List<SearchVO> t_search(SearchVO search);       //이거 말고도 c_search(내용 검색)과 search(내용,제목 검색)을 추가해야 됨. serviceImpl 참조할 것 (if문도 추가해야됨)

    @Select("")
    List<SearchVO> c_search(SearchVO search);

    @Select("")
    List<SearchVO> search(SearchVO search);
}