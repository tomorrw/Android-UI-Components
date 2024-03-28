package com.tomorrow.videoplayer

sealed class VideoPlayerEvent {
    data object OnPauseToggle : VideoPlayerEvent()
    data object OnPause : VideoPlayerEvent()
    data object OnPlay : VideoPlayerEvent()
    class OnSeekChanged(val value: Float) : VideoPlayerEvent()
    data object OnFullScreenClicked : VideoPlayerEvent()
    class OnNewVideo(val videoUrl: String) : VideoPlayerEvent()
    data object ResetAll : VideoPlayerEvent()
    data object ToggleControls : VideoPlayerEvent()
}
