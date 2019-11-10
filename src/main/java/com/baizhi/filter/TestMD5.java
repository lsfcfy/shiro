package com.baizhi.filter;

import org.apache.shiro.crypto.hash.Md5Hash;
//测试数据库时生成密文
public class TestMD5 {
    public static void main(String[] args) {
        Md5Hash md5 = new Md5Hash("3","zxcv",1024);
        String s = md5.toHex();
        System.out.println(s);
    }
}
