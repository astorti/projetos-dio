package me.dio.credit.application.system.controller

import me.dio.credit.application.system.dto.CreditDto
import me.dio.credit.application.system.dto.CustomerDto
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tools.jackson.databind.ObjectMapper
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditResourceTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var creditRepository: CreditRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/api/credits"
    }

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should save credit and return 201 status`() {
        //given
        val customer: Customer = customerRepository.save(builderCustomerDto().toEntity())
        val creditDto: CreditDto = buildCreditDTO(customerId = customer.id!!)
        //when
        val response = mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditDto))
        )
        //then
        response
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not save credit when invalid day first installment and return 400 status`() {
        //given
        val customer: Customer =
            customerRepository.save(builderCustomerDto().toEntity())

        val creditDto: CreditDto =
            buildCreditDTO(
                customerId = customer.id!!,
                dayFirstOfInstallment = LocalDate.now().plusMonths(4L)
            )

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditDto))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find all credits by customer id and return 200 status`() {
        //given
        val customer: Customer =
            customerRepository.save(builderCustomerDto().toEntity())

        val credit: Credit =
            creditRepository.save(buildCredit(customer = customer))

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .param("customerId", customer.id.toString())
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].creditCode")
                .value(credit.creditCode.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].creditValue")
                .value(500.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfInstallments")
                .value(5))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find credit by credit code and return 200 status`() {
        //given
        val customer: Customer =
            customerRepository.save(builderCustomerDto().toEntity())

        val credit: Credit =
            creditRepository.save(buildCredit(customer = customer))

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${credit.creditCode}")
                .param("customerId", customer.id.toString())
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditCode")
                .value(credit.creditCode.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue")
                .value(500.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallment")
                .value(5))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status")
                .value("IN_PROGRESS"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailCustomer")
                .value("asimov@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.incomeCustomer")
                .value(1000.0))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find credit when credit code does not exist and return 400 status`() {
        //given
        val customer: Customer =
            customerRepository.save(builderCustomerDto().toEntity())

        val creditCode = UUID.randomUUID()

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/$creditCode")
                .param("customerId", customer.id.toString())
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find credit when credit belongs to another customer and return 400 status`() {
        //given
        val customer: Customer =
            customerRepository.save(builderCustomerDto().toEntity())

        val credit: Credit =
            creditRepository.save(buildCredit(customer = customer))

        val anotherCustomer: Customer =
            customerRepository.save(
                builderCustomerDto(
                    firstName = "Isaac",
                    lastName = "Newton",
                    cpf = "22222222222",
                    email = "newton@email.com",
                    income = BigDecimal.valueOf(2000.0),
                    password = "1234",
                    zipCode = "00000-001",
                    street = "Newton Street"
                ).toEntity()
            )

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${credit.creditCode}")
                .param("customerId", anotherCustomer.id.toString())
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andDo(MockMvcResultHandlers.print())
    }

    private fun builderCustomerDto(
        firstName: String = "Isaac",
        lastName: String = "Asimov",
        cpf: String = "11111111111",
        email: String = "asimov@email.com",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        password: String = "1234",
        zipCode: String = "00000-000",
        street: String = "Asimov Street",
    ) = CustomerDto(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        income = income,
        password = password,
        zipCode = zipCode,
        street = street
    )

    private fun buildCreditDTO(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstOfInstallment: LocalDate = LocalDate.now().plusMonths(1L),
        numberOfInstallment: Int = 5,
        customerId: Long = 1L
    ) = CreditDto(
        creditValue = creditValue,
        dayFirstOfInstallment = dayFirstOfInstallment,
        numberOfInstallment = numberOfInstallment,
        customerId = customerId
    )

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(1L),
        numberOfInstallment: Int = 5,
        customer: Customer
    ) = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallment = numberOfInstallment,
        customer = customer
    )
}