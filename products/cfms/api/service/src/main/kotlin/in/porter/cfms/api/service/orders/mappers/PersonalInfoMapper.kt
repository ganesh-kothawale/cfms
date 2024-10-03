package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.PersonalInfo
import javax.inject.Inject
import `in`.porter.cfms.domain.orders.entities.PersonalInfo as DomainPersonalInfo

class PersonalInfoMapper
@Inject constructor() {
    fun map(personalInfo: PersonalInfo): DomainPersonalInfo {
        return DomainPersonalInfo(
            name = personalInfo.name,
            mobileNumber = personalInfo.mobileNumber
        )
    }

    fun fromDomain(domainPersonalInfo: DomainPersonalInfo): PersonalInfo {
        return PersonalInfo(
            name = domainPersonalInfo.name,
            mobileNumber = domainPersonalInfo.mobileNumber
        )

    }
}