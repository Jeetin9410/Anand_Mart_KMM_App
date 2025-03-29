package org.example.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun LoadNetworkImage(url: String, modifier: Modifier)