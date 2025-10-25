package org.serratec.Cleantech.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

		String detailMessage = "Um valor já existente (CPF/E-mail) foi inserido, ou um campo obrigatório não foi preenchido.";

		if (ex.getCause() != null && ex.getCause().getMessage().contains("viola a restrição de unicidade")) {
			if (ex.getCause().getMessage().contains("cpf")) {
				detailMessage = "O CPF informado já está registrado em nossa base de dados e não pode ser reutilizado.";
			} else if (ex.getCause().getMessage().contains("email")) {
				detailMessage = "O E-mail informado já está registrado em nossa base de dados e não pode ser reutilizado.";
			} else {
				detailMessage = "Violação de Unicidade: O valor inserido para um campo único já existe.";
			}
		}

		ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), "Conflito de Dados", detailMessage);

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Recurso não encontrado",
				ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Falha de Validação", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> new ErrorResponse.FieldError(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());

		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Erros de Validação de Campo",
				"A requisição contém um ou mais campos inválidos. Verifique a lista de erros.", fieldErrors);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Dados inválidos", ex.getMessage());
		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleJsonParsingException(HttpMessageNotReadableException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "JSON Inválido",
				"O corpo da requisição está mal formado (ex: erro de sintaxe JSON ou tipo de dado incorreto).");
		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do servidor",
				ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado.");
		return ResponseEntity.internalServerError().body(error);
	}

	public static class ErrorResponse {
		private LocalDateTime timestamp;
		private int status;
		private String error;
		private String message;
		private List<FieldError> errors;

		public ErrorResponse(int status, String error, String message) {
			this.timestamp = LocalDateTime.now();
			this.status = status;
			this.error = error;
			this.message = message;
			this.errors = null;
		}

		public ErrorResponse(int status, String error, String message, List<FieldError> errors) {
			this.timestamp = LocalDateTime.now();
			this.status = status;
			this.error = error;
			this.message = message;
			this.errors = errors;
		}

		public static class FieldError {
			private String field;
			private String message;

			public FieldError(String field, String message) {
				this.field = field;
				this.message = message;
			}

			public String getField() {
				return field;
			}

			public String getMessage() {
				return message;
			}

			public void setField(String field) {
				this.field = field;
			}

			public void setMessage(String message) {
				this.message = message;
			}
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public int getStatus() {
			return status;
		}

		public String getError() {
			return error;
		}

		public String getMessage() {
			return message;
		}

		public List<FieldError> getErrors() {
			return errors;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public void setError(String error) {
			this.error = error;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setErrors(List<FieldError> errors) {
			this.errors = errors;
		}
	}
}