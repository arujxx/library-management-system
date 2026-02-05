package com.company.validation;

public interface Validator<T> {
    boolean isValid(T obj);
}
