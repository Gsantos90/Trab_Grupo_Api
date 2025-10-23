package org.serratec.Cleantech.exception;

public class InvalidCepException extends RuntimeException {
    public InvalidCepException(String cep) {
        super("CEP inválido: " + cep);
    }
}