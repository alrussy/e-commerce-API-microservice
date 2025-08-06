// package product_app.error;
//
// import java.time.ZonedDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.HashMap;
// import java.util.Map;
//
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.context.request.ServletWebRequest;
// import org.springframework.web.context.request.WebRequest;
//
// import io.swagger.v3.oas.annotations.Hidden;
// import jakarta.persistence.EntityNotFoundException;
//
//
// @RestControllerAdvice
// @Hidden
// public class ExceptionController {
//
//	    @ExceptionHandler(EntityNotFoundException.class)
//	    @ResponseStatus(code = HttpStatus.NOT_FOUND)
//	    public Map <String, Object> handleEntityNotFoundException(
//	            EntityNotFoundException e, WebRequest webRequest) {
//	    	var msg ="Entity Not Found ";
//	        return createExceptionMessage(msg, HttpStatus.NOT_FOUND, webRequest);
//	    }
//
//	    @ExceptionHandler(IllegalArgumentException.class)
//	    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	    public Map <String, Object> handleIllegalArgumentException(
//	            IllegalArgumentException e, WebRequest webRequest){
//	        return createExceptionMessage(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, webRequest);
//	    }
//
//
//
//	    @ExceptionHandler(Exception.class)
//	    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	    public Map <String, Object> handleOtherExptiton(
//	            Exception e, WebRequest webRequest){
//	        return createExceptionMessage(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, webRequest);
//	    }
//
//
//		@ExceptionHandler(MethodArgumentNotValidException.class)
//		@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//		public Map <String, Object> handleMethodArgumentNotValidExecption(
//				MethodArgumentNotValidException exception,WebRequest webRequest){
//			var resulMap=
//			 exception.getBindingResult().getFieldErrors().stream().map(t-> t.getField()+":"+t.getDefaultMessage()).reduce((t,
// u) ->t+" , "+u ).get();
//			 return createExceptionMessage(resulMap, HttpStatus.BAD_REQUEST, webRequest);
//		}
//
////	    ---------------- Being reworked
//
//
//	// alter to not just create the message but also log the error
//	// create method to log the error to an internal file
//	    private Map<String,Object> createExceptionMessage(String e, HttpStatus status, WebRequest webRequest) {
//
//	    Map <String, Object> error = new HashMap<>();
//	    String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
//
//	    if(webRequest instanceof ServletWebRequest){
//	        error.put("uri",
//	                ((ServletWebRequest)webRequest).getRequest().getRequestURI());
//	    }
//	    error.put("message", e);
//	    error.put("status code", status.value());
//	    error.put("timestamp", timestamp);
//	    error.put("reason", status.getReasonPhrase());
//	    return error;
//	    }
//	}
//
//
//
//
