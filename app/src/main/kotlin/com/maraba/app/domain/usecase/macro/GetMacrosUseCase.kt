package com.maraba.app.domain.usecase.macro

import com.maraba.app.data.model.Macro
import com.maraba.app.data.repository.MacroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * GetMacrosUseCase — returns all macros as a reactive Flow.
 */
class GetMacrosUseCase @Inject constructor(
    private val macroRepository: MacroRepository
) {
    operator fun invoke(): Flow<List<Macro>> = macroRepository.observeAll()
}
