package br.com.gestaopedidos.api_pedidos.application.services.exceptions;

public class DataBaseException extends RuntimeException{

    public DataBaseException(String msg){
        super(msg);
    }
}
