package org.example.project.presentation.screens.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Camera
import compose.icons.fontawesomeicons.solid.Edit
import compose.icons.fontawesomeicons.solid.UserEdit
import compose.icons.tablericons.Eye
import compose.icons.tablericons.EyeOff
import org.example.project.presentation.screens.main_screen.tabs.NetworkImage
import org.example.project.presentation.screens.main_screen.tabs.ProfileScreen
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ProfileImageWithEditIcon(imageUrl: String, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.BottomEnd) {
        NetworkImage(
            url = imageUrl,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.LightGray, CircleShape)
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(20.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                FontAwesomeIcons.Solid.UserEdit,
                contentDescription = "Edit Image",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Composable
fun ProfileActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.primary),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(text = text, color = Color.White, style = appTypography().button)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileListItem(
    icon: ImageVector,
    label: String,
    iconTint: Color = Color.Black,
    trailingIcon: ImageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
    onClick: () -> Unit,
) {
    Card(
        onClick ={
            onClick.invoke()
        },
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = iconTint)
            Spacer(modifier = Modifier.width(16.dp))
            Text(label, modifier = Modifier.weight(1f),style = appTypography().subtitle2.copy(fontWeight = FontWeight.Normal))
            Icon(trailingIcon, contentDescription = null, tint = Color.Gray)
        }
    }

}

@Composable
fun SectionDivider() {
    Divider(
        color = Color(0xFFE0E0E0),
        thickness = 2.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = appTypography().subtitle2) },
        enabled = enabled,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                val icon = if (passwordVisible) TablerIcons.Eye else TablerIcons.EyeOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(icon, contentDescription = null)
                }
            }
        },
        textStyle = appTypography().subtitle2,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AppColors.primary,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color(0xFF4A5A48)
        )
    )
}