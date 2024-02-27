package com.danny.bookexplorer.model.search_request_2;

import android.graphics.pdf.PdfDocument;

import com.google.gson.annotations.SerializedName;

public class Range {
//    @SerializedName("pagecount")
//    private PageCount pageCount;

    @SerializedName("averagerating")
    private AverageRating averageRating;

//    public Range(PageCount pageCount, AverageRating averageRating){
//        this.averageRating = averageRating;
//        this.pageCount = pageCount;
//    }

    public Range(AverageRating averageRating){
        this.averageRating = averageRating;
    }

//    public PageCount getPageCount() {
//        return pageCount;
//    }
//
//    public void setPageCount(PageCount pageCount) {
//        this.pageCount = pageCount;
//    }

    public AverageRating getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(AverageRating averageRating) {
        this.averageRating = averageRating;
    }
}
