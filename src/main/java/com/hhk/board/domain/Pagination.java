package com.hhk.board.domain;


public class Pagination {
    private int nowPage=1; //현재 페이지 기본 값은 1
    private int totalPage; //모든 페이지
    private int nowBlock=1; //현재 블럭(1,2,3,4,5 = 1블럭  6,7,8,9,10 2블럭)
    private int totalBlock; //모든 블럭
    private int startPage=0; //LIMIT 문에서 쓸 시작 페이지

    public int getTotalBlock() {
        return totalBlock;
    }

    public void setTotalBlock(int totalPage) {
        this.totalBlock = (int) (Math.ceil(totalPage/5));
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int nowPage) {

        this.startPage = (nowPage-1)*10; //한 페이지당 10개씩 출력력
   }

    public int getNowBlock() {
        return nowBlock;
    }

    public void setNowBlock(int nowPage) {
        this.nowBlock = (int) (Math.ceil(nowPage/5));
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int total) {
        double total2=(double)total;
        this.totalPage =(int) Math.ceil(total2/10);
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        if(nowPage<1){ //현재 페이지가 전체 페이지보다 크거나 0페이지보다 작을 경우 첫 번째 페이지로 바꿈
            nowPage=1;
        }else if(nowPage>this.totalPage){
            nowPage=this.totalPage;
        }

        this.setStartPage(nowPage);
        this.nowPage = nowPage;
    }
}
