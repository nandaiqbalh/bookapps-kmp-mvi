package com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import bookapps_kmp.composeapp.generated.resources.Res
import bookapps_kmp.composeapp.generated.resources.close_hint
import bookapps_kmp.composeapp.generated.resources.search_hint
import com.nandaiqbalh.kmp.bookapp.core.presentation.DarkBlue
import com.nandaiqbalh.kmp.bookapp.core.presentation.DesertWhite
import com.nandaiqbalh.kmp.bookapp.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookSearchBar(
	searchQuery: String,
	onSearchQueryChange: (String) -> Unit,
	onImeSearch: () -> Unit, // when user click search on user's keyboard
	modifier: Modifier = Modifier,
) {

	// intercept / provides Text Selection Color (example case: user select all the text or block the text)
	CompositionLocalProvider(
		LocalTextSelectionColors provides TextSelectionColors(
			handleColor = SandYellow,
			backgroundColor = SandYellow
		)
	){

		// text field
		OutlinedTextField(
			value = searchQuery,
			onValueChange = onSearchQueryChange,
			shape = RoundedCornerShape(100),
			colors = OutlinedTextFieldDefaults.colors(
				cursorColor = DarkBlue,
				focusedLabelColor = SandYellow
			),
			placeholder = {
				Text(
					stringResource(Res.string.search_hint)
				)
			},
			leadingIcon = {
				Icon(
					imageVector = Icons.Default.Search,
					contentDescription = null,
					tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
				)
			},
			singleLine = true,
			keyboardActions = KeyboardActions(
				onSearch = {
					onImeSearch()
				}
			),
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Text,
				imeAction = ImeAction.Search
			),
			trailingIcon = {
				AnimatedVisibility(
					visible = searchQuery.isNotBlank(),
					enter = fadeIn(),
					exit = fadeOut()
				) {
					IconButton(
						onClick = {
							onSearchQueryChange("")
						}
					) {
						Icon(
							imageVector = Icons.Default.Close,
							contentDescription = stringResource(Res.string.close_hint),
							tint = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			},
			modifier = modifier
				.background(
					shape = RoundedCornerShape(100),
					color = DesertWhite
				)
				.minimumInteractiveComponentSize()
				.fillMaxWidth()
		)

	}

}