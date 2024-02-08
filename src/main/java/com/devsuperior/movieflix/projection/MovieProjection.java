package com.devsuperior.movieflix.projection;

public interface MovieProjection {
    Long getId();
    String getTitle();
    String getSubTitle();
    Integer getYearMovie();
    String getImgUrl();
}
