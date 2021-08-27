package core.interfaces.usecase

interface RemoveFavoriteUseCase {
    suspend fun run(wordId: Int)
}
