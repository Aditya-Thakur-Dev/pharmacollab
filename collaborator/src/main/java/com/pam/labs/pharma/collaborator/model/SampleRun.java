package com.pam.labs.pharma.collaborator.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pam.labs.pharma.collaborator.util.CustomLocalDateTimeDeserializer;
import com.pam.labs.pharma.collaborator.util.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

@lombok.Getter
@lombok.Setter
@lombok.Builder
public class SampleRun {
    private String userName;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime runtime;
}
