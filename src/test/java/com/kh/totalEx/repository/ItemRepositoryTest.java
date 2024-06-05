package com.kh.totalEx.repository;
import com.kh.totalEx.constant.ItemSellStatus;
import com.kh.totalEx.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.diff.DiffAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.xml.crypto.dsig.XMLSignature;
import java.time.LocalDateTime;
import java.util.List;

import static java.rmi.server.LogStream.log;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")

class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }
    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품3");
        for(Item e : itemList) System.out.println(e.getItemDetail());
    }
    @Test
    @DisplayName("상품명 또는 상품 상세 설명 조회")
    public void findByItemNmorItemDetailTest() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1","테스트 상품 상세 설명5");
        for(Item e : itemList) System.out.println(e.getItemNm());
    }
    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
        }
    @Test
    @DisplayName("가격 Between 테스트")
    public void findByPriceBetween() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceBetween(10001, 10007);
        for(Item item : itemList) {
            log.info(item.toString());
        }
    }
    @Test
    @DisplayName("문자열 찾기")
    public void findByItemDetailContaining() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemDetailContaining("설명2");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("@Query를 이용한 검색 기능 구현")
    public void findByItemDetailTest() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemDetail("설명6");
        for(Item e : itemList) System.out.println(e.toString());
    }
    @Test
    @DisplayName("@Query를 이용한 검색 기능 구현")
    public void findByItemDetailByNative() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemDetailByNative("설명6");
        for(Item e : itemList) log.info(e.toString());
    }

}


