package `in`.porter.cfms.data.di

import dagger.Binds
import dagger.Module
import `in`.porter.cfms.data.tasks.repos.PsqlTasksRepo
import `in`.porter.cfms.domain.tasks.repos.TasksRepo

@Module
abstract class TasksModule {

    @Binds
    abstract fun bindTaskRepo(psqlTasksRepo: PsqlTasksRepo): TasksRepo
}
