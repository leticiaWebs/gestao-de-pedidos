package br.com.gestaopedidos.api_pedidos.application.services.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataBaseExceptionTest {
    @Test
    void testExceptionMessage() {
        // Defina a mensagem que você espera na exceção
        String errorMessage = "Erro de banco de dados";

        // Lance a exceção e verifique a mensagem
        DataBaseException exception = assertThrows(DataBaseException.class, () -> {
            throw new DataBaseException(errorMessage);
        });

        // Verifique se a mensagem da exceção está correta
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionMessageIsNotNull() {
        String errorMessage = "Erro de banco de dados";

        // Lança a exceção e verifica se a mensagem não é nula
        DataBaseException exception = assertThrows(DataBaseException.class, () -> {
            throw new DataBaseException(errorMessage);
        });

        // Verifique se a mensagem não é nula
        assertEquals("Erro de banco de dados", exception.getMessage());
    }
}
