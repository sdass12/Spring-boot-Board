package com.hhk.board.domain;

import lombok.Data;

@Data
public class SearchVO {
    private String op;
    private String s_con;

    public String getOp(){
        return op;
    }

    public void setOp(String op){
        this.op=op;
    }

    public String getS_con(){
        return s_con;
    }

    public void setS_con(String s_con){
        this.s_con=s_con;
    }
}
