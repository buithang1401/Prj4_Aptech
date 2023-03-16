package com.QLTC;

import com.QLTC.Module.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryData {

    public static List<Category> getCategory(){
        List<Category> categoryList = new ArrayList<>();

        Category Luong = new Category();
        Luong.setName("Lương");
        Luong.setImage(R.drawable.money);
        categoryList.add(Luong);

        Category ThuNo = new Category();
        ThuNo.setName("Thu Nợ");
        ThuNo.setImage(R.drawable.money__1_);
        categoryList.add(ThuNo);

        Category KhoanThuKhac = new Category();
        KhoanThuKhac.setName("Khoản Thu Khác");
        KhoanThuKhac.setImage(R.drawable.money);
        categoryList.add(KhoanThuKhac);

        return categoryList;
    }
}
