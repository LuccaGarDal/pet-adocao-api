package br.com.lucca.pet_adocao_api.model.usuario;

public enum CargoUsuario {
    ADMIN("admin"),
    USUARIO ("usuario");

    private String cargo;

    CargoUsuario(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo () {
        return cargo;
    }
}
