package me.dio.credit.application.system.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.service.impl.CreditService
import me.dio.credit.application.system.service.impl.CustomerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@ExtendWith(MockKExtension::class)
class CreditServiceTest {
    @MockK
    lateinit var customerService: CustomerService
    @MockK
    lateinit var creditRepository: CreditRepository
    @InjectMockKs
    lateinit var creditService: CreditService


    @Test
    fun `should create credit`(){
        //given
        val fakeCustomer: Customer = buildCustomer()
        val fakeCredit: Credit = buildCredit(customer = fakeCustomer)
        val customerId: Long = fakeCustomer.id!!

        every { customerService.findById(customerId) } returns fakeCustomer
        every { creditRepository.save(fakeCredit) } returns fakeCredit
        //when
        val actualCredit: Credit = creditService.save(fakeCredit)
        //then
        Assertions.assertThat(actualCredit).isNotNull
        Assertions.assertThat(actualCredit).isSameAs(fakeCredit)
        verify(exactly = 1) { customerService.findById(customerId) }
        verify(exactly = 1) { creditRepository.save(fakeCredit) }
    }

    @Test
    fun `should not create credit when invalid day first installment and throw BusinessException`() {
        // given
        val fakeCustomer: Customer = buildCustomer()
        val fakeCredit: Credit = buildCredit(customer = fakeCustomer)
        val customerId: Long = fakeCustomer.id!!

        fakeCredit.dayFirstInstallment = LocalDate.now().plusMonths(4L)

        every { customerService.findById(customerId) } returns fakeCustomer

        // when / then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy {
                creditService.save(fakeCredit)
            }
            .withMessage("Invalid Date")

        verify(exactly = 0) {creditRepository.save(fakeCredit)}
    }

    @Test
    fun `should find all credits by customer`() {
        // given
        val customerId = 1L
        val fakeCustomer = buildCustomer(id = customerId)
        val fakeCredit = buildCredit(customer = fakeCustomer)
        val fakeCredits = listOf(fakeCredit)

        every { creditRepository.findAllByCustomerId(customerId)} returns fakeCredits

        // when
        val actual = creditService.findAllByCustomer(customerId)

        // then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isEqualTo(fakeCredits)

        verify(exactly = 1) {creditRepository.findAllByCustomerId(customerId)}
    }

    @Test
    fun `should find credit by credit code`() {
        // given
        val fakeCustomer = buildCustomer()
        val fakeCredit = buildCredit(customer = fakeCustomer)
        val creditCode = UUID.randomUUID()

        every {creditRepository.findByCreditCode(creditCode)} returns fakeCredit

        // when
        val actual = creditService.findByCreditCode(fakeCustomer.id!!, creditCode)

        // then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)

        verify(exactly = 1) {creditRepository.findByCreditCode(creditCode)}
    }

    @Test
    fun `should throw BusinessException when credit code does not exist`() {
        // given
        val creditCode = UUID.randomUUID()

        every {creditRepository.findByCreditCode(creditCode)} returns null

        // when
        // then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy {
                creditService.findByCreditCode(1L, creditCode)
            }
            .withMessage("Creditcode $creditCode not found")

        verify(exactly = 1) {creditRepository.findByCreditCode(creditCode)}
    }

    @Test
    fun `should throw IllegalArgumentException when credit belongs to another customer`() {
        // given
        val fakeCustomer = buildCustomer(id = 1L)
        val fakeCredit = buildCredit(customer = fakeCustomer)
        val creditCode = UUID.randomUUID()

        every {creditRepository.findByCreditCode(creditCode)} returns fakeCredit

        // when
        // then
        Assertions.assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                creditService.findByCreditCode(2L, creditCode)
            }
            .withMessage("Contact admin")

        verify(exactly = 1) {creditRepository.findByCreditCode(creditCode)}
    }

    companion object {
        fun buildCustomer(
            firstName: String = "Isaac",
            lastName: String = "Asimov",
            cpf: String = "11111111111",
            email: String = "isaac@email.com",
            password: String = "12345",
            zipCode: String = "00000-00",
            street: String = "Isaac Street",
            income: BigDecimal = BigDecimal.valueOf(1000.0),
            id: Long = 1L
        ) = Customer(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            address = Address(
                zipCode = zipCode,
                street = street,
            ),
            income = income,
            id = id
        )

        fun buildCredit(
            creditValue: BigDecimal = BigDecimal.valueOf(500.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(1L),
            numberOfInstallment: Int = 5,
            customer: Customer = CustomerServiceTest.buildCustomer()
        ): Credit = Credit(
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallment = numberOfInstallment,
            customer = customer
        )
    }
}