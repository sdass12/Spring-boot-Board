package com.hhk.board.repository;


import com.hhk.board.domain.BoardVO;
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
}