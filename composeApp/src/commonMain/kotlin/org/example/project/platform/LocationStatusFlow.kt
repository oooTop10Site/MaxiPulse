package org.example.project.platform

import kotlinx.coroutines.flow.Flow


expect fun locationStatusFlow(): Flow<Boolean>
