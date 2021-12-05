package pl.kulbat.monitorowaniejednostekmorskich.model;

public record TokenRequest(String client_id, String scope, String client_secret, String grant_type) {
    public TokenRequest() {
        this("cirowes839@slvlog.com:myFirstClient", "api", "cirowes839@slvlog.com", "client_credentials");
    }
}
