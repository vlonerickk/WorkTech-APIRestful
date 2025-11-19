package br.com.fiap.ecowork.bo;

import br.com.fiap.ecowork.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EcoLogicBO {

    // Regra de Negócio: Calcula quanto CO2 foi evitado
    // Fator médio: Carro a gasolina emite ~0.12kg de CO2 por km
    public double calcularImpactoAmbiental(Usuario usuario, int diasHomeOffice) {
        if (usuario.getDistanciaTrabalhoKm() == null) return 0.0;

        double co2PorKm = 0.12;
        // Distancia (ida e volta ja considerada no cadastro) * dias * fator
        return usuario.getDistanciaTrabalhoKm() * diasHomeOffice * co2PorKm;
    }

    public boolean validarUsuario(Usuario usuario) {
        return usuario.getEmail() != null && usuario.getEmail().contains("@");
    }
}