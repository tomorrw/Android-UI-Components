package com.tomorrow.eventlisting


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomorrow.components.others.GeneralError
import com.tomorrow.components.others.PullToRefreshLayout
import com.tomorrow.eventlisting.presentationModel.EventCardModel
import com.tomorrow.eventlisting.presentationModel.isNow
import java.time.LocalDate
import java.time.format.DateTimeFormatter


private val getMonthFormatter = DateTimeFormatter.ofPattern("MMM")

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalFoundationApi::class
)
@Composable
fun EventsList(
    events: List<EventCardModel>,
    style: HeaderStyle = HeaderStyle.defaultHeaderStyle(),
    state: PullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = { }),
    isRefreshing: Boolean = false,
    lazyListState: LazyListState = rememberLazyListState(),
    days: List<LocalDate>,
    selectedDay: LocalDate?,
    emptyView: @Composable () -> Unit = {
        GeneralError(message = "Nothing to see here yet.", description = "")
    },
    eventCard: @Composable (EventCardModel) -> Unit = { EventCard(event = it) },
    onDaySelected: (LocalDate) -> Unit,
) {
    //  ========= DAYS =========
    val eventsByLocation: Map<String, List<EventCardModel>> by remember(events) {
        derivedStateOf {
            mapOf("All Locations" to events).plus(
                events.groupBy { it.location }.toSortedMap(Comparator.reverseOrder())
            )
        }
    }

    var selectedLocation by remember {
        mutableStateOf(eventsByLocation.keys.firstOrNull() ?: "")
    }

    LaunchedEffect(key1 = eventsByLocation) {
        if (eventsByLocation.keys.contains(selectedLocation).not()) {
            selectedLocation = eventsByLocation.keys.firstOrNull() ?: ""
        }
    }

    val filteredEvents by remember(eventsByLocation) {
        derivedStateOf { eventsByLocation.getOrDefault(selectedLocation, events) }
    }

    PullToRefreshLayout(state = state, isRefreshing = isRefreshing) {
        LaunchedEffect(key1 = selectedDay) {
            getCurrentEventIndex(events).let {
                if (it != -1) lazyListState.animateScrollToItem(it)
            }
        }

        LazyColumn(
            Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp))
                .animateContentSize(),
            state = lazyListState
        ) {
            if (events.isEmpty()) item {
                Column(modifier = Modifier.fillParentMaxHeight()) {
                    Header(
                        lazyListState,
                        style,
                        eventsByLocation,
                        days,
                        selectedDay,
                        onDaySelected,
                        selectedLocation
                    ) { selectedLocation = it }

                    emptyView()
                }
            } else {
                stickyHeader {
                    Header(
                        lazyListState,
                        style,
                        eventsByLocation,
                        days,
                        selectedDay,
                        onDaySelected,
                        selectedLocation
                    ) { selectedLocation = it }
                }

                item { Spacer(Modifier.height(16.dp)) }

                items(filteredEvents, key = { it.hashCode() }) { e ->
                    Column(Modifier.animateItemPlacement()) {

                        eventCard(e)

                        Spacer(Modifier.size(16.dp))
                    }

                }

                item { Spacer(modifier = Modifier.size(32.dp)) }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header(
    lazyListState: LazyListState,
    style: HeaderStyle = HeaderStyle.defaultHeaderStyle(),
    eventsByLocation: Map<String, List<EventCardModel>>,
    days: List<LocalDate>,
    selectedDay: LocalDate?,
    onDaySelected: (LocalDate) -> Unit,
    selectedLocation: String,
    onSelectedLocation: (String) -> Unit,
) {
    val daysScrollState: LazyListState = rememberLazyListState()
    LaunchedEffect(key1 = "") {
        if(selectedDay == null) return@LaunchedEffect
        daysScrollState.scrollToItem(days.indexOf(selectedDay))
    }

    CompositionLocalProvider(
        LocalDensity provides Density(LocalDensity.current.density, 1f)
    ) {
        val headerHeight = 130.dp
        val density = LocalDensity.current

        var oldFirstVisibleItemOffset by remember { mutableIntStateOf(lazyListState.firstVisibleItemScrollOffset) }
        var oldFirstVisibleItemIndex by remember { mutableIntStateOf(lazyListState.firstVisibleItemIndex) }
        val newFirstVisibleItemScrollOffset by remember(lazyListState) { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }
        val newFirstVisibleItemScrollIndex by remember(lazyListState) { derivedStateOf { lazyListState.firstVisibleItemIndex } }

        var headerOffset by remember { mutableStateOf(0.dp) }

        LaunchedEffect(newFirstVisibleItemScrollOffset) {
            if (oldFirstVisibleItemIndex == newFirstVisibleItemScrollIndex) {
                val changeOfOffset =
                    newFirstVisibleItemScrollOffset - oldFirstVisibleItemOffset

                with(density) {
                    headerOffset =
                        (headerOffset + changeOfOffset.toDp()).coerceIn(
                            0.dp,
                            headerHeight
                        )
                }
            }

            oldFirstVisibleItemOffset = newFirstVisibleItemScrollOffset
            oldFirstVisibleItemIndex = newFirstVisibleItemScrollIndex
        }

        Column(
            Modifier
                .height(headerHeight)
                .offset(y = -headerOffset)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                state = daysScrollState
            ) {
                items(days) {
                    Column(
                        Modifier
                            .padding(end = if (days.count() > 5) 16.dp else 0.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (it == selectedDay) style.dayListStyle.selectedDayContainerColor else style.dayListStyle.dayContainerColor
                            )
                            .clickable { onDaySelected(it) }
                            .padding(8.dp)
                            .width(40.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val color =
                            if (selectedDay == it) style.dayListStyle.selectedDayColor else style.dayListStyle.dayColor

                        Text(
                            it.dayOfMonth.toString(),
                            style = MaterialTheme.typography.titleLarge.copy(color, lineHeight = 14.sp)
                        )
                        Text(
                            getMonthFormatter.format(it),
                            style = LocalTextStyle.current.copy(color)
                        )
                    }
                }
            }

            var isLocationFilterExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                expanded = isLocationFilterExpanded,
                onExpandedChange = {
                    isLocationFilterExpanded = !isLocationFilterExpanded
                }
            ) {
                OutlinedTextField(
                    value = selectedLocation,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLocationFilterExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(50.dp),
                    textStyle = style.dropDownStyle.textStyle,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = style.dropDownStyle.backgroundColor,
                        unfocusedContainerColor = style.dropDownStyle.backgroundColor,
                        disabledContainerColor = style.dropDownStyle.backgroundColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    )
                )

                DropdownMenu(
                    modifier = Modifier
                        .exposedDropdownSize()
                        .background(style.dropDownStyle.menuBackgroundColor),
                    expanded = isLocationFilterExpanded,
                    onDismissRequest = { isLocationFilterExpanded = false }
                ) {
                    eventsByLocation.forEach { item ->
                        Text(
                            text = item.key,
                            style = style.dropDownStyle.menuTextStyle,
                            modifier = Modifier
                                .clickable {
                                    onSelectedLocation(item.key)
                                    isLocationFilterExpanded = false
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))
        }
    }
}

private fun getCurrentEventIndex(list: List<EventCardModel>): Int = list.indexOfFirst { it.isNow() }

data class DropDownStyle(
    val backgroundColor: Color,
    val textStyle: TextStyle,
    val menuBackgroundColor: Color,
    val menuTextStyle: TextStyle,
)

data class HeaderStyle(
    val dropDownStyle: DropDownStyle,
    val dayListStyle: DayListStyle,
) {
    companion object {
        @Composable
        fun defaultHeaderStyle(
            dropDownStyle: DropDownStyle = defaultDropDownStyle(),
            dayListStyle: DayListStyle = defaultDayListStyle()
        ) = HeaderStyle(
            dropDownStyle = dropDownStyle,
            dayListStyle = dayListStyle
        )

        @Composable
        fun defaultDayListStyle(
            dayColor: Color = androidx.compose.material.MaterialTheme.colors.primaryVariant,
            selectedDayColor: Color = MaterialTheme.colorScheme.primary,
            dayContainerColor: Color = MaterialTheme.colorScheme.surface,
            selectedDayContainerColor: Color = MaterialTheme.colorScheme.background
        ) = DayListStyle(
            dayColor = dayColor,
            selectedDayColor = selectedDayColor,
            dayContainerColor = dayContainerColor,
            selectedDayContainerColor = selectedDayContainerColor
        )

        @Composable
        fun defaultDropDownStyle(
            backgroundColor: Color = MaterialTheme.colorScheme.background,
            textStyle: TextStyle = LocalTextStyle.current,
            menuBackgroundColor: Color = MaterialTheme.colorScheme.background,
            menuTextStyle: TextStyle = LocalTextStyle.current
        ) = DropDownStyle(
            backgroundColor = backgroundColor,
            textStyle = textStyle,
            menuBackgroundColor = menuBackgroundColor,
            menuTextStyle = menuTextStyle
        )
    }
}

data class DayListStyle(
    val dayColor: Color,
    val selectedDayColor: Color,
    val dayContainerColor: Color,
    val selectedDayContainerColor: Color,
)
