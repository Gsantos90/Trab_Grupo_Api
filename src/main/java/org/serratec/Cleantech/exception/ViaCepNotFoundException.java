package org.serratec.Cleantech.exception;
public class ViaCepNotFoundException extends RuntimeException {
    public ViaCepNotFoundException(String cep) { super("CEP não encontrado: " + cep); }
}
