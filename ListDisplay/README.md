# List Display

The List Display is a Searchable List that allows you to display a list of items with a search bar, custom header, categorized list and a loading state.

## Usage
```kotlin
//Views
import com.tomorrow.listdisplay.ListDisplay // or 
import com.tomorrow.listdisplay.ListDisplayPage
//Models
import com.tomorrow.listdisplay.ListDisplayItem
import com.tomorrow.listdisplay.ListHeader
//Model Interface
import com.tomorrow.listdisplay.ListDisplayItemInterface
//ReadViewModel implementation
import com.tomorrow.listdisplay.ListDisplayReadViewModel
```


## Components
### Views

#### ListDisplay Categorized
```kotlin
ListDisplay(
    map: Map<ListHeader, List<Item>>,
    onItemClick: ((String) -> Unit)? = null,
    itemDisplay: @Composable (Item) -> Unit = /* Default display */,
    state: PullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = { }),
    isRefreshing: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    shouldShowSearchBar: Boolean = true,
    searchValue: MutableState<String> = remember { mutableStateOf("") },
    header: @Composable (() -> Unit)? = null,
    shouldHideHeaders: Boolean = false,
    isScrollableToLastElement: Boolean = false,
    emptyListView: @Composable (() -> Unit)? = /* Default emptyListView */,
    separator: @Composable (() -> Unit)? = /* Default separator */,
    searchAlgorithm: (String, String) -> Double
)
```

#### ListDisplay Not Categorized
```kotlin
ListDisplay(
    items: List<Item>,
    onItemClick: ((String) -> Unit)? = null,
    itemDisplay: @Composable (Item) -> Unit = /* Default item */,
    state: PullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = { }),
    isRefreshing: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    shouldShowSearchBar: Boolean = true,
    searchValue: MutableState<String> = remember { mutableStateOf("") },
    header: @Composable (() -> Unit)? = null,
    emptyListView: @Composable (() -> Unit)? = null,
    isScrollableToLastElement: Boolean = false,
    separator: @Composable (() -> Unit)? = /* Default seperator */,
    searchAlgorithm: (String, String) -> Double
)
```

#### ListDisplayPage
```kotlin
ListDisplayPage(
    title: String?,
    description: String? = null,
    onBackPress: () -> Unit,
    onItemClick: ((String) -> Unit)? = null,
    itemDisplay: @Composable (Item) -> Unit = /* Default item */,
    viewModel: ListDisplayReadViewModel<Item>,
    lazyListState: LazyListState = rememberLazyListState(),
    shouldShowSearchBar: Boolean = true,
    searchValue: MutableState<String> = remember { mutableStateOf("") },
    shouldHideHeaders: Boolean = true,
    header: @Composable (() -> Unit)? = null,
    emptyListView: @Composable (() -> Unit)? = null,
    separator: @Composable (() -> Unit)? = /* Default seperator */,
    searchAlgorithm: (String, String) -> Double = viewModel.searchAlgorithm
)
```

### Models
#### The ListDisplayItem Conforms to the ListDisplayItemInterface with default ( null ) values
```kotlin
interface ListDisplayItemInterface {
    val id: String
    val title: String
    val description: String?
    val imageUrlString: String?
}
```

#### The ListHeader
```kotlin
ListHeader(
    val id: String = UUID.randomUUID().toString(),
    val display: @Composable () -> Unit,
)
```

### ReadViewModel implementation
used in the ListDisplayPage to unify search Algorithm and other states
```kotlin
open class ListDisplayReadViewModel<Item : ListDisplayItemInterface>(
    load: () -> Flow<List<Item>>,
    refresh: () -> Flow<List<Item>> = load,
    emptyCheck: (List<Item>) ->  Boolean = { it.isEmpty() },
    searchAlgorithm: (String, String) -> Double
): ReadViewModel...
```