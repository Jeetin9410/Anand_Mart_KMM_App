package org.example.project.presentation.screens.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.CashBanknote
import compose.icons.tablericons.History
import compose.icons.tablericons.Language
import compose.icons.tablericons.Lock
import compose.icons.tablericons.Logout

@Composable
fun ProfileScreenUi(
    userName: String,
    email: String,
    profileImage: String,
    onEditClick: () -> Unit,
    onItemClicked: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProfileImageWithEditIcon(profileImage, onClick = { /* open image picker */ })
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(userName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(email, color = Color.Gray)
                ProfileActionButton("Edit Profile", onClick = onEditClick)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        SectionDivider()
        Spacer(modifier = Modifier.height(6.dp))
        ProfileListItem(
            Icons.Default.FavoriteBorder,
            label = "Favourites",
            onClick = { onItemClicked("Favourites") })
        ProfileListItem(TablerIcons.Lock, label = "Downloads") { onItemClicked("Downloads") }
        ProfileListItem(TablerIcons.Language, "Language") { onItemClicked("Language") }
        ProfileListItem(Icons.Default.Place, "Location") { onItemClicked("Location") }
        ProfileListItem(TablerIcons.CashBanknote, "Past orders") { onItemClicked("Subscription") }

        SectionDivider()

        ProfileListItem(Icons.Default.Delete, "Clear cache") { onItemClicked("Clear cache") }
        ProfileListItem(TablerIcons.History, "Clear history") { onItemClicked("Clear history") }

        SectionDivider()

        ProfileListItem(
            TablerIcons.Logout,
            "Log out",
            iconTint = Color.Red
        ) { onItemClicked("Log out") }
    }
}
