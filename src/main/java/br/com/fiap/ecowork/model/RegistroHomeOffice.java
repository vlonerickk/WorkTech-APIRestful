package br.com.fiap.ecowork.model;

import java.time.LocalDate;

public class RegistroHomeOffice {

    private Long id;
    private LocalDate dataTrabalho;
    private int horasTrabalhadas;
    private double co2Economizado; // Campo calculado ou persistido

    // Relacionamento: Todo registro pertence a um usuário
    private Usuario usuario;

    // Construtor Vazio
    public RegistroHomeOffice() {
    }

    // Construtor Cheio
    public RegistroHomeOffice(Long id, LocalDate dataTrabalho, int horasTrabalhadas, double co2Economizado, Usuario usuario) {
        this.id = id;
        this.dataTrabalho = dataTrabalho;
        this.horasTrabalhadas = horasTrabalhadas;
        this.co2Economizado = co2Economizado;
        this.usuario = usuario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataTrabalho() {
        return dataTrabalho;
    }

    public void setDataTrabalho(LocalDate dataTrabalho) {
        this.dataTrabalho = dataTrabalho;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public double getCo2Economizado() {
        return co2Economizado;
    }

    public void setCo2Economizado(double co2Economizado) {
        this.co2Economizado = co2Economizado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "RegistroHomeOffice{" +
                "id=" + id +
                ", data=" + dataTrabalho +
                ", co2=" + co2Economizado +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                '}';
    }
}