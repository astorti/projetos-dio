package me.dio.credit.application.system.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.Column

@Embeddable
data class Address (
    @Column(nullable = false) var zipCode: String = "",
    @Column(nullable = false) var street: String = ""
)
