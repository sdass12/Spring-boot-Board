package com.hhk.board.domain;

public class Paging {

        int recordsPerPage;       // 페이지당 레코드 수
        int firstPageNo;          // 첫번째 페이지 번호
        int prevPageNo;           // 이전 페이지 번호
        int startPageNo;          // 시작 페이지 (페이징 너비 기준)
        int currentPageNo;        // 페이지 번호
        int endPageNo;            // 끝 페이지 (페이징 너비 기준)
        int nextPageNo;           // 다음 페이지 번호
        int finalPageNo;          // 마지막 페이지 번호
        int numberOfRecords;      // 전체 레코드 수
        int sizeOfPage;           // 보여지는 페이지 갯수 (1,2,3,4,5 갯수)

        public Paging(int currentPageNo, int recordsPerPage) {

            this.currentPageNo = currentPageNo;
            //기본 페이지 : 5개 보기를 default로 설정함
            this.sizeOfPage = 5;

            //recordsPerPage가 0이 아니면 recordsPerPage, 아니면 무조건 10(default : 10)
            this.recordsPerPage = (recordsPerPage != 0) ? recordsPerPage : 10;

        }

        public int getRecordsPerPage() {
            return recordsPerPage;
        }

        public void setRecordsPerPage(int recordsPerPage) {
            this.recordsPerPage = recordsPerPage;
        }

        public int getFirstPageNo() {
            return firstPageNo;
        }

        public void setFirstPageNo(int firstPageNo) {
            this.firstPageNo = firstPageNo;
        }

        public int getPrevPageNo() {
            return prevPageNo;
        }

        public void setPrevPageNo(int prevPageNo) {
            this.prevPageNo = prevPageNo;
        }

        public int getStartPageNo() {
            return startPageNo;
        }

        public void setStartPageNo(int startPageNo) {
            this.startPageNo = startPageNo;
        }

        public int getCurrentPageNo() {
            return currentPageNo;
        }

        public void setCurrentPageNo(int currentPageNo) {
            this.currentPageNo = currentPageNo;
        }

        public int getEndPageNo() {
            return endPageNo;
        }

        public void setEndPageNo(int endPageNo) {
            this.endPageNo = endPageNo;
        }

        public int getNextPageNo() {
            return nextPageNo;
        }

        public void setNextPageNo(int nextPageNo) {
            this.nextPageNo = nextPageNo;
        }

        public int getFinalPageNo() {
            return finalPageNo;
        }

        public void setFinalPageNo(int finalpageNo) {
            this.finalPageNo = finalpageNo;
        }

        public int getNumberOfRecords() {
            return numberOfRecords;
        }

        public void setNumberOfRecords(int numberOfRecords) {
            this.numberOfRecords = numberOfRecords;
        }

        /**
         * 페이징 생성
        **/
        public void makePaging() {
            if (numberOfRecords == 0)        // 게시글 전체 수가 없는 경우
                return;

            if (currentPageNo == 0)
                setCurrentPageNo(1);        // 기본 값 설정

            if (recordsPerPage == 0)
                setRecordsPerPage(10);        // 기본 값 설정

            // 마지막 페이지
            int finalPage = (numberOfRecords + (recordsPerPage - 1)) / recordsPerPage;

            if (currentPageNo > finalPage)
                setCurrentPageNo(finalPage);// 기본 값 설정

            if (currentPageNo < 0 || currentPageNo > finalPage)
                currentPageNo = 1;            // 현재 페이지 유효성 체크
            // 시작 페이지 (전체)
            boolean isNowFirst = currentPageNo == 1 ? true : false;
            boolean isNowFinal = currentPageNo == finalPage ? true : false;

            int startPage = ((currentPageNo - 1) / sizeOfPage) * sizeOfPage + 1;
            int endPage = startPage + sizeOfPage - 1;

            if (endPage > finalPage)
                endPage = finalPage;

            setFirstPageNo(1);                    // 첫번째 페이지 번호

            if (isNowFirst)
                setPrevPageNo(1);                // 이전 페이지 번호
            else                                // 이전 페이지 번호
                setPrevPageNo(((currentPageNo - 1) < 1 ? 1 : (currentPageNo - 1)));

            setStartPageNo(startPage);            // 시작페이지
            setEndPageNo(endPage);                // 끝 페이지

            if (isNowFinal)
                setNextPageNo(finalPage);        // 다음 페이지 번호
            else
                setNextPageNo(((currentPageNo + 1) > finalPage ? finalPage : (currentPageNo + 1)));

            setFinalPageNo(finalPage);            // 마지막 페이지 번호
        }
}
