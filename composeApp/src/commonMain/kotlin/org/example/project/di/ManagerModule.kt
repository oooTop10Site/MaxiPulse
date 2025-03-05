package org.example.project.di

//import com.plusmobileapps.konnectivity.Konnectivity
import com.russhwolf.settings.Settings
import org.example.project.data.repository.AiAssistantManager
import org.example.project.data.repository.AiAssistantManagerImpl
import org.example.project.data.repository.AuthRepositoryImpl
import org.example.project.domain.manager.AuthManager
import org.example.project.domain.manager.AuthManagerImpl
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.domain.manager.MessageObserverManagerImpl
import org.example.project.domain.repository.AuthRepository
import org.example.project.platform.ScanBluetoothSensorsManager
import org.example.project.platform.SpeechToTextRecognizer
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val moduleManager = module {
//    singleOf(::Konnectivity)
    singleOf(::AuthManagerImpl) { bind<AuthManager>() }
    singleOf(::MessageObserverManagerImpl) { bind<MessageObserverManager>() }
    singleOf(::AiAssistantManagerImpl) { bind<AiAssistantManager>() }
    singleOf(::ScanBluetoothSensorsManager)
    factoryOf(::SpeechToTextRecognizer)

}