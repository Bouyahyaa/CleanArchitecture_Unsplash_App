package com.example.cleanarchitectureunsplashapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleanarchitectureunsplashapp.core.Screen
import com.example.cleanarchitectureunsplashapp.presentation.pictures.PictureListScreen
import com.example.cleanarchitectureunsplashapp.presentation.stories.StoriesScreen
import com.example.cleanarchitectureunsplashapp.ui.theme.CleanArchitectureUnsplashAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureUnsplashAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PictureListScreen.route
                    ) {
                        composable(route = Screen.PictureListScreen.route) {
                            PictureListScreen(navController = navController)
                        }

                        composable(
                            route = Screen.StoriesScreen.route + "/{pictureUrl}/{userPictureUrl}/{username}/{pictureId}",
                            arguments = listOf(
                                navArgument("pictureUrl") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                },
                                navArgument("userPictureUrl") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                },
                                navArgument("username") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                },
                                navArgument("pictureId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) { entry ->
                            StoriesScreen(
                                userPictureUrl = entry.arguments?.getString("userPictureUrl")!!,
                                username = entry.arguments?.getString("username")!!,
                                pictureId = entry.arguments?.getString("pictureId")!!,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}