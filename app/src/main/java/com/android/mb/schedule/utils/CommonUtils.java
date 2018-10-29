package com.android.mb.schedule.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CommonUtils {
    /**
     * 创建比较器
     */
    public static <T> List<List<T>> dividerList(List<T> list,Comparator<? super T> comparator) {
        List<List<T>> lists = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            boolean isContain = false;
            for (int j = 0; j < lists.size(); j++) {
                if (lists.get(j).size() == 0||comparator.compare(lists.get(j).get(0),list.get(i)) == 0) {
                    lists.get(j).add(list.get(i));
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                List<T> newList = new ArrayList<>();
                newList.add(list.get(i));
                lists.add(newList);
            }
        }
        return lists;
    }
}
