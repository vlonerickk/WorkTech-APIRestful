package br.com.fiap.ecowork.dao;

import br.com.fiap.ecowork.model.Usuario;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioDAO {

    @Inject
    AgroalDataSource dataSource; // Connection Factory injetada pelo Quarkus

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO T_EWA_USUARIO (id_usuario, nm_usuario, email, dist_km) VALUES (SEQ_USUARIO.nextval, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setDouble(3, usuario.getDistanciaTrabalhoKm());
            ps.executeUpdate();
        }
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_EWA_USUARIO ORDER BY id_usuario";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id_usuario"));
                u.setNome(rs.getString("nm_usuario"));
                u.setEmail(rs.getString("email"));
                u.setDistanciaTrabalhoKm(rs.getDouble("dist_km"));
                lista.add(u);
            }
        }
        return lista;
    }

    // Implementar métodos atualizar e deletar conforme necessidade do CRUD completo
}