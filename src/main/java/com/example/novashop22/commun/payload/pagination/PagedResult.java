package com.example.novashop22.commun.payload.pagination;

import lombok.Data;

import java.util.List;

/**
 * PagedResult
 *
 * @param <T> Template Clazz
 * @author f.ghemari
 * @version 1.1
 */
@Data
public class PagedResult<T> {

    private int page; // current page
    private int size; // current size
    private long totalElements; // total of elements
    private int totalPages; // total pages
    private boolean last; // is last page ?

    private List<T> entities; // db entities

}
