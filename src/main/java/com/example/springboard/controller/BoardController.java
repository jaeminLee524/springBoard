package com.example.springboard.controller;

import com.example.springboard.controller.dto.BoardForm;
import com.example.springboard.domain.Board;
import com.example.springboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시물 생성
     * 폼 객체를 이용하여 웹 계층과 서비스 계층을 분리
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("board", new BoardForm());
        return "board/createBoardForm";
    }


    /**
     * 게시물 저장
     */
    //TODO validation
    @PostMapping("/register")
    public String boardWrite(BoardForm form) {
        //form 객체에서 데이터 추출
        Board board = new Board();
        board.setNoticeYn(form.getNoticeYn());
        board.setSecretYn(form.getSecretYn());
        board.setTitle(form.getTitle());
        board.setWriter(form.getWriter());
        board.setContent(form.getContent());

        boardService.registerBoard(board);

        return "redirect:/board/list";
    }

    /**
     * 게시물 목록 조회
     */
    @GetMapping("/list")
    public String boardList(Model model) {
        List<Board> boardList = boardService.getBoardList();

        model.addAttribute("boardList", boardList);

        return "board/list";
    }

    /**
     * 게시물 상세페이지
     */
    @GetMapping("/detail/{idx}")
    public String boardDetail(@PathVariable Long idx, Model model) {
        // 넘어온 idx값 체크
        if( idx == null ) {
            return "redirect:/board/list";
        }

        // DB에서 찾아온 게시물이 없거나 삭제됐는지 체크
        Board findBoard = boardService.getBoardDetail(idx);
        if( findBoard == null || "Y".equals(findBoard.getDeleteYn())) {
            return "redirect:/board/list";
        }

        // DB에서 찾아온 놈을 form에 셋팅해서 updateForm에 전달함 -> updateForm에서 데이터 수정한 뒤에 post로 update/{idx}로 보냄
//        BoardForm form = new BoardForm();
//
//        form.setIdx(findBoard.getIdx());
//        form.setTitle(findBoard.getTitle());
//        form.setWriter(findBoard.getWriter());
//        form.setContent(findBoard.getContent());
//        form.setCreateTime(findBoard.getCreateTime());
//        form.setViewCnt(findBoard.getViewCnt());
//
//        log.info("detail에 떨어지는 객체 : {} ", form);

        model.addAttribute("board", findBoard);

        return "board/detail";
    }

    /**
     * 게시물 수정 - GET, form을 전달받아서 화면에 뿌려줌
     */
    @GetMapping("/update/{idx}")
    public String updateBoardForm(@PathVariable Long idx, Model model) {
        // 영속성 데이터 추출
        Board findBoard = boardService.getBoardDetail(idx);

        // model에 내려줄 form객체 생성
        BoardForm boardForm = new BoardForm();

        //form객체에 추출한 데이터 세팅
        boardForm.setIdx(findBoard.getIdx());
        boardForm.setTitle(findBoard.getTitle());
        boardForm.setWriter(findBoard.getWriter());
        boardForm.setContent(findBoard.getContent());
        boardForm.setCreateTime(findBoard.getCreateTime());
        boardForm.setViewCnt(findBoard.getViewCnt());

        model.addAttribute("board", boardForm);
        log.info("updateForm에 뿌려지는 객체 : {} ", boardForm);

        return "board/updateBoardForm";
    }

    /**
     * 게시물 수정 - POST
     */
    @PostMapping("/update/{idx}")
    public String updateBoard(BoardForm form) {
        log.info("post로 넘어온 객체는 ? {}", form);
        // idx값 체크
        if( form.getIdx() == null ) {
            return "redirect:/board/list";
        }

        //service에 위임
        boardService.updateBoard(form);

        return "redirect:/board/list";
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/delete/{idx}")
    public String boardDelete(@PathVariable Long idx) {
        // idx에 맞는 데이터 삭제
        boardService.deleteBoard(idx);

        return "redirect:/board/list";
    }
}
