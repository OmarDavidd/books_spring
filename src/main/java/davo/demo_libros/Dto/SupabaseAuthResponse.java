package davo.demo_libros.Dto;

// Representa (una parte simplificada de) la respuesta de Supabase al iniciar sesión
// Supabase incluye más campos como refresh_token, user, etc. Puedes añadir los que necesites.
public class SupabaseAuthResponse {
    private String access_token; // Este es el JWT
    private String token_type;
    private Long expires_in; // En segundos
    private String refresh_token;
    // Agrega un campo para el objeto user si lo necesitas, por ejemplo:
    // private SupabaseUser user;

    // Getters y setters
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
