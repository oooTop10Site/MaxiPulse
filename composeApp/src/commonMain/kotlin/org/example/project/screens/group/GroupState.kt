package org.example.project.screens.group

import org.example.project.domain.model.composition.CompositionUI

data class GroupState (
    val search: String,
    val compositions: List<CompositionUI>
) {
    companion object {
        val InitState = GroupState("", listOf(
            CompositionUI(id = "1", title = "Symphony No. 5", member = 85),
            CompositionUI(id = "2", title = "Piano Concerto No. 21", member = 1),
            CompositionUI(id = "3", title = "Violin Sonata No. 9", member = 2),
            CompositionUI(id = "4", title = "The Four Seasons", member = 5),
            CompositionUI(id = "5", title = "Requiem in D minor", member = 100),
            CompositionUI(id = "6", title = "Cello Suite No. 1", member = 1),
            CompositionUI(id = "7", title = "Eine kleine Nachtmusik", member = 20),
            CompositionUI(id = "8", title = "Rhapsody in Blue", member = 40),
            CompositionUI(id = "9", title = "Clair de Lune", member = 1),
            CompositionUI(id = "10", title = "Boléro", member = 60),
            CompositionUI(id = "11", title = "Carnival of the Animals", member = 14),
            CompositionUI(id = "12", title = "Messiah", member = 100),
            CompositionUI(id = "13", title = "Swan Lake", member = 70),
            CompositionUI(id = "14", title = "Symphony No. 9", member = 100),
            CompositionUI(id = "15", title = "Hungarian Rhapsody No. 2", member = 1),
            CompositionUI(id = "16", title = "Canon in D", member = 3),
            CompositionUI(id = "17", title = "Symphony No. 40", member = 85),
            CompositionUI(id = "18", title = "Nutcracker Suite", member = 80),
            CompositionUI(id = "19", title = "Moonlight Sonata", member = 1),
            CompositionUI(id = "20", title = "Peer Gynt Suite No. 1", member = 60)
        ))
    }
}