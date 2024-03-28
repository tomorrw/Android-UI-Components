package com.tomorrow.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.tomorrow.android.ui.theme.AndroidTheme
import com.tomorrow.eventlisting.EventsList
import com.tomorrow.eventlisting.presentationModel.EventCardModel
import com.tomorrow.listdisplay.ListDisplay
import com.tomorrow.listdisplay.ListDisplayItem
import com.tomorrow.readviewmodel.DefaultReadView
import com.tomorrow.readviewmodel.ReadViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime
class testing(){
    fun test(): Flow<List<EventCardModel>> =
         flow {
            emit(listOf(
                EventCardModel(
                    id = "1",
                    title = "Event 1",
                    topic = "Topic 1",
                    startDate = LocalDateTime.now(),
                    endDate = LocalDateTime.now(),
                    location = "Location 1",
                    speakers = listOf(),
                    hasAttended = false,
                    color = Color.Red,
                    onClick = {

                    }
                )
            )
            )    }
    fun testt(): Flow<List<EventCardModel>> =
         flow {
            emit(listOf(
                EventCardModel(
                    id = "1",
                    title = "Event 1",
                    topic = "Topic 1",
                    startDate = LocalDateTime.now(),
                    endDate = LocalDateTime.now(),
                    location = "Location 1",
                    speakers = listOf(),
                    hasAttended = false,
                    color = Color.Blue,
                    onClick = {

                    }
                )
            )
            )    }
}

class testViewModel: ReadViewModel<List<EventCardModel>>(
    load = { testing().test() },
    refresh = { testing().testt() }
    )
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val testm = testViewModel()
        setContent {

            val searchValue: MutableState<String> = remember { mutableStateOf("") }

            AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TextField(
                        value = searchValue.value,
                        singleLine = true,
                        onValueChange = {
                            searchValue.value = it;
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        placeholder = { Text("Search") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            disabledContainerColor = MaterialTheme.colorScheme.background,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        )
                    )
                    ListDisplay(
                        items = listOf(
                            ListDisplayItem(
                                id = "honestatis",
                                title = "iaculis",
                                description = null,
                                imageUrlString = null
                            ),
                            ListDisplayItem(
                                id = "his",
                                title = "iaculis",
                                description = null,
                                imageUrlString = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png"
                            ),
                        ),
                        header = {
                            Box(Modifier.fillMaxWidth()) {
                                CompositionLocalProvider(
                                    LocalDensity provides Density(LocalDensity.current.density, 1f)
                                ) {
                                    val size = 25.dp
                                    Icon(
                                        modifier = Modifier
                                            // beware don't change values those affect what header is displayed
                                            .size(size)
                                            .align(Alignment.BottomEnd),
                                        imageVector = Icons.Outlined.ArrowBack,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                }
                            }
                        },
                        searchValue = searchValue,
                        )

                    DefaultReadView(viewModel = testm) {


                        EventsList(
                            events = it,
                            days = listOf(LocalDate.now()),
                            selectedDay = null,
                            onDaySelected = {
                                testm.on(ReadViewModel.Event.OnRefresh)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTheme {
        Greeting("Android")
    }
}