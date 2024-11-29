package com.pomingmatgo.authservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private String identifier;
    private LoginType loginType;
}
