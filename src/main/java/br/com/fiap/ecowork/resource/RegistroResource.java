package br.com.fiap.ecowork.resource;

import br.com.fiap.ecowork.bo.EcoLogicBO;
import br.com.fiap.ecowork.dao.RegistroDAO;
import br.com.fiap.ecowork.dao.UsuarioDAO;
import br.com.fiap.ecowork.dto.RegistroDTO;
import br.com.fiap.ecowork.model.RegistroHomeOffice;
import br.com.fiap.ecowork.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/api/ecowork/registros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistroResource {

    @Inject
    RegistroDAO registroDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    EcoLogicBO ecoLogicBO;

    @POST
    public Response registrarHomeOffice(RegistroDTO dto) {
        try {
            // 1. Buscar o usuário no banco para pegar a distância dele
            Usuario usuario = usuarioDAO.buscarPorId(dto.idUsuario);

            if (usuario == null) {
                return Response.status(404).entity("Usuário não encontrado").build();
            }

            // 2. Calcular o CO2 economizado neste dia
            double co2Economizado = ecoLogicBO.calcularImpactoDiario(usuario);

            // 3. Montar o objeto Registro
            RegistroHomeOffice registro = new RegistroHomeOffice();
            registro.setUsuario(usuario);
            registro.setDataTrabalho(dto.dataTrabalho);
            registro.setHorasTrabalhadas(dto.horasTrabalhadas);
            registro.setCo2Economizado(co2Economizado); // Aqui entra o cálculo automático!

            // 4. Salvar no banco
            registroDAO.inserir(registro);

            return Response.status(201).entity(registro).build();

        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao salvar registro: " + e.getMessage()).build();
        }
    }

    @GET
    public Response listarTodos() {
        try {
            List<RegistroHomeOffice> lista = registroDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao listar: " + e.getMessage()).build();
        }
    }
}