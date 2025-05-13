package davo.demo_libros.controllers;


import davo.demo_libros.Dto.LoginRequest;
import davo.demo_libros.services.SupabaseAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SupabaseAuthService supabaseAuthService;

    public AuthController(SupabaseAuthService supabaseAuthService) {
        this.supabaseAuthService = supabaseAuthService;
    }

    @PostMapping("/login")
    // ¡Cambia el tipo de retorno a Mono<ResponseEntity<Object>>!
    public Mono<ResponseEntity<Object>> login(@RequestBody LoginRequest loginRequest) {
        return supabaseAuthService.signIn(loginRequest)
                // Esto produce Mono<ResponseEntity<Object>>
                .map(response -> ResponseEntity.<Object>ok(response))
                // Esto produce Mono<ResponseEntity<Object>>
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).<Object>body(e.getMessage()))
                );
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<Object>> register(@RequestBody LoginRequest signupRequest) {
        return supabaseAuthService.signUp(signupRequest)
                .map(response -> ResponseEntity.<Object>ok(response))
                .onErrorResume(RuntimeException.class, e ->
                        // *** ¡Añade Mono.just(...) aquí! ***
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Object)e.getMessage()))
                );
    }

}