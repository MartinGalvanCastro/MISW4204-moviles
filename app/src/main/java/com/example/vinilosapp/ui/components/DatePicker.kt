package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.vinilosapp.utils.convertMillisToDate
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

object TestTags {
    const val DATE_PICKER_TEXT_FIELD = "DatePickerTextField"
    const val DATE_PICKER_DIALOG = "DatePickerDialog"
    const val DATE_PICKER_CONFIRM_BUTTON = "DatePickerConfirmButton"
    const val DATE_PICKER_DISMISS_BUTTON = "DatePickerDismissButton"
    const val DATE_PICKER_TRAILING_ICON = "DatePickerTrailingIcon"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    label: String,
    initialDate: String = "",
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(initialDate) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.takeIf { it.isNotBlank() }?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        },
    )

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.testTag(TestTags.DATE_PICKER_TRAILING_ICON),
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date",
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .testTag(TestTags.DATE_PICKER_TEXT_FIELD),
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val formattedDate = convertMillisToDate(it)
                            selectedDate = formattedDate
                            onDateSelected(formattedDate)
                        }
                        showDatePicker = false
                    },
                    modifier = Modifier.testTag(TestTags.DATE_PICKER_CONFIRM_BUTTON),
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false },
                    modifier = Modifier.testTag(TestTags.DATE_PICKER_DISMISS_BUTTON),
                ) {
                    Text("Cancel")
                }
            },
            modifier = Modifier.testTag(TestTags.DATE_PICKER_DIALOG),
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
