package br.com.fiap.ecowork.resource;

import br.com.fiap.ecowork.bo.EcoLogicBO;
import br.com.fiap.ecowork.dao.UsuarioDAO;
import br.com.fiap.ecowork.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/api/ecowork")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EcoWorkResource {

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    EcoLogicBO ecoLogicBO;

    @GET
    @Path("/usuarios")
    public Response listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            return Response.ok(usuarios).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro no banco: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/usuarios")
    public Response criarUsuario(Usuario usuario) {
        if (!ecoLogicBO.validarUsuario(usuario)) {
            return Response.status(400).entity("Dados inválidos").build();
        }
        try {
            usuarioDAO.inserir(usuario);
            return Response.status(201).entity("Usuário criado com sucesso").build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao inserir: " + e.getMessage()).build();
        }
    }

    //endpoint para o frontend.
    @GET
    @Path("/calculo-impacto/{distancia}/{dias}")
    public Response simularImpacto(@PathParam("distancia") Double distancia, @PathParam("dias") int dias) {
        Usuario u = new Usuario();
        u.setDistanciaTrabalhoKm(distancia);
        double co2Evitado = ecoLogicBO.calcularImpactoDiario(u);

        return Response.ok("{\"co2EvitadoKg\": " + co2Evitado + "}").build();
    }
}