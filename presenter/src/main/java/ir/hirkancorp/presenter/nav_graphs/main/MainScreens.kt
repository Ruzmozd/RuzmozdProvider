package ir.hirkancorp.presenter.nav_graphs.main

sealed class MainScreens(val route: String) {

    companion object {
        const val JOB_ID = "jobId"
    }

    data object MainScreen : MainScreens(route = "main_screen")
    data object RequestsScreen : MainScreens(route = "requests_screen")
    data object JobProgressScreen : MainScreens(route = "job_progress_screen/{$JOB_ID}") {
        fun createRoute(jobId: Int) = "job_progress_screen/$jobId"
    }
}
