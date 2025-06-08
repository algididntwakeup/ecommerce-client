package com.bobrito.shoecommerce.navigator


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNavigator() : Navigator = AppNavigator

}