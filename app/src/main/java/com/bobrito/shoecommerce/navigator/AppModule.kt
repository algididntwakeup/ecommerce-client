package com.bobrito.shoecommerce.navigator

import com.bobrito.navigator.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn // ← MISSING IMPORT INI!
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = AppNavigator() // ← TAMBAH () untuk instantiate class

}
