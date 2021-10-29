package com.kosa.login.service;

import com.kosa.login.dto.BoardDTO;
import com.kosa.login.entity.Board;
import com.kosa.login.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl  implements BoardService{


    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO boardDTO) {

        Board board = dtoToEntity(boardDTO);

        log.info("===========================================");
        log.info(board);

        boardRepository.save(board);

        return board.getNum();
    }

    @Override
    public BoardDTO get(Long num) {
        Optional<Board> result = boardRepository.getWithWriter(num);

        if(result.isPresent()){
            return entityToDTO(result.get());
        }

        return null;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Long num = boardDTO.getNum();
        Optional<Board> result = boardRepository.findById(num);

        if(result.isPresent()){
            Board board = result.get();
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(board.getContent());
            boardRepository.save(board);
        }
    }

    @Override
    public void remove(Long num) {
        boardRepository.deleteById(num);
    }

    @Override
    public List<BoardDTO> getAllWithWriter(String writerEmail) {
        List<Board> boardList = boardRepository.getList(writerEmail);

        return boardList.stream().map(board -> entityToDTO(board))
                .collect(Collectors.toList());
    }


}
