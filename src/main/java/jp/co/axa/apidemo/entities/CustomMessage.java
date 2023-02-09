package jp.co.axa.apidemo.entities;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomMessage {
    private Integer error;
    
    @NotNull
    private String message;
    
    private String details;
}