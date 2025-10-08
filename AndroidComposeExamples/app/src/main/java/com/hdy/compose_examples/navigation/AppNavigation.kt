package com.hdy.compose_examples.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hdy.compose_examples.ui.affirmations.AffirmationScreen
import com.hdy.compose_examples.ui.art_space.ArtSpaceScreen
import com.hdy.compose_examples.ui.birthday_card.BirthdayCardScreen
import com.hdy.compose_examples.ui.business_card.BusinessCardScreen
import com.hdy.compose_examples.ui.cupcake.CupcakeScreen
import com.hdy.compose_examples.ui.dessert.DessertScreen
import com.hdy.compose_examples.ui.dice_roller.DiceRollerScreen
import com.hdy.compose_examples.ui.guess_word.GuessWordScreen
import com.hdy.compose_examples.ui.hexagon.HexagonScreen
import com.hdy.compose_examples.ui.home.ExampleListScreen
import com.hdy.compose_examples.ui.image_gallery.ImageGalleryScreen
import com.hdy.compose_examples.ui.material.MaterialScreen
import com.hdy.compose_examples.ui.race_tracker.RaceTrackerScreen
import com.hdy.compose_examples.ui.reply.ReplyScreen
import com.hdy.compose_examples.ui.survey.SurveyScreen
import com.hdy.compose_examples.ui.task_list.TaskListScreen
import com.hdy.compose_examples.ui.tip_calculator.TipCalculatorScreen
import com.hdy.compose_examples.ui.topic.TopicScreen
import com.hdy.compose_examples.ui.user_profile.UserProfileScreen

object Routes {  // route: 与路线名称对应的字符串
    const val HOME = "home"
    const val TASK_LIST = "task_list"
    const val USER_PROFILE = "user_profile"
    const val HEXAGON = "hexagon"
    const val SURVEY = "survey"
    const val IMAGE_GALLERY = "image_gallery"
    const val BIRTHDAY_CARD = "birthday_card"
    const val BUSINESS_CARD = "business_card"
    const val DICE_ROLLER = "dice_roller"
    const val TIP_CALCULATOR = "tip_calculator"
    const val ART_SPACE = "art_space"
    const val AFFIRMATIONS = "affirmations"
    const val TOPICS = "topics"
    const val MATERIAL = "material"
    const val DESSERT = "dessert"
    const val GUESS_WORD = "guess_word"
    const val CUPCAKE = "cupcake"
    const val REPLY = "reply"
    const val RACE_TRACKER = "race_tracker"
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

        // 官方示例
        composable(Routes.BIRTHDAY_CARD) {
            BirthdayCardScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.BUSINESS_CARD) {
            BusinessCardScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.DICE_ROLLER) {
            DiceRollerScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.TIP_CALCULATOR) {
            TipCalculatorScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.ART_SPACE) {
            ArtSpaceScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.AFFIRMATIONS) {
            AffirmationScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.TOPICS) {
            TopicScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.MATERIAL) {
            MaterialScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.DESSERT) {
            DessertScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.GUESS_WORD) {
            GuessWordScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.CUPCAKE) {
            CupcakeScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.REPLY) {
            ReplyScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(Routes.RACE_TRACKER) {
            RaceTrackerScreen ()
        }
    }
}