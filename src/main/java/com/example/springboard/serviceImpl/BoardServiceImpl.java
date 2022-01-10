//package com.example.springboard.serviceImpl;
//
//import com.example.springboard.domain.BoardDTO;
//import com.example.springboard.mapper.BoardMapper;
//import com.example.springboard.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//
//
//public class BoardServiceImpl implements BoardService {
//
//
//
//    /**
//     * 게시판 등록
//     */
//    @Override
//    public void registerBoard(BoardDTO boardDTO) {
//        if( boardDTO.getIdx() == null ) {
//            //생성
//            boardMapper.insertBoard(boardDTO);
//        }else {
//            //수정
//            boardMapper.updateBoard(boardDTO);
//        }
//    }
//
//    /**
//     * 게시판 상세정보
//     */
//    @Override
//    public BoardDTO getBoardDetail(Long idx) {
//        return boardMapper.selectBoardDetail(idx);
//    }
//
//    /**
//     * 게시판 삭제
//     */
//    @Override
//    public void deleteBoard(Long idx) {
//        boardMapper.deleteBoard(idx);
//    }
//
//    /**
//     * 게시판 목록 조회
//     */
//    @Override
//    public List<BoardDTO> getBoardList() {
//        int totalCnt = boardMapper.selectBoardTotalCount();
//
//        if( totalCnt > 0 ) {
//            return boardMapper.selectBoardList();
//        }else {
//            throw new IllegalStateException();
//        }
//    }
//
//    /**
//     * 게시판 내용 수정
//     */
//    @Override
//    public void updateBoard(BoardDTO boardDTO) {
//        boardMapper.updateBoard(boardDTO);
//    }
//}
