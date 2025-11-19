package br.com.fiap.ecowork.model;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private Double distanciaTrabalhoKm; //Distância ida e volta

    // Construtor Vazio
    public Usuario() {}

    public Usuario(Long id, String nome, String email, Double distanciaTrabalhoKm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.distanciaTrabalhoKm = distanciaTrabalhoKm;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Double getDistanciaTrabalhoKm() {
        return distanciaTrabalhoKm;
    }
    public void setDistanciaTrabalhoKm(Double distanciaTrabalhoKm) {
        this.distanciaTrabalhoKm = distanciaTrabalhoKm;
    }
}