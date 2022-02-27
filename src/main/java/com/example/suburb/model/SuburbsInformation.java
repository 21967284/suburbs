package com.example.suburb.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SuburbsInformation {
    private List<String> names;
    private int totalNoOfCharacters;
}
