package yocxli.gallerysample.domain.usecase

interface UseCase<T> {
    suspend fun execute(): T
}