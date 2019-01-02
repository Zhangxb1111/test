package com.test.service;

import com.test.entity.MonUnit;

import java.util.List;

public interface MonUnitServiceI {

    void insert();

    void executeAsync();

    List<MonUnit> getList();
}
