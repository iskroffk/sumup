package com.coding.challenge.sumup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class Command {

    @Getter
    @Setter
    @JsonProperty("name")
    private String name;

    @Getter
    @Setter
    @JsonProperty("command")
    private String command;
}