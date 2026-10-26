package com.elibrary.api;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageResponse<T> {
    private List<T> items;
    private int page;
    private int size;
    private long total;

    public static <S, T> PageResponse<T> of(Page<S> source, Function<S, T> mapper) {
        PageResponse<T> response = new PageResponse<>();
        response.items = source.getContent().stream().map(mapper).collect(Collectors.toList());
        response.page = source.getNumber();
        response.size = source.getSize();
        response.total = source.getTotalElements();
        return response;
    }

    public List<T> getItems() { return items; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotal() { return total; }
}
