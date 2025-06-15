package com.example.novashop22.commun.helper;

import com.dashy.orchestrator.commun.exception.BusinessException;
import com.dashy.orchestrator.commun.payload.pagination.PagedResult;
import com.dashy.orchestrator.domain.enums.SortDirectionEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public final class PageUtils {

    private PageUtils() {
        // Private constructor to prevent instantiation
    }

    public static Pageable getPageable(int page, int size) {
        validatePageNumberAndSize(page, size);
        return PageRequest.of(page, size, Sort.Direction.DESC, "id");
    }

    public static Pageable getPageable(int page, int size, SortDirectionEnum sort) {
        validatePageNumberAndSize(page, size);
        return PageRequest.of(page, size, SortDirectionEnum.toSpringDirection(sort), "id");
    }

    private static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BusinessException("Invalid page number or size. Page number cannot be less than zero.");
        }
    }

    public static <D> PagedResult<D> toPageResult(Page<?> pages, Class<D> destinationType) {
        if (pages == null) {
            return null;
        }
        PagedResult<D> result = new PagedResult<>();
        result.setPage(pages.getNumber());
        result.setLast(pages.isLast());
        result.setSize(pages.getSize());
        result.setTotalPages(pages.getTotalPages());
        result.setTotalElements(pages.getTotalElements());
        result.setEntities(pages.getContent()
                .stream().map(entity -> MapHelper.map(entity, destinationType))
                .toList());
        return result;
    }

    public static <D> PagedResult<D> toPageResult(Page<?> pages, List<D> entities) {
        if (pages == null) {
            return null;
        }
        PagedResult<D> result = new PagedResult<>();
        result.setPage(pages.getNumber());
        result.setLast(pages.isLast());
        result.setSize(pages.getSize());
        result.setTotalPages(pages.getTotalPages());
        result.setTotalElements(pages.getTotalElements());
        result.setEntities(entities);
        return result;
    }
}
