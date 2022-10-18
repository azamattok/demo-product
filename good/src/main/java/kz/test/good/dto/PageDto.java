package kz.test.good.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private List<T> data;
    private int pageNumber;
    private long total;
    private long size;
    private boolean hasNext;

    public PageDto(List<T> data, int pageNumber, long total, long size) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.total = total;
        this.size = size;
        this.setHasNext(pageNumber < this.getSize());
    }
}
