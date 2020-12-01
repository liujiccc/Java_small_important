package cn.itcast.dao.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author QLBF
 * @version 1.0
 * @date 2020/12/1 9:27
 */
public class test {
    public static void main(String[] args) {
//        List list=new ArrayList();
//        list.add("和机缘");
//        list.add("小何");
//        System.out.println(list);
//        System.out.println(list.toArray());

        String s1="hello";
        StringBuilder s2=new StringBuilder(s1);
        System.out.println("s2:"+s2);
        s2.append(" world");
        System.out.println("s2添加后:"+s2);
        System.out.println("s2.tostring后:"+s2.toString());



    }
}
