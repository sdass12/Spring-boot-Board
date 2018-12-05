package com.hhk.board.domain;

import lombok.Data;

@Data
public class BoardVO {

    private int bno;
    private String title;
    private String content;
    private String date;
    private String writer;
    private String password;


    public int getBno(){
        return bno;
    }

    public void setBno(int bno){
        this.bno=bno;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content=content;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date=date;
    }

    public String getWriter(){
        return writer;
    }

    public void setWriter(String writer){
        this.writer=writer;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

}
