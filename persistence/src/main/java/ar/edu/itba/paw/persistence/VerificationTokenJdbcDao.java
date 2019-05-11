package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
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
import static ar.edu.itba.paw.constants.DBTableName.verification_token;
import static ar.edu.itba.paw.constants.DBVerificationTokenFields.*;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;

@Repository
public class VerificationTokenJdbcDao implements VerificationTokenDao {

    private final static RowMapper<VerificationToken> ROW_MAPPER = (rs, rowNum) -> verificationTokenFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public VerificationTokenJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(verification_token.name())
                .usingGeneratedKeyColumns(token_id.name());
    }


    @Override
    public Either<VerificationToken, Validation> findByToken(String tok) {
        Optional<VerificationToken> optional = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?", verification_token.name(), token.name()),
                ROW_MAPPER, tok).stream().findAny();
        if(!optional.isPresent()) {
            return Either.alternative(new Validation(INEXISTENT_TOKEN));
        }
        return Either.value(optional.get());
    }

    @Override
    public void save(VerificationToken.Builder tokenBuilder) {
        Map<String, Object> tokenRow = tokenToTableRow(tokenBuilder);
        jdbcInsert.execute(tokenRow);

    }

    @Override
    public void delete(final long tokenId) {

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
