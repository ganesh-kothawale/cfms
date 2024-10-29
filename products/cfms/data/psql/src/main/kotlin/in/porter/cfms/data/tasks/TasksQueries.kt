package `in`.porter.cfms.data.tasks

import `in`.porter.cfms.data.hlp.HlpsTable
import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.pickuptasks.PickupTasksTable
import `in`.porter.cfms.data.pickuptasks.pickupimagemappings.PickupOrderMappingsTable
import `in`.porter.cfms.data.recon.ReconTable
import `in`.porter.cfms.data.tasks.mappers.ListTaskRowMapper
import `in`.porter.cfms.data.tasks.mappers.TaskRowMapper
import `in`.porter.cfms.data.tasks.records.ListTaskRecord
import `in`.porter.cfms.data.tasks.records.TaskRecord
import `in`.porter.cfms.domain.tasks.entities.DomainListTasksRequest
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.ZoneOffset
import javax.inject.Inject

class TasksQueries
@Inject
constructor(
    override val db: Database,
    override val dispatcher: CoroutineDispatcher,
    private val taskRowMapper: TaskRowMapper,
    private val listTaskRowMapper: ListTaskRowMapper
) : ExposedRepo {

    private val logger = LoggerFactory.getLogger(TasksQueries::class.java)

    // Retrieve tasks with pagination
    suspend fun findAll(request: DomainListTasksRequest, offset: Int): List<ListTaskRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching tasks with request: $request, offset: $offset")

        val query = TasksTable
            .join(PickupTasksTable, JoinType.LEFT, TasksTable.taskId, PickupTasksTable.taskId)
            .join(PickupOrderMappingsTable, JoinType.LEFT, PickupTasksTable.pickupTaskId, PickupOrderMappingsTable.pickupTaskId)
            .join(OrdersTable, JoinType.LEFT, PickupOrderMappingsTable.orderId, OrdersTable.orderId)
            .join(HlpsTable, JoinType.LEFT, PickupTasksTable.hlpId, HlpsTable.hlpOrderId)
            .join(ReconTable, JoinType.LEFT, TasksTable.taskId, ReconTable.taskId)  // Added ReconTable join
            .selectAll().apply {
                // Apply filters based on request parameters
                request.taskType?.let {
                    andWhere { TasksTable.flowType eq it }
                }

                // Franchise IDs with `or` condition for partial matching
                request.franchiseIds?.let { ids ->
                    andWhere {
                        ids.map { id -> PickupTasksTable.franchiseId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                // Order IDs with `or` condition for partial matching
                request.orderIds?.let { ids ->
                    andWhere {
                        ids.map { id -> OrdersTable.orderId like "%$id%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                // AWB Numbers with `or` condition for partial matching
                request.awbNumbers?.let { awbs ->
                    andWhere {
                        awbs.map { awb -> OrdersTable.awbNumber like "%$awb%" }
                            .reduce { acc, condition -> acc or condition }
                    }
                }

                // Task status
                request.taskStatus?.let {
                    andWhere { TasksTable.status eq it }
                }

                // Apply the created_at filter
                request.createdDate?.let {
                    andWhere { TasksTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                }

                // Apply the updated_at filter
                request.updatedDate?.let {
                    andWhere { TasksTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
                }
            }

            .orderBy(TasksTable.createdAt, SortOrder.DESC)
            .limit(request.size, offset)

        query.map { row ->
            logger.info("Mapping row: $row")
            listTaskRowMapper.toRecord(row)
        }
    }



    // Count total number of tasks in the database
    suspend fun countAll(request: DomainListTasksRequest): Int = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Counting tasks with request: $request")

        TasksTable
            .join(PickupTasksTable, JoinType.INNER, TasksTable.taskId, PickupTasksTable.taskId)
            .join(PickupOrderMappingsTable, JoinType.INNER, PickupTasksTable.pickupTaskId, PickupOrderMappingsTable.pickupTaskId)
            .join(OrdersTable, JoinType.INNER, PickupOrderMappingsTable.orderId, OrdersTable.orderId)
            .selectAll().apply {
            request.taskType?.let { andWhere { TasksTable.flowType eq it } }
            request.franchiseIds?.let { ids ->
                andWhere {
                    ids.map { id -> PickupTasksTable.franchiseId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.orderIds?.let { ids ->
                andWhere {
                    ids.map { id -> OrdersTable.orderId like "%$id%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.awbNumbers?.let { awbs ->
                andWhere {
                    awbs.map { awb -> OrdersTable.awbNumber like "%$awb%" }
                        .reduce { acc, condition -> acc or condition }
                }
            }
            request.taskStatus?.let { andWhere { TasksTable.status eq it } }
            request.createdDate?.let {
                andWhere { TasksTable.createdAt greaterEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }
            request.updatedDate?.let {
                andWhere { TasksTable.updatedAt lessEq it.atStartOfDay(ZoneOffset.UTC).toInstant() }
            }
        }.count().toInt()
    }

    // Retrieve tasks by their IDs
    suspend fun findByIds(taskIds: List<String>): List<TaskRecord> = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Fetching tasks with IDs: $taskIds")
        TasksTable
            .select { TasksTable.taskId inList taskIds }
            .map { row ->
                logger.info("Mapping row: $row")
                taskRowMapper.toRecord(row)
            }
    }

    // Update the status of tasks based on task IDs
    suspend fun updateStatus(taskIds: List<String>, status: String) = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Updating status for tasks with IDs: $taskIds to status: $status")
        TasksTable
            .update({ TasksTable.taskId inList taskIds }) {
                it[TasksTable.status] = status
                it[TasksTable.updatedAt] = Instant.now()
            }
        logger.info("Successfully updated status for tasks: $taskIds")
    }

    suspend fun insert(taskRecord: TaskRecord): String = transact {
        logger.info("Inserting a new task into the database")

        TasksTable.insert { row ->
            row[taskId] = taskRecord.taskId
            row[flowType] = taskRecord.flowType
            row[status] = taskRecord.status
            row[createdAt] = taskRecord.createdAt?: Instant.now()
            row[updatedAt] = taskRecord.updatedAt?: Instant.now()
        }
        taskRecord.taskId
    }

    suspend fun findByTaskId(taskId: String): TaskRecord? = transact {
        TasksTable
            .select { TasksTable.taskId eq taskId }
            .map { taskRowMapper.toRecord(it) }
            .firstOrNull()
    }

    suspend fun updateTask(taskRecord: TaskRecord): Unit = transact {
        logger.info("Updating task with ID: ${taskRecord.taskId}")

        TasksTable.update({ TasksTable.taskId eq taskRecord.taskId }) {
            it[flowType] = taskRecord.flowType
            it[status] = taskRecord.status
            it[updatedAt] = taskRecord.updatedAt?:Instant.now()
        }
    }

    suspend fun deleteTaskById(taskId: String): Unit = transact {
        addLogger(StdOutSqlLogger)
        logger.info("Deleting task with ID: $taskId")
        TasksTable.deleteWhere { TasksTable.taskId eq taskId }
    }
}
