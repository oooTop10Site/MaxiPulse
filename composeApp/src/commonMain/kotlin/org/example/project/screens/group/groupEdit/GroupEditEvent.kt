package org.example.project.screens.group.groupEdit

sealed interface GroupEditEvent {
    object Success: GroupEditEvent
    object SuccessDelete: GroupEditEvent
}