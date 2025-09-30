package com.hdy.compose_examples.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hdy.compose_examples.ui.task.ExampleListScreen
import com.hdy.compose_examples.ui.task.HexagonScreen
import com.hdy.compose_examples.ui.task.SurveyScreen
import com.hdy.compose_examples.ui.task.TaskListScreen
import com.hdy.compose_examples.ui.task.UserProfileScreen

object Routes {
    const val HOME = "home"
    const val TASK_LIST = "task_list"
    const val USER_PROFILE = "user_profile"
    const val HEXAGON = "hexagon"
    const val SURVEY = "survey"
}

/**
 * 应用导航配置
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // 首页
        composable(Routes.HOME) {
            ExampleListScreen(
                onExampleClick = { route ->
                    navController.navigate(route)
                }
            )
        }

        // 任务列表示例
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        // 用户资料示例
        composable(Routes.USER_PROFILE) {
            UserProfileScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        // 六边形
        composable(Routes.HEXAGON) {
            HexagonScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        // 问卷调查
        composable(Routes.SURVEY) {
            SurveyScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}