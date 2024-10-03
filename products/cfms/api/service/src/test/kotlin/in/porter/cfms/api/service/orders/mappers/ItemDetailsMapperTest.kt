package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.service.orders.factories.ItemDetailsFactory
import `in`.porter.cfms.domain.orders.entities.ItemDetails as DomainItemDetails
import `in`.porter.cfms.api.models.orders.ItemDetails as ApiItemDetails
import io.mockk.clearAllMocks
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemDetailsMapperTest {

    private lateinit var itemDetailsMapper: ItemDetailsMapper
    private lateinit var dimensionsMapper: DimensionsMapper

    @BeforeAll
    fun init() {
        dimensionsMapper = DimensionsMapper()
        itemDetailsMapper = ItemDetailsMapper(dimensionsMapper)
    }

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `map api item details to domain item details`() {
        val apiItemDetails: ApiItemDetails = ItemDetailsFactory.buildApiItemDetails()
        val expectedDomainItemDetails: DomainItemDetails = ItemDetailsFactory.buildDomainItemDetails()

        val result: DomainItemDetails = itemDetailsMapper.map(apiItemDetails)

        Assertions.assertEquals(expectedDomainItemDetails.materialType, result.materialType)
        Assertions.assertEquals(expectedDomainItemDetails.materialWeight, result.materialWeight)
        Assertions.assertEquals(expectedDomainItemDetails.dimensions, result.dimensions)
    }
}