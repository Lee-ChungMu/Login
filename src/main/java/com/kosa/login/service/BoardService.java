package com.kosa.login.service;

import com.kosa.login.dto.BoardDTO;
import com.kosa.login.entity.Board;
import com.kosa.login.entity.Member;

import java.util.List;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    BoardDTO get(Long num);

    void modify(BoardDTO boardDTO);

    void remove(Long num);

    List<BoardDTO> getAllWithWriter(String writerEmail);

    default Board dtoToEntity(BoardDTO boardDTO){

        Board board = Board.builder()
                .num(boardDTO.getNum())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(Member.builder().email(boardDTO.getWriterEmail()).build())
                .build();

        return board;
    }

    default BoardDTO entityToDTO(Board board){

        BoardDTO boardDTO = BoardDTO.builder()
                .num(board.getNum())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(board.getWriter().getEmail())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        return boardDTO;

    }
}
