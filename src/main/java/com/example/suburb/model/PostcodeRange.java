package com.example.suburb.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostcodeRange {
    private int start;
    private int end;
}
