package org.example.apitest.controller.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.apitest.model.board.Board;
import org.example.apitest.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @ResponseBody
    @GetMapping("/getBoardList")
    public List<Board> getBoardList(
    ) {
        List<Board> boardList = boardService.getBoardList();
        System.out.println(boardList);
        return boardList;
    }

    @PostMapping("/insert")
    public boolean insertBoard(
            @RequestBody Board board
    ) {
        boardService.insertBoard(board.getTitle());
        return true;
    }
}
