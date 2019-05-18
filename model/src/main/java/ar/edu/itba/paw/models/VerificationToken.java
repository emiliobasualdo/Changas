package ar.edu.itba.paw.models;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    private long tokenId;
    private String token;
    private long userId;
    private Date expiryDate;

//    public VerificationToken(String token, User user) {
//        this.expiryDate = calculateExpiryDate(EXPIRATION);
//        this.token = token;
//        this.user = user;
//    }

    public VerificationToken(String token, long userId, Date expiryDate) {
        this.expiryDate = expiryDate;
        this.token = token;
        this.userId = userId;
    }

    public static class Builder {

        private String token;
        private long userId;
        private Date expiryDate;

        public Builder (String token, long userId) {
            this.expiryDate = calculateExpiryDate(EXPIRATION);
            this.token = token;
            this.userId = userId;
        }

        private Date calculateExpiryDate(int expiryTimeInMinutes) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Timestamp(cal.getTime().getTime()));
            cal.add(Calendar.MINUTE, expiryTimeInMinutes);
            return new Date(cal.getTime().getTime());
        }

        public String getToken() {
            return token;
        }

        public long getUserId() {
            return userId;
        }

        public Date getExpiryDate() {
            return expiryDate;
        }

    }

    public VerificationToken(VerificationToken.Builder tokenBuilder) {
        this.expiryDate = tokenBuilder.getExpiryDate();
        this.token = tokenBuilder.getToken();
        this.userId = tokenBuilder.getUserId();
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long id) {
        this.tokenId = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUser(long userId) {
        this.userId = userId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }


    @Override
    public String toString() {
        return "VerificationToken{" +
                "tokenId=" + tokenId +
                ", token='" + token + '\'' +
                ", userId=" + userId +
                ", expiryDate=" + expiryDate +
                '}';
    }

}
