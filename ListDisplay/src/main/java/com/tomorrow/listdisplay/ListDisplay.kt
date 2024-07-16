package com.tomorrow.listdisplay

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.tomorrow.components.others.GeneralError
import com.tomorrow.components.others.PullToRefreshLayout
import com.tomorrow.components.cards.InlineCardDisplay
import com.tomorrow.components.headers.PageHeaderLayout
import com.tomorrow.readviewmodel.DefaultReadView
import com.tomorrow.readviewmodel.ReadViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

//View Models
open class ListDisplayReadViewModel<Item : ListDisplayItemInterface>(
    load: () -> Flow<List<Item>>,
    refresh: () -> Flow<List<Item>> = load,
    emptyCheck: (List<Item>) -> Boolean = { it.isEmpty() },
    searchAlgorithm: (String, String) -> Double
) : ReadViewModel<List<Item>>(
    load = load,
    refresh = refresh,
    emptyCheck = emptyCheck
){
    val searchAlgorithm = searchAlgorithm
}

//Models
interface ListDisplayItemInterface {
    val id: String
    val title: String
    val description: String?
    val imageUrlString: String?
}

//View
data class ListDisplayItem(
    override val id: String = UUID.randomUUID().toString(),
    override val title: String,
    override val description: String? = null,
    override val imageUrlString: String? = null,
) : ListDisplayItemInterface


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun <Item : ListDisplayItemInterface> ListDisplayPage(
    title: String?,
    description: String? = null,
    onBackPress: () -> Unit,
    onItemClick: ((String) -> Unit)? = null,
    itemDisplay: @Composable (Item) -> Unit = {
        InlineCardDisplay(
            name = it.title,
            detailText = it.description ?: "",
            avatar = it.imageUrlString,
            onClick = { if (onItemClick !== null) onItemClick(it.id) },
        )
    },
    viewModel: ListDisplayReadViewModel<Item>,
    lazyListState: LazyListState = rememberLazyListState(),
    shouldShowSearchBar: Boolean = true,
    placeHolder: @Composable (() -> Unit) = { Text("Search") },
    searchValue: MutableState<String> = remember { mutableStateOf("") },
    shouldHideHeaders: Boolean = true,
    header: @Composable (() -> Unit)? = null,
    emptyListView: @Composable (() -> Unit)? = null,
    separator: @Composable (() -> Unit)? = {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
    },
    searchAlgorithm: (String, String) -> Double = viewModel.searchAlgorithm
) = PageHeaderLayout(title = title, subtitle = description, onBackPress = onBackPress) {
    DefaultReadView(viewModel = viewModel, loader = { ListLoader() }, error = {
        GeneralError(message = it,
            description = "Please check your internet connection and try again.",
            onButtonClick = { viewModel.on(ReadViewModel.Event.OnRefresh) })
    }) {
        ListDisplay(
            items = it,
            onItemClick = onItemClick,
            state = rememberPullRefreshState(refreshing = viewModel.state.isRefreshing,
                onRefresh = { viewModel.on(ReadViewModel.Event.OnRefresh) }),
            itemDisplay = itemDisplay,
            isRefreshing = viewModel.state.isRefreshing,
            listState = lazyListState,
            shouldShowSearchBar = shouldShowSearchBar,
            placeHolder = placeHolder,
            searchValue = searchValue,
            header = header,
            emptyListView = emptyListView,
            separator = separator,
            searchAlgorithm = searchAlgorithm
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <Item : ListDisplayItemInterface> ListDisplay(
    items: List<Item>,
    onItemClick: ((String) -> Unit)? = null,
    itemDisplay: @Composable (Item) -> Unit = {
        InlineCardDisplay(
            name = it.title,
            detailText = it.description ?: "",
            avatar = it.imageUrlString,
            onClick = { if (onItemClick !== null) onItemClick(it.id) },
        )
    },
    state: PullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = { }),
    isRefreshing: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    shouldShowSearchBar: Boolean = true,
    placeHolder: @Composable (() -> Unit) = { Text("Search") },
    searchValue: MutableState<String> = remember { mutableStateOf("") },
    header: @Composable (() -> Unit)? = null,
    emptyListView: @Composable (() -> Unit)? = null,
    isScrollableToLastElement: Boolean = false,
    separator: @Composable (() -> Unit)? = {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
    },
    searchAlgorithm: (String, String) -> Double
) = ListDisplay(map = listOf(ListHeader {} to items).toMap(),
    onItemClick = onItemClick,
    state = state,
    itemDisplay = itemDisplay,
    isRefreshing = isRefreshing,
    listState = listState,
    shouldShowSearchBar = shouldShowSearchBar,
    placeHolder = placeHolder,
    searchValue = searchValue,
    shouldHideHeaders = true,
    header = header,
    emptyListView = emptyListView,
    isScrollableToLastElement = isScrollableToLastElement,
    separator = separator,
    searchAlgorithm = searchAlgorithm
)

data class ListHeader(
    val id: String = UUID.randomUUID().toString(),
    val display: @Composable () -> Unit,
)

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun <Item : ListDisplayItemInterface> ListDisplay(
    map: Map<ListHeader, List<Item>>,
    onItemClick: ((String) -> Unit)? = null,
    itemDisplay: @Composable (Item) -> Unit = {
        InlineCardDisplay(
            name = it.title,
            detailText = it.description ?: "",
            avatar = it.imageUrlString,
            onClick = { if (onItemClick !== null) onItemClick(it.id) },
        )
    },
    state: PullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = { }),
    isRefreshing: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    shouldShowSearchBar: Boolean = true,
    placeHolder: @Composable (() -> Unit) = { Text("Search") },
    searchValue: MutableState<String> = remember { mutableStateOf("") },
    header: @Composable (() -> Unit)? = null,
    shouldHideHeaders: Boolean = false,
    isScrollableToLastElement: Boolean = false,
    emptyListView: @Composable (() -> Unit)? = {
        GeneralError(
            message = "Data not available",
            description = "Please check your internet connection and try again."
        )
    },
    separator: @Composable (() -> Unit)? = {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
    },
    searchAlgorithm: (String, String) -> Double
) {
    Spacer(Modifier.height(24.dp))

    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    val searchableItems = remember(map) {
        map.entries.flatMap { it.value.map { value -> it.key to value } }
    }

    LaunchedEffect(key1 = searchValue.value) {
        scope.launch { listState.scrollToItem(0) }
    }

    val filteredItems: State<Map<ListHeader, List<Item>>> = remember(map) {
        derivedStateOf {
            if (searchValue.value.isBlank()) map.filter { it.value.isNotEmpty() }
            else searchableItems.asSequence().map {
                Pair(listOf(
                    it.second.title,
                    it.second.description
                ).mapNotNull { phrase -> phrase?.lowercase()?.split(" ") }.flatten()
                    .mapIndexed { i, word ->
                        val indexMultiplier: Double = (1.0 - (0.2 * (i)))
                        val similarity = searchAlgorithm(
                            word, searchValue.value.lowercase()
                        )
                        indexMultiplier * similarity
                    }.max(), it
                )
            }.filter { it.first > (searchValue.value.length / 20.0).coerceIn(0.0, 0.6) }
                .sortedByDescending { it.first }.map { it.second }.groupBy { it.first }
                .mapValues { it.value.map { item -> item.second } }.filter { it.value.isNotEmpty() }
        }
    }

    Column {
        if (shouldShowSearchBar) TextField(
            value = searchValue.value,
            singleLine = true,
            onValueChange = {
                searchValue.value = it
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search, contentDescription = "", modifier = Modifier.size(24.dp)
                )
            },
            placeholder = { placeHolder() },
            trailingIcon = {
                if (searchValue.value.isNotBlank()) {
                    IconButton(onClick = {
                        searchValue.value = ""
                        scope.launch { listState.scrollToItem(0) }
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
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

        PullToRefreshLayout(state = state, isRefreshing = isRefreshing) {
            Column {
                header?.let { it() }

                LazyColumn(state = listState) {
                    if (filteredItems.value.isEmpty()) item {
                        Box(Modifier.fillParentMaxHeight()) {
                            searchValue.value.let {
                                if (it.isBlank()) emptyListView?.let { it() } else GeneralError(
                                    modifier = Modifier.fillParentMaxHeight(),
                                    message = "No search results for \"$it\"",
                                    description = "• Make sure that all words are spelled correctly.\n" + "• Try different keywords.\n" + "• Try more general keywords."
                                )
                            }
                        }
                    } else filteredItems.value.onEach { it ->
                        if (!shouldHideHeaders) stickyHeader { it.key.display() }

                        itemsIndexed(it.value) { innerIndex, item ->
                            itemDisplay(item)

                            separator?.let {separator ->
                                if (innerIndex != it.value.size - 1 ) separator()
                            }
                        }
                    }

                    item {
                        if (isScrollableToLastElement) Spacer(
                            modifier = Modifier.height(
                                with(
                                    localDensity
                                ) { listState.layoutInfo.viewportEndOffset.toDp() } - 60.dp)
                        )
                    }
                }
            }
        }
    }
}
