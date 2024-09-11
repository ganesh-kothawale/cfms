package `in`.porter.kptr.servers.sqs.configs

import java.time.Duration

data class SQSDrainerConfig(
  val queueUrl: String,
  val fetcherConfig: FetcherConfig,
  val deleterConfig: DeleterConfig,
  val looperConfig: LooperConfig
) {

  data class FetcherConfig(
    val maxPolls: Int,
    val minLoopMessagesCnt: Int
  )

  data class DeleterConfig(
    val deleteMaxDelay: Duration
  )

  data class LooperConfig(
    val pauseDuration: Duration,
    val pauseWindowMultiplier: Double?
  )

}
