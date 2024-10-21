package `in`.porter.cfms.domain.tasks.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.tasks.entities.Tasks
import `in`.porter.cfms.domain.tasks.repos.TasksRepo
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class GetTasks @Inject constructor(
    private val tasksRepo: TasksRepo
) : Traceable{

    suspend fun findTaskById(taskId : String): Tasks =
        trace(){
            tasksRepo.findTaskById(taskId)
                ?: throw CfmsException("Task with ID $taskId not found")
        }
}