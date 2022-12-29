package com.example.BankAdmin.aop;

import com.example.BankAdmin.dto.BankDto;
import com.example.BankAdmin.models.Request;
import com.example.BankAdmin.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestAOP {

    private final RequestRepository requestRepository;

    @Pointcut("execution(* com.example.BankAdmin.controlles.WebBankController.create(..))"
         + "|| execution(* com.example.BankAdmin.controlles.WebBankController.delete(..))")
    public void pointcut() {

    }


    @Around(value = "pointcut()")
    public Mono<BankDto> after(ProceedingJoinPoint joinPoint) throws Throwable {

        @SuppressWarnings("unchecked cast")
        Mono<BankDto> bankDtoMono = (Mono<BankDto>) joinPoint.proceed();

        return bankDtoMono.publishOn(Schedulers.boundedElastic()).doOnSuccess(
            bank -> {
                log.info("Successfully LOG in Mongo DB");

                requestRepository.insert(
                    Request.builder()
                        .method(joinPoint.getSignature().getName())
                        .bankId(bank.getIdBank())
                        .bankName(bank.getName())
                        .bankAddress(bank.getAddress())
                        .createdTime(LocalDateTime.now())
                        .build());
            }
        );
    }
}
