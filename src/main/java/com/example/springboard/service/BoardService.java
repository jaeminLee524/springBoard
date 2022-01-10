package com.example.springboard.service;

import com.example.springboard.controller.dto.BoardForm;
import com.example.springboard.domain.Board;
import com.example.springboard.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardMapper boardMapper;

    /**
     * 게시판 등록
     */
    public void registerBoard(Board board) {
        if( board.getIdx() == null ) {
            //생성
            boardMapper.insertBoard(board);
        }else {
            //수정
            boardMapper.updateBoard(board);
        }
    }

    /**
     * 게시판 목록 조회
     */
    public List<Board> getBoardList() {
        int totalCnt = boardMapper.selectBoardTotalCount();

        if( totalCnt > 0 ) {
            return boardMapper.selectBoardList();
        }else {
            //TODO DB에 데이터가 하나도 없을 때 처리하는 로직 추가 예정
            throw new IllegalStateException();
        }
    }

    /**
     * 게시판 상세정보
     */
    public Board getBoardDetail(Long idx) {
        return boardMapper.selectBoardDetail(idx);
    }

    /**
     * 게시판 내용 수정
     * idx로 영속성 데이터를 추출 후, 영속성 데이터에 update
     */
    public void updateBoard(BoardForm form) {
        //영속성 데이터 추출
        Board findBoard = boardMapper.selectBoardDetail(form.getIdx());
        log.info("영속성 데이터 : {} ", findBoard);

        //영속성 데이터에 저장
        findBoard.setNoticeYn(form.getNoticeYn());
        findBoard.setSecretYn(form.getSecretYn());
        findBoard.setTitle(form.getTitle());
        findBoard.setWriter(form.getWriter());
        findBoard.setTitle(form.getTitle());

        boardMapper.updateBoard(findBoard);
    }

    /**
     * 게시판 삭제
     */
    public void deleteBoard(Long idx) {
        boardMapper.deleteBoard(idx);
    }
}
