package com.kh.totalEx.repository;

import com.kh.totalEx.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")

class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 insert")
    public void createBoardTest() {
        for (int i = 1; i <= 10; i++) {
            Board board = new Board();

            board.setTitle("안녕하세요" + i);
            board.setContent("반갑습니다" + i);
            board.setImgPath("감사합니다" + i);
            board.setRegDate(LocalDateTime.now());
            boardRepository.save(board);
        }
    }

    @Test
    @DisplayName("전체 게시글 조회")
    public void findAllBoard() {
        this.createBoardTest();
        List<Board> boardList = boardRepository.findAll();
        for (Board e : boardList) System.out.println(e.toString());
    }

    @Test
    @DisplayName("키워드로 본문 내용 조회")
    public void findByContentContaining() {
        this.createBoardTest();
        List<Board> boardList = boardRepository.findByTitleContaining("반갑");
        for (Board e : boardList) System.out.println(e.toString());
    }

    @Test
    @DisplayName("키워드로 본문 내용 조회")
    public void findByTitleContaining() {
        this.createBoardTest();
        List<Board> boardList = boardRepository.findByContentContaining("안녕");
        for (Board e : boardList) System.out.println(e.toString());
    }
}