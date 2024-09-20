package orders.mappers

import `in`.porter.cfms.api.models.orders.PersonalInfo
import `in`.porter.cfms.domain.orders.entities.PersonalInfo as DomainPersonalInfo

class PersonalInfoMapper {
    fun map(personalInfo: PersonalInfo): DomainPersonalInfo {
        return DomainPersonalInfo(
            name = personalInfo.name,
            mobileNumber = personalInfo.mobileNumber
        )
    }
}