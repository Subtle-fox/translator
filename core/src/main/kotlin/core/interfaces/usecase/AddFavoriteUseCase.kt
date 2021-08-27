package core.interfaces.usecase

interface AddFavoriteUseCase {
    suspend fun run(wordId: Int)
}
