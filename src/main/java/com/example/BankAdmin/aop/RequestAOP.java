package com.example.BankAdmin.aop;

import com.example.BankAdmin.dto.BankDto;
import com.example.BankAdmin.models.Request;
import com.example.BankAdmin.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestAOP {

    private final RequestRepository requestRepository;

    @Pointcut("this(com.example.BankAdmin.controlles.WebBankController)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object after(ProceedingJoinPoint joinPoint) {
        Mono<BankDto> bankDtoMono = null;

        try {
            bankDtoMono = (Mono<BankDto>) joinPoint.proceed();

            bankDtoMono.subscribe(
                bank ->
                    requestRepository.insert(
                        Request.builder()
                            .method(joinPoint.getSignature().getName())
                            .bankId(bank.getIdBank())
                            .bankName(bank.getName())
                            .bankAddress(bank.getAddress())
                            .createdTime(LocalDateTime.now())
                            .build())
            );

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return bankDtoMono;
   }
}
