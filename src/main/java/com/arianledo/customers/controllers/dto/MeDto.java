package com.arianledo.customers.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public record MeDto(@NotBlank String username,
                    @NotBlank String emailUser,
                    @NotBlank String businessEntityName,
                    @NotBlank Long businessEntityId) {
}
