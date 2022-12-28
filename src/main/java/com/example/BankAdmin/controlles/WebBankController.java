package com.example.BankAdmin.controlles;

import com.example.BankAdmin.dto.AuthenticationRequest;
import com.example.BankAdmin.dto.BankDto;
import com.example.BankAdmin.services.WebBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/bank", method = RequestMethod.GET)
@RequiredArgsConstructor
public class WebBankController {

    private final WebBankService webBankService;

    @PostMapping("/create")
    public Mono<BankDto> create(@RequestBody BankDto bank,
                                @RequestHeader (HttpHeaders.AUTHORIZATION) String token) {
        token = token.substring(7);
        return webBankService.create(bank, token);
    }

    @DeleteMapping("/delete")
    public Mono<BankDto> delete(@RequestParam Integer idBank,
                               @RequestHeader (HttpHeaders.AUTHORIZATION) String token) {
        token = token.substring(7);
        return webBankService.delete(idBank, token);
    }

    @PostMapping("/auth")
    public Mono<String> auth(@RequestBody AuthenticationRequest auth) {
        return webBankService.auth(auth);
    }
}
