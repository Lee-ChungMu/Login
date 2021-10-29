package com.kosa.login.controller;

import com.kosa.login.dto.BoardDTO;
import com.kosa.login.entity.Board;
import com.kosa.login.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    
    
    @GetMapping("/list")
    public String list(){
        log.info("list..............");
        return "board/list";
    }

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody BoardDTO boardDTO){
        log.info("-----------register-------------------------------");
        log.info(boardDTO);
        Long num = boardService.register(boardDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    //@PathVariable 을 사용해서 경로에 있는 board의 num 값을 얻어서 해당 board정보를 반환
    @GetMapping(value = "/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardDTO> read(@PathVariable("num") Long num){
        log.info("-----------read-------------------------------");
        log.info(num);
        return new ResponseEntity<>(boardService.get(num), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BoardDTO>> getList(String email){
        log.info("-----------getList-------------------------------");
        log.info(email);

        return new ResponseEntity<>(boardService.getAllWithWriter(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("num") Long num){
        log.info("-----------remove-------------------------------");
        log.info(num);
        boardService.remove(num);

        return new ResponseEntity<>("removed", HttpStatus.OK);
    }
    //제목은 바뀌는데 내용은 수정이 안될까......
    @PutMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody BoardDTO boardDTO){
        log.info("-----------modify-------------------------------");
        log.info(boardDTO);

        boardService.modify(boardDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }

}
