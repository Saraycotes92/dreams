package com.cristhianbonilla.oraculo

sealed class DreamState {
    data class SuccessDream(val meaning: String) : DreamState()
    object Error : DreamState()
    object Loading : DreamState()
    object InitState : DreamState()
    object RequestDreamMeaning : DreamState()
}