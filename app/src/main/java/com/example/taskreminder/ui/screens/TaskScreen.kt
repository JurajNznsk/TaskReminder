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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskreminder.R
import com.example.taskreminder.data.Task
import com.example.taskreminder.ui.theme.DarkGray
import com.example.taskreminder.ui.theme.LightGray
import com.example.taskreminder.ui.viewmodels.TasksViewmodel

@Composable
fun TaskScreen(
) {
    // TODO: Make task list load from local database
//    val tasks = listOf(
////        Task("MAS", "Predavacky"),
////        Task("SI", "Nic"),
////        Task("ZTS", "DU")
//    )


    Scaffold (
        topBar = { TopAppBar() },
        bottomBar = { BottomAppBar() },
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

                //TaskList(tasks)
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
                text = task.title,
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
fun BottomAppBar() {
    Box(
        modifier = Modifier
            .padding(start = 400.dp, bottom = 60.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = LightGray
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_task)
            )
        }
    }
}