package com.hdy.compose_examples.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hdy.compose_examples.ui.affirmations.AffirmationScreen
import com.hdy.compose_examples.ui.dice_roller.DiceRollerScreen
import com.hdy.compose_examples.ui.home.ExampleListScreen
import com.hdy.compose_examples.ui.hexagon.HexagonScreen
import com.hdy.compose_examples.ui.image_gallery.ImageGalleryScreen
import com.hdy.compose_examples.ui.survey.SurveyScreen
import com.hdy.compose_examples.ui.task_list.TaskListScreen
import com.hdy.compose_examples.ui.user_profile.UserProfileScreen

object Routes {
    const val HOME = "home"
    const val TASK_LIST = "task_list"
    const val USER_PROFILE = "user_profile"
    const val HEXAGON = "hexagon"
    const val SURVEY = "survey"
    const val IMAGE_GALLERY = "image_gallery"
    const val DICE_ROLLER = "dice_roller"
    const val AFFIRMATIONS = "affirmations"
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

        // 本地图片 + jetpack paging
        composable(Routes.IMAGE_GALLERY) {
            ImageGalleryScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Routes.AFFIRMATIONS) {
            AffirmationScreen (
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Routes.DICE_ROLLER) {
            DiceRollerScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}