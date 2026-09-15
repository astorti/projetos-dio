package me.dio.credit.application.system.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import me.dio.credit.application.system.enumaration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "credit")
data class Credit (
    @Column(nullable = false, unique = true) var creditCode: UUID = UUID.randomUUID(),
    @Column(nullable = false) var creditValue: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false) var dayFirstInstallment: LocalDate = LocalDate.now(),
    @Column(nullable = false) var numberOfInstallment: Int = 0,
    @Enumerated(EnumType.STRING) var status: Status = Status.IN_PROGRESS,
    @ManyToOne var customer: Customer = Customer(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)
