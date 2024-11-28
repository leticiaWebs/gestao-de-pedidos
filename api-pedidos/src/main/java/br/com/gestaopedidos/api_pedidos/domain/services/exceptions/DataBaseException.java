package br.com.gestaopedidos.api_pedidos.domain.services.exceptions;

public class DataBaseException extends RuntimeException{

    public DataBaseException(String msg){
        super(msg);
    }
}
