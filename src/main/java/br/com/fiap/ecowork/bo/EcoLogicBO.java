package br.com.fiap.ecowork.bo;

import br.com.fiap.ecowork.model.RegistroHomeOffice;
import br.com.fiap.ecowork.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EcoLogicBO {

    // Regra de Negócio: Calcula CO2 para UM dia de trabalho
    public double calcularImpactoDiario(Usuario usuario) {
        if (usuario.getDistanciaTrabalhoKm() == null) return 0.0;

        double co2PorKm = 0.12; // Fator de emissão médio (kg CO2/km)
        double idaEVolta = 2.0;

        // Distância * 2 (Ida e volta) * Fator de emissão
        return usuario.getDistanciaTrabalhoKm() * idaEVolta * co2PorKm;
    }

    public boolean validarUsuario(Usuario usuario) {
        return usuario.getEmail() != null && usuario.getEmail().contains("@");
    }
}