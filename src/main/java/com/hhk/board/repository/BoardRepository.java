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

    @Select("SELECT * FROM board WHERE title LIKE CONCAT('%',#{s_con},'%') ORDER BY bno DESC") //제목으로 검색
    List<BoardVO> t_search(SearchVO search);

    @Select("SELECT * FROM board WHERE content LIKE CONCAT('%',#{s_con},'%') ORDER BY bno DESC")
    List<BoardVO> c_search(SearchVO search);

    @Select("SELECT * FROM board WHERE title LIKE CONCAT('%',#{s_con},'%') OR content LIKE CONCAT('%',#{s_con},'%') ORDER BY bno DESC")
    List<BoardVO> search(SearchVO search);

    @Select("SELECT COUNT(*) FROM board WHERE title LIKE CONCAT('%',#{s_con},'%')")
    int t_searchTotal(SearchVO search);

    @Select("SELECT COUNT(*) FROM board WHERE content LIKE CONCAT('%',#{s_con},'%')")
    int c_searchTotal(SearchVO search);

    @Select("SELECT COUNT(*) FROM board WHERE title LIKE CONCAT('%',#{s_con},'%') OR content LIKE CONCAT('%',#{s_con},'%')")
    int searchTotal(SearchVO search);
}