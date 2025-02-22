package com.cjmobileapps.quidditch_players_kmm_2024.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import org.jetbrains.compose.resources.stringResource
import quidditch_players_kmm_2024.shared.generated.resources.Res
import quidditch_players_kmm_2024.shared.generated.resources.app_name
import quidditch_players_kmm_2024.shared.generated.resources.back_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuidditchPlayersTopAppBar(
    navController: NavController,
    topBarTitle: String = "",
) {
    val horizontalArrangement =
        if (navController.previousBackStackEntry != null) Arrangement.Start else Arrangement.Center

    val titleText =
        if (!topBarTitle.isNullOrEmpty()) {
            topBarTitle
        } else {
            stringResource(Res.string.app_name)
        }

    TopAppBar(
        modifier =
        Modifier
            .fillMaxWidth(),
        title = {
            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag("$titleText title"),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = horizontalArrangement,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = titleText,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.back_description),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        },
        colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary,
        ),
    )
}
