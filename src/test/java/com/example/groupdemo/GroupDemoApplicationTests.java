package com.example.groupdemo;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
class GroupDemoApplicationTests {

    @Autowired
    Gson gson;

    @Test
    void contextLoads() {

        ////分类项数据

        List<CategoryItem> categoryItemList = new ArrayList<>();

        CategoryItem item1 = new CategoryItem();
        item1.setItemId(1);
        item1.setCategoryId(3);
        item1.setItemName("item-1");

        categoryItemList.add(item1);

        CategoryItem item2 = new CategoryItem();
        item2.setItemId(2);
        item2.setCategoryId(1);
        item2.setItemName("item-2");

        categoryItemList.add(item2);

        CategoryItem item3 = new CategoryItem();
        item3.setItemId(3);
        item3.setCategoryId(1);
        item3.setItemName("item-3");

        categoryItemList.add(item3);


        CategoryItem item4 = new CategoryItem();
        item4.setItemId(4);
        item4.setCategoryId(3);
        item4.setItemName("item-4");

        categoryItemList.add(item4);

        CategoryItem item5 = new CategoryItem();
        item5.setItemId(5);
        item5.setCategoryId(4);
        item5.setItemName("item-5");

        categoryItemList.add(item5);

        ////分类数据

        List<Category> categoryList = new ArrayList<>();

        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setCategoryName("cate-1");
        categoryList.add(category1);

        Category category2 = new Category();
        category2.setCategoryId(3);
        category2.setCategoryName("cate-3");
        categoryList.add(category2);

        Category category3 = new Category();
        category3.setCategoryId(4);
        category3.setCategoryName("cate-4");
        categoryList.add(category3);

        //按categroyId分组分类数据
        Map<Integer, Category> categoryMap = categoryList.stream().collect(Collectors.toMap(Category::getCategoryId, Function.identity()));

        //按categroyId分组分类项数据
        Map<Integer, List<CategoryItem>> categoryItemMap = categoryItemList.stream().collect(Collectors.groupingBy(CategoryItem::getCategoryId));

        //最后组装完成后的数据结构
        List<CategoryVo> categoryVoList = new ArrayList<>(categoryMap.size());

        Set<Map.Entry<Integer, Category>> categorySet =  categoryMap.entrySet();

        Iterator<Map.Entry<Integer, Category>> entryIterator = categorySet.iterator();

        //开始组装数据
        while (entryIterator.hasNext()){
            Map.Entry<Integer, Category> entry = entryIterator.next();
            Integer key = entry.getKey();
            Category category = entry.getValue();

            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setCategoryId(category.getCategoryId());
            categoryVo.setCategoryName(category.getCategoryName());
            List<CategoryItem> items = categoryItemMap.get(key);
            categoryVo.setItems(items);

            categoryVoList.add(categoryVo);

        }


        System.out.println(gson.toJson(categoryVoList));


    }

}
