package com.example.mealmates.ui.views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealmates.constants.GlobalObjects
import com.example.mealmates.constants.NavArguments
import com.example.mealmates.constants.Routes
import com.example.mealmates.models.Group
import com.example.mealmates.ui.theme.MealMatesTheme
import com.example.mealmates.ui.theme.button_colour
import com.example.mealmates.ui.viewModels.LoginViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.net.PlacesClient

data class NavigationItem(val icon: ImageVector, val label: String, val route: String)

fun convertToByteArray(image: String): ByteArray {
    return image.toByteArray()
}

fun convertToInt(string: String): Int {
    return string.trim().toInt()
}

fun convertToStringList(string: String): List<String> {
    val stringNoSpaces = string.trim()
    val cleanedString =
        if (stringNoSpaces.startsWith("[") && stringNoSpaces.endsWith("]")) {
            stringNoSpaces.substring(1, stringNoSpaces.length - 1)
        } else {
            stringNoSpaces
        }

    return cleanedString.split(",").map { it.trim() }.filter { it.isNotEmpty() }
}

fun convertToLatLng(string: String): LatLng {
    // Use a regular expression to extract the latitude and longitude values
    val regex = Regex("""lat/lng: \(([^,]+),([^)]+)\)""")
    val matchResult = regex.find(string.trim())

    return if (matchResult != null) {
        val (lat, lng) = matchResult.destructured
        LatLng(lat.toDouble(), lng.toDouble())
    } else {
        throw IllegalArgumentException("Invalid LatLng format")
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint(
    "RememberReturnType", "UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MealMatesApp(loginModel: LoginViewModel, placesClient: PlacesClient) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }

    val navBarItems =
        arrayOf(
            NavigationItem(icon = Icons.Rounded.Home, label = "Home", route = Routes.HOME),
            NavigationItem(
                icon = Icons.Rounded.AccountBox, label = "Profile", route = Routes.PROFILE))

    fun onNavigateToHome() {
        navController.navigate(Routes.HOME)
    }

    fun onNavigateToSurvey() {
        navController.navigate(Routes.SURVEY)
    }

    fun onNavigateToMainPage() {
        navController.navigate(Routes.HOME)
    }

    fun onNavigateToLocationFromSignupPage() {
        navController.navigate(Routes.LOCATION_FROM_SIGNUP)
    }

    fun onNavigateToLocationFromUserProfilePage() {
        navController.navigate(Routes.LOCATION_FROM_USER_PROFILE)
    }

    fun onNavigateToLocationFromGroupSettingsPage(groupId: Int) {
        navController.navigate(Routes.LOCATION_FROM_GROUP_SETTINGS + "?" + "${NavArguments.GROUP_INFO.GROUP_ID}=$groupId")
    }

    fun onNavigateToLocationFromCreateNewGroupPage(group: Group) {
        println("This is the group $group")
        navController.navigate(
            Routes.LOCATION_FROM_CREATE_NEW_GROUP +
                    "?" +
                    "${NavArguments.GROUP_INFO.GROUP_ID}= ${group.gid}&" +
                    "${NavArguments.GROUP_INFO.GROUP_NAME}= ${group.name}&" +
                    "${NavArguments.GROUP_INFO.RESTRICTIONS}= ${group.restrictions}&" +
                    "${NavArguments.GROUP_INFO.PREFERENCES}= ${group.preferences}&" +
                    "${NavArguments.GROUP_INFO.USERS}= ${group.uids}&" +
                    "${NavArguments.GROUP_INFO.IMAGE}= ${group.image}&" +
                    "${NavArguments.GROUP_INFO.LOCATION}= ${group.location}")
    }

    fun onNavigateToRestaurantPrompts(groupId: Int) {
        navController.navigate(
            Routes.RESTAURANT_PROMPTS + "?" + "${NavArguments.GROUP_INFO.GROUP_ID}=$groupId")
    }

    fun onNavigateToMatchedRestaurants(rid: Int) {
        navController.navigate(
            Routes.MATCHED_RESTAURANTS + "?" +
            "${NavArguments.RESTAURANT_INFO.RESTAURANT_ID}= $rid"
        )
    }

    fun onNavigateToGroup() {
        navController.navigate(Routes.GROUP)
    }

    fun onNavigateToCreateNewGroup() {
        navController.navigate(Routes.CREATE_NEW_GROUP)
    }

    fun onNavigateToGroupMembers() {
        navController.navigate(Routes.GROUP_MEMBERS)
    }

    fun onNavigateToProfile() {
        navController.navigate(Routes.PROFILE)
    }

    // test places api
    fun searchNearbyPlaces() {
        navController.navigate(Routes.PLACES_API_TEST)
    }

    fun onNavigateToGroupInfo(group: Group) {
        println("This is the group $group")
        navController.navigate(
            Routes.GROUP_INFO +
                "?" +
                "${NavArguments.GROUP_INFO.GROUP_ID}= ${group.gid}&" +
                "${NavArguments.GROUP_INFO.GROUP_NAME}= ${group.name}&" +
                "${NavArguments.GROUP_INFO.RESTRICTIONS}= ${group.restrictions}&" +
                "${NavArguments.GROUP_INFO.PREFERENCES}= ${group.preferences}&" +
                "${NavArguments.GROUP_INFO.USERS}= ${group.uids}&" +
                "${NavArguments.GROUP_INFO.IMAGE}= ${group.image}&" +
                "${NavArguments.GROUP_INFO.LOCATION}= ${group.location}")
    }

    fun onNavigateToGroupSettings(group: Group) {
        //        navController.navigate(Routes.GROUP_SETTINGS)
        println("This is the group $group")
        navController.navigate(
            Routes.GROUP_SETTINGS +
                "?" +
                "${NavArguments.GROUP_INFO.GROUP_ID}= ${group.gid}&" +
                "${NavArguments.GROUP_INFO.GROUP_NAME}= ${group.name}&" +
                "${NavArguments.GROUP_INFO.RESTRICTIONS}= ${group.restrictions}&" +
                "${NavArguments.GROUP_INFO.PREFERENCES}= ${group.preferences}&" +
                "${NavArguments.GROUP_INFO.USERS}= ${group.uids}&" +
                "${NavArguments.GROUP_INFO.IMAGE}= ${group.image}&" +
                "${NavArguments.GROUP_INFO.LOCATION}= ${group.location}")
    }

    fun onNavigateToCreateNewGroup(group: Group) {
        println("This is the group $group")
        navController.navigate(
            Routes.CREATE_NEW_GROUP +
                    "?" +
                    "${NavArguments.GROUP_INFO.GROUP_ID}= ${group.gid}&" +
                    "${NavArguments.GROUP_INFO.GROUP_NAME}= ${group.name}&" +
                    "${NavArguments.GROUP_INFO.RESTRICTIONS}= ${group.restrictions}&" +
                    "${NavArguments.GROUP_INFO.PREFERENCES}= ${group.preferences}&" +
                    "${NavArguments.GROUP_INFO.USERS}= ${group.uids}&" +
                    "${NavArguments.GROUP_INFO.IMAGE}= ${group.image}&" +
                    "${NavArguments.GROUP_INFO.LOCATION}= ${group.location}")
    }

    fun onNavigateToMatchList(groupId: Int) {
        navController.navigate(
            Routes.MATCH_LIST + "?" +
            "${NavArguments.GROUP_INFO.GROUP_ID}= $groupId"
        )
    }


    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "survey" -> false
        "location" -> false
        else -> true
    }


    MealMatesTheme {
        Scaffold(
            bottomBar = {
                if (showBottomBar)
                    BottomNavigation(
                        backgroundColor = button_colour,
                        modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp)) {
                            val currentDestination = navBackStackEntry?.destination

                            navBarItems.forEach { navItem ->
                                BottomNavigationItem(
                                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),
                                    icon = {
                                        Icon(
                                            navItem.icon,
                                            contentDescription = null,
                                            tint = Color.White,
                                        )
                                    },
                                    label = {
                                        Text(navItem.label, maxLines = 1, color = Color.White)
                                    },
                                    selected =
                                        currentDestination?.hierarchy?.any {
                                            it.route == navItem.route
                                        } == true,
                                    onClick = { navController.navigate(navItem.route) })
                            }
                    }
                }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(0.dp)
            ) {

                NavHost(
                    navController,
                    startDestination = if (GlobalObjects.user.preferences.isEmpty()) {
                        Routes.SURVEY
                    } else if (GlobalObjects.user.preferences.isNotEmpty() && GlobalObjects.user.location.latitude.equals(0.0) && GlobalObjects.user.location.longitude.equals(0.0)) {
                        Routes.LOCATION_FROM_SIGNUP
                    } else {
                        Routes.HOME
                    }
                ) {
                    composable(Routes.HOME) {
                        MainPage(
                            loginModel,
                            onNavigateToGroup = { group -> onNavigateToGroupInfo(group) },
                            onNavigateToMatches = { gid -> onNavigateToMatchList(gid) },
                            onNavigateToCreateNewGroup = { onNavigateToCreateNewGroup() }
                        )
                    }

                    composable(Routes.SURVEY) {
                        PreferenceAndRestrictions(loginModel, { onNavigateToMainPage() }, { onNavigateToLocationFromSignupPage() }, { onNavigateToProfile() })
                    }

                    composable(Routes.LOCATION_FROM_SIGNUP) {
                        val locationSettingPage = LocationSettingPage()
                        locationSettingPage.LocationSettings(loginModel, placesClient, false, { onNavigateToMainPage() }, { group: Group -> onNavigateToGroupSettings(group) }, null, null)
                    }

                    composable(Routes.LOCATION_FROM_USER_PROFILE) {
                        val locationSettingPage = LocationSettingPage()
                        locationSettingPage.LocationSettings(loginModel, placesClient, false, { onNavigateToProfile() }, { group: Group -> onNavigateToGroupSettings(group) }, null, null)
                    }

                    composable(
                        Routes.LOCATION_FROM_GROUP_SETTINGS_WITH_ARGS,
                        arguments =
                        listOf(
                            navArgument(NavArguments.GROUP_INFO.GROUP_ID) {
                                defaultValue = ""
                            })
                    ) {backStackEntry ->
                        val groupId =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.GROUP_ID
                            ) ?: ""
                        val locationSettingPage = LocationSettingPage()
                        locationSettingPage.LocationSettings(loginModel, placesClient, true, { onNavigateToMainPage() }, { group: Group -> onNavigateToGroupSettings(group) }, groupId, null)
                    }

                    composable(
                        Routes.RESTAURANT_PROMPTS_WITH_ARGS,
                        arguments =
                        listOf(
                            navArgument(NavArguments.GROUP_INFO.GROUP_ID) {
                                defaultValue = ""
                            })
                    ) { backStackEntry ->
                        val groupId =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.GROUP_ID
                            ) ?: ""
                        RestaurantPrompt(loginModel, groupId) { onNavigateToHome() }
                    }

                    composable(
                        Routes.MATCHED_RESTAURANTS_WITH_ARGS,
                        arguments = listOf(
                            navArgument(NavArguments.RESTAURANT_INFO.RESTAURANT_ID) {
                                defaultValue = ""
                            }
                        )
                    ) {
                        val restaurantId =
                            convertToInt(
                                it.arguments?.getString(
                                    NavArguments.RESTAURANT_INFO.RESTAURANT_ID
                                ) ?: ""
                            )
                        println("This is the arguments: $restaurantId")
                        ListOfMatchedRestaurantsPage(
                            loginModel,
                            restaurantId
                        ) { onNavigateToHome() }
                    }

                    composable(Routes.GROUP) {
                        GroupPage(
                            loginModel,
                            { onNavigateToGroupMembers() },
                            { onNavigateToMatchList(-1) })
                    }

                    composable(Routes.CREATE_NEW_GROUP) {
                        CreateNewGroupPage(loginModel, -1, "", listOf(), listOf(), listOf(), byteArrayOf(0), LatLng(0.0, 0.0), { onNavigateToMainPage() }, { group: Group -> onNavigateToLocationFromCreateNewGroupPage(group) } )
                    }

                    composable(Routes.GROUP_MEMBERS) {
                        GroupMembersPage(loginModel)

                    }

                    composable(Routes.PROFILE) {
                        UserProfileManagementPage(loginModel, { onNavigateToSurvey() }, { onNavigateToLocationFromUserProfilePage() })
                    }

                    composable(
                        Routes.GROUP_SETTINGS_WITH_ARGS,
                        arguments =
                        listOf(
                            navArgument(NavArguments.GROUP_INFO.GROUP_ID) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.GROUP_NAME) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.USERS) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.PREFERENCES) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.IMAGE) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.LOCATION) {
                                defaultValue = ""
                            })
                    ) { backStackEntry ->
                        val groupId =
                            convertToInt(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.GROUP_ID
                                ) ?: ""
                            )
                        val groupName =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.GROUP_NAME
                            ) ?: ""
                        val uids =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.USERS
                                ) ?: ""
                            )
                        val preferences =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.PREFERENCES
                                ) ?: ""
                            )
                        val restrictions =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.RESTRICTIONS
                                ) ?: ""
                            )
                        val image =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.IMAGE
                            ) ?: ""
                        val location =
                            convertToLatLng(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.LOCATION
                                ) ?: "0.0,0.0"
                            )

                        GroupSettings(
                            loginModel,
                            groupId,
                            groupName,
                            preferences, // Simplified for debugging
                            restrictions, // Simplified for debugging
                            uids, // Simplified for debugging
                            ByteArray(0), // Simplified for debugging
                            location, // Simplified for debugging
                            { onNavigateToMainPage() },
                            { group: Group -> onNavigateToGroupInfo(group) },
                            { onNavigateToLocationFromGroupSettingsPage(groupId) }
                        )
                    }

                    // test
                    composable(Routes.PLACES_API_TEST) { PlacesTest(loginModel) }

                    composable(
                        Routes.GROUP_INFO_WITH_ARGS,
                        arguments =
                        listOf(
                            navArgument(NavArguments.GROUP_INFO.GROUP_ID) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.GROUP_NAME) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.USERS) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.PREFERENCES) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.RESTRICTIONS) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.IMAGE) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.LOCATION) {
                                defaultValue = ""
                            })
                    ) { backStackEntry ->
                        val groupId =
                            convertToInt(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.GROUP_ID
                                ) ?: ""
                            )
                        val groupName =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.GROUP_NAME
                            ) ?: ""
                        val uids =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.USERS
                                ) ?: ""
                            )
                        val preferences =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.PREFERENCES
                                ) ?: ""
                            )
                        val restrictions =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.RESTRICTIONS
                                ) ?: ""
                            )
                        val image =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.IMAGE
                            ) ?: ""
                        val location =
                            convertToLatLng(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.LOCATION
                                ) ?: "0.0,0.0"
                            )

                        GroupInfoPage(
                            loginModel,
                            groupId,
                            groupName,
                            preferences, // Simplified for debugging
                            restrictions, // Simplified for debugging
                            uids, // Simplified for debugging
                            ByteArray(0), // Simplified for debugging
                            location, // Simplified for debugging
                            { group: Group -> onNavigateToGroupSettings(group) },
                            { onNavigateToRestaurantPrompts(groupId) })
                    }

                    composable(
                        Routes.CREATE_NEW_GROUP_WITH_ARGS,
                        arguments =
                        listOf(
                            navArgument(NavArguments.GROUP_INFO.GROUP_ID) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.GROUP_NAME) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.USERS) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.PREFERENCES) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.IMAGE) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.LOCATION) {
                                defaultValue = ""
                            })
                    ) { backStackEntry ->
                        val groupId =
                            convertToInt(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.GROUP_ID
                                ) ?: ""
                            )
                        val groupName =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.GROUP_NAME
                            ) ?: ""
                        val uids =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.USERS
                                ) ?: ""
                            )
                        val preferences =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.PREFERENCES
                                ) ?: ""
                            )
                        val restrictions =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.RESTRICTIONS
                                ) ?: ""
                            )
                        val image =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.IMAGE
                            ) ?: ""
                        val location =
                            convertToLatLng(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.LOCATION
                                ) ?: "0.0,0.0"
                            )

                        val curGroup = Group(groupId, groupName, uids, preferences, restrictions, ByteArray(0), location)

                        CreateNewGroupPage(
                            loginModel,
                            groupId,
                            groupName,
                            preferences, // Simplified for debugging
                            restrictions, // Simplified for debugging
                            uids, // Simplified for debugging
                            ByteArray(0), // Simplified for debugging
                            location, // Simplified for debugging
                            { onNavigateToMainPage() },
                            { onNavigateToLocationFromCreateNewGroupPage(curGroup) }
                        )
                    }

                    composable(
                        Routes.LOCATION_FROM_CREATE_NEW_GROUP_WITH_ARGS,
                        arguments =
                        listOf(
                            navArgument(NavArguments.GROUP_INFO.GROUP_ID) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.GROUP_NAME) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.USERS) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.PREFERENCES) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.IMAGE) {
                                defaultValue = ""
                            },
                            navArgument(NavArguments.GROUP_INFO.LOCATION) {
                                defaultValue = ""
                            })
                    ) { backStackEntry ->
                        val groupId =
                            convertToInt(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.GROUP_ID
                                ) ?: ""
                            )
                        val groupName =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.GROUP_NAME
                            ) ?: ""
                        val uids =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.USERS
                                ) ?: ""
                            )
                        val preferences =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.PREFERENCES
                                ) ?: ""
                            )
                        val restrictions =
                            convertToStringList(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.RESTRICTIONS
                                ) ?: ""
                            )
                        val image =
                            backStackEntry.arguments?.getString(
                                NavArguments.GROUP_INFO.IMAGE
                            ) ?: ""
                        val location =
                            convertToLatLng(
                                backStackEntry.arguments?.getString(
                                    NavArguments.GROUP_INFO.LOCATION
                                ) ?: "0.0,0.0"
                            )

                        val curGroup = Group(groupId, groupName, uids, preferences, restrictions, ByteArray(0), location)

                        val locationSettingPage = LocationSettingPage()
                        locationSettingPage.LocationSettings(loginModel, placesClient, true, { onNavigateToProfile() }, { group: Group -> onNavigateToCreateNewGroup(group) }, null, curGroup)
                    }

                    composable(
                        Routes.MATCH_LIST_WITH_ARGS,
                        arguments = listOf(
                            navArgument(NavArguments.GROUP_INFO.GROUP_ID) { defaultValue = "" }
                        )
                    ) { backStackEntry ->
                        val groupId = convertToInt(
                            backStackEntry.arguments?.getString(NavArguments.GROUP_INFO.GROUP_ID)
                                ?: ""
                        )

                        MatchListPage(loginModel, groupId) { rid ->
                            onNavigateToMatchedRestaurants(rid)
                        }
                    }
                }
            }
        }
    }
}
