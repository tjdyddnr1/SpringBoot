package com.kh.totalEx.repository;

import com.kh.totalEx.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemNm(String itemNm); // select item_nm from item where item_nm = 'ddd'
    List<Item> findByItemNmAndPrice(String itemNm, int price);

    // OR 조건 처리하기
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // LessThan : 보다 작다라는 의미
    List<Item> findByPriceLessThan(int price);

    // 정렬
    List<Item> findByPriceLessThanOrderByPriceDesc(int price);

    // Between
    List<Item> findByPriceBetween(int minPrice, int maxPrice);

    List<Item> findByItemDetailContaining(String keyword);
}
