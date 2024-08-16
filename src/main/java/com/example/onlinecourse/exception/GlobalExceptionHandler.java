package com.example.onlinecourse.exception;
import com.example.onlinecourse.Dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ResponseDto> handleRuntimeException(RuntimeException e) {
        ResponseDto responseDTO = new ResponseDto();
        responseDTO.setStatus("ERROR");
        responseDTO.setMessage(e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e.getMessage().contains("Course not found")) {
            status = HttpStatus.NOT_FOUND;
        } else if (e.getMessage().contains("Not enough slots available")) {
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(status).body(responseDTO);
    }
}