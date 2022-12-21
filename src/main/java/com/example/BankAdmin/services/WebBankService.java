package com.example.BankAdmin.services;

import com.example.BankAdmin.dto.AuthenticationRequest;
import com.example.BankAdmin.dto.BankDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class WebBankService {

    private final WebClient webClient;

    public WebBankService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<BankDto> create(BankDto bank, String token) {
        return webClient.post()
                .uri("/bank/add")
                .headers(h -> h.setBearerAuth(token))
                .body(Mono.just(bank), BankDto.class)
                .retrieve()
                .bodyToMono(BankDto.class);
    }

    public Mono<String> delete(Integer idBank, String token) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/bank/delete")
                        .queryParam("idBank", idBank)
                        .build())
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> auth(AuthenticationRequest authDTO) {
        return webClient.post()
                .uri("/auth/authenticate")
                .body(Mono.just(authDTO), AuthenticationRequest.class)
                .retrieve()
                .bodyToMono(String.class);
    }
}
