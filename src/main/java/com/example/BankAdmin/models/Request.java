package com.example.BankAdmin.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
public class Request {
    @Id
    private String id;
    private String method;
    //private String personalId;
    private Integer bankId;
    private String bankName;
    private String bankAddress;
    private LocalDateTime createdTime;
}
