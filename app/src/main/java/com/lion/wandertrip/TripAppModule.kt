package com.lion.wandertrip

import com.lion.wandertrip.repository.UserRepository
import com.lion.wandertrip.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 모듈은 Hilt에게 객체를 어떻게 제공할지에 대한 규칙을 정의하며, 주로 @Provides 또는 @Binds 어노테이션을 사용하여 의존성을 생성합니다.
// @InstallIn(SingletonComponent::class)는 모듈이 SingletonComponent에 설치되어 해당 컴포넌트의 라이프사이클 동안 유효한 의존성으로 제공됨을 의미합니다.
@Module
@InstallIn(SingletonComponent ::class)
object TripAppModule {

    @Provides
    @Singleton
    fun userRepositoryProvider() : UserRepository {
        return UserRepository()
    }

    @Provides
    @Singleton
    fun userServiceProvider(userRepository: UserRepository) : UserService {
        return UserService(userRepository)
    }

}