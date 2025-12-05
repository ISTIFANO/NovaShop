package com.example.novashop22.domain.enums;

import org.springframework.data.domain.Sort;

public enum SortDirectionEnum {

    ASC, DESC;

    public static Sort.Direction toSpringDirection(SortDirectionEnum direction) {
        if (direction == null) {
            return null;
        }
        switch (direction) {
            case ASC:
                return Sort.Direction.ASC;
            default:
                return Sort.Direction.DESC;
        }
    }

}
