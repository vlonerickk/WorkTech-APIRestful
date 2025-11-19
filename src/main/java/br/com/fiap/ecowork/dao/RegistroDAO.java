package br.com.fiap.ecowork.dao;

import br.com.fiap.ecowork.model.RegistroHomeOffice;
import br.com.fiap.ecowork.model.Usuario;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RegistroDAO {

    @Inject
    AgroalDataSource dataSource;

    // 1. INSERIR UM NOVO REGISTRO
    public void inserir(RegistroHomeOffice registro) throws SQLException {
        String sql = "INSERT INTO T_EWA_REGISTRO (id_registro, dt_trabalho, horas, co2_poupado, id_usuario) " +
                "VALUES (SEQ_REGISTRO.nextval, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Converter LocalDate do Java para Date do SQL
            ps.setDate(1, Date.valueOf(registro.getDataTrabalho()));
            ps.setInt(2, registro.getHorasTrabalhadas());
            ps.setDouble(3, registro.getCo2Economizado());

            // Pega o ID do usuário que está dentro do objeto Registro
            if (registro.getUsuario() != null) {
                ps.setLong(4, registro.getUsuario().getId());
            } else {
                throw new SQLException("Usuário é obrigatório para salvar o registro.");
            }

            ps.executeUpdate();
        }
    }

    // 2. LISTAR TODOS OS REGISTROS (COM JOIN PARA PEGAR NOME DO USUARIO)
    public List<RegistroHomeOffice> listarTodos() throws SQLException {
        List<RegistroHomeOffice> lista = new ArrayList<>();

        // Fazemos o JOIN para preencher o objeto Usuario dentro do Registro
        String sql = "SELECT r.id_registro, r.dt_trabalho, r.horas, r.co2_poupado, " +
                "       u.id_usuario, u.nm_usuario, u.email " +
                "FROM T_EWA_REGISTRO r " +
                "JOIN T_EWA_USUARIO u ON r.id_usuario = u.id_usuario " +
                "ORDER BY r.dt_trabalho DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroHomeOffice reg = mapearResultSet(rs);
                lista.add(reg);
            }
        }
        return lista;
    }

    // 3. LISTAR REGISTROS DE UM USUÁRIO ESPECÍFICO (Útil para o Dashboard pessoal)
    public List<RegistroHomeOffice> listarPorIdUsuario(Long idUsuario) throws SQLException {
        List<RegistroHomeOffice> lista = new ArrayList<>();
        String sql = "SELECT r.id_registro, r.dt_trabalho, r.horas, r.co2_poupado, " +
                "       u.id_usuario, u.nm_usuario, u.email " +
                "FROM T_EWA_REGISTRO r " +
                "JOIN T_EWA_USUARIO u ON r.id_usuario = u.id_usuario " +
                "WHERE u.id_usuario = ? " +
                "ORDER BY r.dt_trabalho DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistroHomeOffice reg = mapearResultSet(rs);
                    lista.add(reg);
                }
            }
        }
        return lista;
    }

    // Método auxiliar para não repetir código de mapeamento
    private RegistroHomeOffice mapearResultSet(ResultSet rs) throws SQLException {
        RegistroHomeOffice reg = new RegistroHomeOffice();

        // Dados do Registro
        reg.setId(rs.getLong("id_registro"));
        reg.setDataTrabalho(rs.getDate("dt_trabalho").toLocalDate());
        reg.setHorasTrabalhadas(rs.getInt("horas"));
        reg.setCo2Economizado(rs.getDouble("co2_poupado"));

        // Dados do Usuário (Preenchemos o objeto Usuario associado)
        Usuario u = new Usuario();
        u.setId(rs.getLong("id_usuario"));
        u.setNome(rs.getString("nm_usuario"));
        u.setEmail(rs.getString("email"));

        // Vincula o usuário ao registro
        reg.setUsuario(u);

        return reg;
    }
}