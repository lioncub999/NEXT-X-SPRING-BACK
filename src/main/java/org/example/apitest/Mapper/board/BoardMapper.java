package org.example.apitest.Mapper.board;

import org.apache.ibatis.annotations.Mapper;
import org.example.apitest.model.board.Board;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> getBoardList();

    void insertBoard(String title);

    void deleteBoard(int id);

    Board getBoardById(int id);

    void updateBoard(Board board);

    void likeBoard(Board board);

    int getTotalBoardCount();
}
