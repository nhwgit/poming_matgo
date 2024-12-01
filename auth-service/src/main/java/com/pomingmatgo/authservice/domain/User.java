package com.pomingmatgo.authservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private long id;
    private String identifier;
    private LoginType loginType;
}
