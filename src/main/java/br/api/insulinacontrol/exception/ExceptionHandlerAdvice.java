package br.api.insulinacontrol.exception;

import br.api.insulinacontrol.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    private static Logger logger = Logger.getLogger(ExceptionHandlerAdvice.class.getName());

    @ExceptionHandler(exception.RegraNegocioException.class)
    public ResponseEntity<ExceptionDto> handleRegraNegocioException(exception.RegraNegocioException ex) {
        ExceptionDto dto = new ExceptionDto(ex.getMessage());
        logger.log(Level.SEVERE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(dto);
    }


//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(x -> x.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(status, errors);
//
//        return new ResponseEntity<>(apiErrorMessage, apiErrorMessage.getStatus());
//    }

}
