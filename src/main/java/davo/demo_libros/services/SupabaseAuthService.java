package davo.demo_libros.services;

import davo.demo_libros.Dto.LoginRequest;
import davo.demo_libros.Dto.SupabaseAuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class SupabaseAuthService {

    private final WebClient webClient;
    private final String supabaseBaseAuthUrl;

    public SupabaseAuthService(@Value("${supabase.url}") String supabaseUrl,
                               @Value("${supabase.anon-key}") String anonKey) {
        this.supabaseBaseAuthUrl = supabaseUrl + "/auth/v1";
        this.webClient = WebClient.builder()
                .baseUrl(this.supabaseBaseAuthUrl)
                .defaultHeader("apikey", anonKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<SupabaseAuthResponse> signIn(LoginRequest loginRequest) {
        String fullUrlBeingCalled = this.supabaseBaseAuthUrl + "/token?grant_type=password";
        System.out.println(">>> Intentando llamar a la URL de Supabase para sign_in (token grant_type=password): " + fullUrlBeingCalled);

        return webClient.post()
                .uri("/token?grant_type=password")
                .body(BodyInserters.fromValue(loginRequest))
                .retrieve()
                .bodyToMono(SupabaseAuthResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    System.err.println("Error en Supabase signIn: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
                    String errorMessage = "Error de autenticación";
                    try {
                        errorMessage = e.getResponseBodyAsString();
                    } catch (Exception ex) {
                        // Ignora si no se puede leer el cuerpo
                    }
                    return Mono.error(new RuntimeException("Error de autenticación: " + errorMessage)); // Propaga el error con más detalle si es posible
                });
    }


    public Mono<SupabaseAuthResponse> signUp(LoginRequest signupRequest) { // Reutilizamos LoginRequest para email/password
        String fullUrl = this.supabaseBaseAuthUrl + "/sign_up";
        System.out.println(">>> Intentando llamar a la URL de Supabase para sign_up: " + fullUrl);

        return webClient.post()
                .uri("/signup") // Endpoint específico para registrar
                .body(BodyInserters.fromValue(signupRequest)) // Envía email, password y opcionalmente 'data'
                .retrieve()
                .bodyToMono(SupabaseAuthResponse.class) // La respuesta incluye tokens y el objeto user
                .onErrorResume(WebClientResponseException.class, e -> {
                    System.err.println("Error en Supabase signUp: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
                    String errorMessage = "Error de registro";
                    try { errorMessage = e.getResponseBodyAsString(); } catch (Exception ex) {}
                    return Mono.error(new RuntimeException("Error de registro: " + errorMessage)); // Propaga el error
                });
    }
}
