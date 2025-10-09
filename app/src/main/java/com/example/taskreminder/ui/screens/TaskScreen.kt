package com.example.taskreminder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.taskreminder.R
import com.example.taskreminder.data.Task
import com.example.taskreminder.data.TasksDatabase
import com.example.taskreminder.ui.theme.DarkGray
import com.example.taskreminder.ui.theme.Gray
import com.example.taskreminder.ui.theme.LightGray
import com.example.taskreminder.ui.viewmodels.TasksViewModel

@Composable
fun TaskScreen(
    viewModel: TasksViewModel = TasksViewModel(TasksDatabase.getInstance(LocalContext.current).tasksDao())
) {
    val tasks by viewModel.tasks.collectAsState()
    var showAddTaskDialog by remember { mutableStateOf(false) }

    Scaffold (
        topBar = { TopAppBar() },
        bottomBar = { BottomAppBar(
            onAddTaskButtonClick = {showAddTaskDialog = true}
        ) },
        containerColor = DarkGray
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = LightGray
                        ),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(R.string.todo_button)
                        )
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = LightGray
                        ),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(R.string.done_button)
                        )
                    }
                }

                TaskList(tasks)
                if (showAddTaskDialog) {
                    AddTaskDialog(
                        viewModel = viewModel,
                        onDismiss = { showAddTaskDialog = false },
                        onConfirm = {
                            viewModel.addTask()
                            showAddTaskDialog = false
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun TopAppBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp)
    ) {
        Text(
            text = stringResource(id = R.string.top_bar),
            color = LightGray,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TaskItem(
    task: Task,
    onDoneClick: () -> Unit,
    onStarClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            )
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            )
            .height(80.dp)
    ) {
        IconButton(
            onClick = onDoneClick
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.done_task),
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = task.acronym,
                color = Color.White
            )
            Text(
                text = task.description,
                color = Color.White
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = onStarClick
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.star_task),
                tint = Color.White
            )
        }
    }
}

@Composable
fun TaskList(
    tasks: List<Task>
) {
   LazyColumn(
       modifier = Modifier
           .fillMaxSize(),
       contentPadding = PaddingValues(vertical = 8.dp)
   ) {
       items(tasks) { task ->
           TaskItem(
               task = task,
               onDoneClick = {},
               onStarClick = {}
           )
           Spacer(modifier = Modifier.height(4.dp))
       }
   }
}

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    viewModel: TasksViewModel
) {
    val acronym by viewModel.acronym.collectAsState()
    val description by viewModel.description.collectAsState()

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .background(color = Gray)
            ) {
                Text(
                    text = stringResource(R.string.dialog_add_task),
                    fontSize = 20.sp,
                    color = DarkGray
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = acronym,
                    onValueChange = { viewModel.onAcronymChange(it) },
                    label = { Text(text = stringResource(R.string.dialog_acronym)) },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = LightGray,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = LightGray,
                        cursorColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { viewModel.onDescriptionChange(it) },
                    label = {
                        Text(
                            text = stringResource(R.string.dialog_description)
                        )
                    },
                    singleLine = false,
                    textStyle = TextStyle(fontSize = 20.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = LightGray,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = LightGray,
                        cursorColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_cancel),
                            color = DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = LightGray,
                            containerColor = Color.Black
                        )
                    ) {
                        Text(stringResource(R.string.dialog_save))
                    }
                }
            }
        }
    }
}

@Composable
fun BottomAppBar(
    onAddTaskButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(end = 16.dp, bottom = 60.dp)
    ) {
        Spacer(Modifier.weight(1f))
        Button(
            onClick = onAddTaskButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = LightGray
            ),
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_task)
            )
        }
    }
}