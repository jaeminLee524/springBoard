package com.example.springboard.mapper;

import com.example.springboard.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    //저장
    public int insertBoard(BoardDTO boardDTO);
    //세부사항
    public BoardDTO selectBoardDetail(Long idx);
    //수정
    public void updateBoard(BoardDTO boardDTO);
    //삭제
    public void deleteBoard(Long idx);
    //게시판 전체조회
    public List<BoardDTO> selectBoardList();
    //게시판 갯수
    public int selectBoardTotalCount();
}
