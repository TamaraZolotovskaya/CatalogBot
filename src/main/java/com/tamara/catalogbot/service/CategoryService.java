package com.tamara.catalogBot.service;

public interface CategoryService {


    String addRoot(String name);

    String add(String child, String parent);

    String view();

    String remove(String name);
}
