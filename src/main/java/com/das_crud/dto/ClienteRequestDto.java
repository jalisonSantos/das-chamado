package com.das_crud.dto;

import jakarta.validation.constraints.*;

public class ClienteRequestDto {

    @NotBlank(message = "Campo obrigatório")
    @Size(max = 120, message = "Máximo de 120 caracteres")
    private String nome;

    @NotBlank(message = "Campo obrigatório")
    @Email(message = "E-mail inválido")
    @Size(max = 120, message = "Máximo de 120 caracteres")
    private String email;

    @Size(max = 30, message = "Máximo de 30 caracteres")
    private String telefone;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

}
