package com.maraba.app.domain.usecase.macro

import com.maraba.app.data.model.Macro
import com.maraba.app.data.repository.MacroRepository
import java.util.UUID
import javax.inject.Inject

/**
 * CreateMacroUseCase — creates and persists a new macro.
 * TODO: implement validation logic
 */
class CreateMacroUseCase @Inject constructor(
    private val macroRepository: MacroRepository
) {
    suspend operator fun invoke(macro: Macro): Result<Macro> = runCatching {
        val newMacro = if (macro.id.isBlank()) {
            macro.copy(id = UUID.randomUUID().toString(), createdAt = System.currentTimeMillis())
        } else {
            macro
        }
        macroRepository.save(newMacro)
        newMacro
    }
}
