package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.ItemDetailsFactory
import `in`.porter.cfms.domain.orders.entities.Dimensions as DomainDimensions
import `in`.porter.cfms.api.models.orders.Dimensions as ApiDimensions
import io.mockk.clearAllMocks
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DimensionsMapperTest {

    private lateinit var dimensionsMapper: DimensionsMapper

    @BeforeAll
    fun init() {
        dimensionsMapper = DimensionsMapper()
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map api dimensions to domain dimensions`() {
        val apiDimensions: ApiDimensions = ItemDetailsFactory.buildApiDimensions()
        val expectedDomainDimensions: DomainDimensions = ItemDetailsFactory.buildDomainDimensions()

        val result: DomainDimensions = dimensionsMapper.map(apiDimensions)

        Assertions.assertEquals(expectedDomainDimensions.length, result.length)
        Assertions.assertEquals(expectedDomainDimensions.breadth, result.breadth)
        Assertions.assertEquals(expectedDomainDimensions.height, result.height)
    }
}