package com.aarav.aisamadhan.navigation

import ImageViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aarav.aisamadhan.screens.HomeScreen
import com.aarav.aisamadhan.screens.ImageGeneration
import com.aarav.aisamadhan.screens.OnBoardingScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavGraph(navController : NavHostController, viewModel: ImageViewModel) {
    NavHost(navController = navController, startDestination = "onboarding_screen") {
        AddOnBoardScreen(navController, this)
        AddHomeScreen(navController, this, viewModel)
        AddResultScreen(navController, this, viewModel)
    }
}

fun AddOnBoardScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder){

    navGraphBuilder.composable(
        route = "onboarding_screen",
    ) {
        OnBoardingScreen(navigateToHome = {navController.navigate("home")})
    }
}

fun AddHomeScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder, viewModel : ImageViewModel){

    navGraphBuilder.composable(
        route = "home",
    ) {
            HomeScreen(navigateToResult = {navController.navigate("result")}, viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun AddResultScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder, viewModel: ImageViewModel){
    navGraphBuilder.composable(
        route = "result",
    ) {
        ImageGeneration(viewModel, navigateToHome = {navController.navigate("home")})
    }
}

