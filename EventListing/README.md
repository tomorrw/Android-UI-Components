# Event Listing

Event Listing provides a way to display a list of events through EventCards.

## Usage
```kotlin
import com.tomorrow.eventlisting.EventCard
import com.tomorrow.eventlisting.EventList
import com.tomorrow.eventlisting.EventLoader
import com.tomorrow.eventlisting.presentationModel.EventCardModel
```


## Components
### Views

#### EventCard
```kotlin
EventCard(
    modifier: Modifier = Modifier, 
    event: EventCardModel?, 
    rightIcon: @Composable (String) -> Unit = {}
)
```

#### EventList
```kotlin
EventsList(
    events: List<EventCardModel>,
    state: PullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = { }),
    isRefreshing: Boolean = false,
    lazyListState: LazyListState = rememberLazyListState(),
    days: List<LocalDate>,
    selectedDay: LocalDate?,
    emptyView: @Composable () -> Unit = /* Default emptyView */,
    eventCard: @Composable (EventCardModel) -> Unit = /* Default eventCard */,
    onDaySelected: (LocalDate) -> Unit,
)
```

### Models
inherit this model to create your own event card model
```kotlin
open class EventCardModel(
    val id: String = "123",
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val title: String,
    val topic: String?,
    val speakers: List<EventSpeaker>,
    val location: String,
    val hasAttended: Boolean = false,
    val color: Color? = null,
    val onClick: (id: String) -> Unit = {},
)
// EventSpeaker
data class EventSpeaker(
    val speakerName: String,
    val speakerImage: String,
)
```