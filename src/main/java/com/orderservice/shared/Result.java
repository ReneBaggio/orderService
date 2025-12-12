package com.orderservice.shared;

import java.util.List;

public class Result<T> {

    public enum Type {
        SUCCESS,
        VALIDATION_ERROR,
        BUSINESS_ERROR,
        NOT_FOUND
    }

    private final Type type;
    private final T data;
    private final List<String> errors;

    private Result(Type type, T data, List<String> errors) {
        this.type = type;
        this.data = data;
        this.errors = errors;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Type.SUCCESS, data, List.of());
    }

    public static <T> Result<T> validationError(List<String> errors) {
        return new Result<>(Type.VALIDATION_ERROR, null, errors);
    }

    public static <T> Result<T> businessError(List<String> errors) {
        return new Result<>(Type.BUSINESS_ERROR, null, errors);
    }

    public static <T> Result<T> notFound(List<String> errors) {
        return new Result<>(Type.NOT_FOUND, null, errors);
    }

    public Type getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public List<String> getErrors() {
        return errors;
    }
}
