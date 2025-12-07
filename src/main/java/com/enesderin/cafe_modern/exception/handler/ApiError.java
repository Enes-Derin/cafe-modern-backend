package com.enesderin.cafe_modern.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError<E> {
    private Exception<E> exception;
    private Integer status;
}
