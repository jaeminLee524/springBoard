package com.example.springboard.controller;

import com.example.springboard.controller.dto.BoardForm;
import com.example.springboard.domain.BoardDTO;
import com.example.springboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNoticeYn(form.getNoticeYn());
        boardDTO.setSecretYn(form.getSecretYn());
        boardDTO.setTitle(form.getTitle());
        boardDTO.setWriter(form.getWriter());
        boardDTO.setContent(form.getContent());

        boardService.registerBoard(boardDTO);

        return "redirect:/board/list";
    }

    /**
     * 게시물 목록 조회
     */
    @GetMapping("/list")
    public String boardList(Model model) {
        List<BoardDTO> boardList = boardService.getBoardList();

        model.addAttribute("boardList", boardList);

        return "board/list";
    }

    /**
     * 게시물 상세페이지
     */
    @GetMapping("/detail")
    public String boardDetail(Long idx, Model model) {
        // 넘어온 idx값 체크
        if( idx == null ) {
            return "redirect:/board/list";
            //TODO 메시지 뿌려주면 좋음
        }

        // DB에서 찾아온 게시물이 없거나 삭제됐는지 체크
        BoardDTO findBoard = boardService.getBoardDetail(idx);
        if( findBoard == null || "Y".equals(findBoard.getDeleteYn())) {
            return "redirect:/board/list";
        }
        model.addAttribute("board", findBoard);

        return "board/detail";
    }

    //TODO 게시판 정보 수정
    @PutMapping("/update/{idx}")
    public String boardUpdate(@PathVariable Long idx) {
        // idx값 체크
        if( idx == null ) {
            return "redirect:/board/list";
        }

        // DB에서 idx 해당하는 객체 찾기
        BoardDTO findBoard = boardService.getBoardDetail(idx);

        // 찾은 객체를 update
        boardService.updateBoard(findBoard);

        return "redirect:/board/list";
    }

}
