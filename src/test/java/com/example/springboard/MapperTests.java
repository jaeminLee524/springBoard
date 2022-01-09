package com.example.springboard;

import com.example.springboard.domain.BoardDTO;
import com.example.springboard.mapper.BoardMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
//@Transactional
public class MapperTests {

    @Autowired
    private BoardMapper boardMapper;

    @Test
    public void 삽입테스트() {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle("1번 게시물");
        boardDTO.setContent("1번 게시물 내용");
        boardDTO.setWriter("jaemin");

        int idx = boardMapper.insertBoard(boardDTO);

        Assertions.assertThat(idx).isEqualTo(1);
    }

    @Test
    public void 상세조회() {
        BoardDTO boardDTO = boardMapper.selectBoardDetail((long)3);
//        Assertions.assertThat(boardDTO.getIdx()).isEqualTo(3);
        Assertions.assertThat(boardDTO.getContent()).isEqualTo("1번 게시물 내용");
        Assertions.assertThat(boardDTO.getWriter()).isEqualTo("jaemin");
    }

    @Test
    public void 게시물_수정() throws IllegalStateException{
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle("1번 게시물 수정");
        boardDTO.setContent("1번 게시글 내욜 수정");
        boardDTO.setWriter("jaemin2");
        boardDTO.setIdx((long)3);

        int idx = boardMapper.updateBoard(boardDTO);
        if(idx == 1) {
            System.out.println("if문 call");
            BoardDTO findDetail = boardMapper.selectBoardDetail((long) 3);
            Assertions.assertThat(findDetail.getTitle()).isEqualTo("1번 게시물 수정");
        }else {
            fail("예외 발생");
        }

    }

    @Test
    public void 게시물_삭제() throws IllegalStateException{
        int deleteCnt = boardMapper.deleteBoard((long) 3);
        if( deleteCnt == 1 ) {
            BoardDTO boardDTO = boardMapper.selectBoardDetail((long) 3);
            assertNull(boardDTO);
        }else {
            fail("예외 발생");
        }
    }
}
