package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.VerificationTokenDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ar.edu.itba.paw.constants.DBTableName.users;
import static ar.edu.itba.paw.constants.DBUserFields.user_id;

@Repository
public class VerificationTokenJdbcDao implements VerificationTokenDao {

    private final static RowMapper<VerificationToken> ROW_MAPPER = (rs, rowNum) -> verificationTokenFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public VerificationTokenJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("verification_token")
                .usingGeneratedKeyColumns("token_id");
    }


    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return jdbcTemplate.query(String.format("SELECT * FROM %s WHERE %s = ?", "verification_token", "token"), ROW_MAPPER, token).stream().findAny();
    }

    @Override
    public void save(VerificationToken.Builder tokenBuilder) {
        Number tokenId;
        Map<String, Object> tokenRow = tokenToTableRow(tokenBuilder);
        tokenId = jdbcInsert.executeAndReturnKey(tokenRow);

    }

    private Map<String, Object> tokenToTableRow(VerificationToken.Builder tokenBuilder) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("token", tokenBuilder.getToken());
        resp.put("user_id", tokenBuilder.getUserId());
        resp.put("expiry_date", tokenBuilder.getExpiryDate());
        return resp;
    }



    private static VerificationToken verificationTokenFromRS(ResultSet rs) throws SQLException {
        return new VerificationToken(rs.getString("token"), rs.getLong("user_id"), rs.getDate("expiry_date"));

    }
}
