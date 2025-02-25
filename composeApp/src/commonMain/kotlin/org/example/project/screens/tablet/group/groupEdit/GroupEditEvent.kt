package org.example.project.screens.tablet.group.groupEdit

sealed interface GroupEditEvent {
    object Success: GroupEditEvent
    object SuccessDelete: GroupEditEvent
    object SuccessNavBack: GroupEditEvent
}