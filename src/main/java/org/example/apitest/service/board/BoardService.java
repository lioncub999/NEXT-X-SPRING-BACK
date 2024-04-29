package org.example.apitest.service.board;

import org.example.apitest.Mapper.board.BoardMapper;
import org.example.apitest.model.board.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardMapper boardMapper;

    public List<Board> getBoardList() {
        return boardMapper.getBoardList();
    }

    public void insertBoard(String title) {
        boardMapper.insertBoard(title);
    }
}
