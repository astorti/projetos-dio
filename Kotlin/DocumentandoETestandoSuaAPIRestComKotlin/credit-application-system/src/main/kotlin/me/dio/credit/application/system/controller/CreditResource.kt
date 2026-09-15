package me.dio.credit.application.system.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import me.dio.credit.application.system.dto.CreditDto
import me.dio.credit.application.system.dto.CreditView
import me.dio.credit.application.system.dto.CreditViewList
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.service.impl.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditResource(
    private val creditService: CreditService,
) {
    @Operation(
        summary = "Criar um novo crédito",
        description = "Gera um novo crédito para um cliente identificado pelo numberID"
    )
    @PostMapping
    fun saveCredit(@RequestBody @Valid creditDto: CreditDto): ResponseEntity<String> {
        val credit: Credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Credit ${credit.creditCode} - Customer ${credit.customer.firstName} saved!")
    }

    @Operation(
        summary = "Buscar vários créditos",
        description = "Encontra todos os créditos registrados para um específico cliente"
    )
    @GetMapping
    fun findAllByCustomerId(
        @Parameter(description = "ID do cliente")
        @RequestParam(value = "customerId") customerId: Long):
            ResponseEntity<List<CreditViewList>> {
        val creditViewList: List<CreditViewList> = this.creditService.findAllByCustomer(customerId).stream()
            .map { credit: Credit -> CreditViewList(credit) }
            .collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(creditViewList)
    }

    @Operation(
        summary = "Buscar um crédito",
        description = "Encontra no banco de dados um crédito específico através do número do crédito e do id do cliente"
    )
    @GetMapping("/{creditCode}")
    fun findByCreditCode(
        @Parameter(description = "Número do crédito / ID do cliente")
        @RequestParam(value = "customerId") customerId: Long,
        @PathVariable creditCode: UUID
    ): ResponseEntity<CreditView> {
        val credit: Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))
    }
}