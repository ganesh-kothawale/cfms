package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.auditlogs.repos.PsqlAuditLogsRepo
import `in`.porter.cfms.domain.auditlogs.repos.AuditLogRepo

@Module
abstract class AuditLogsModule {

    @Binds
    abstract fun bindAuditLogRepo(psqlAuditLogsRepo: PsqlAuditLogsRepo): AuditLogRepo
}
