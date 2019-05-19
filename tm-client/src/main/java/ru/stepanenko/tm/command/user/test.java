package ru.stepanenko.tm.command.user;

import ru.stepanenko.tm.util.HashUtil;

public class test {
    public static void main(String[] args) {
        System.out.println(HashUtil.md5("admin"));
        System.out.println(HashUtil.md5("user"));
    }
}
