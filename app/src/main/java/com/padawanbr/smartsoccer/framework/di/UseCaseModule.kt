package com.padawanbr.smartsoccer.framework.di


import com.padawanbr.smartsoccer.core.usecase.AddGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.AddGroupUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.AddQuickCompetitionUseCase
import com.padawanbr.smartsoccer.core.usecase.AddQuickCompetitionUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.AddSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.AddSoccerPlayerUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.DeleteGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteGroupUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayerUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.GetSoccerPlayersByGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.GetSoccerPlayersByGroupUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.GetGroupsUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGroupsUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindAddGroupUseCase(useCaseImpl: AddGroupUseCaseImpl): AddGroupUseCase

    @Binds
    fun bindGetGroupsUseCase(useCaseImpl: GetGroupsUseCaseImpl): GetGroupsUseCase

    @Binds
    fun bindAddSoccerPlayerUseCase(useCaseImpl: AddSoccerPlayerUseCaseImpl): AddSoccerPlayerUseCase

    @Binds
    fun bindGetSoccerPlayersByGroupUseCase(useCaseImpl: GetSoccerPlayersByGroupUseCaseImpl): GetSoccerPlayersByGroupUseCase

    @Binds
    fun bindDeleteGroupUseCase(useCaseImpl: DeleteGroupUseCaseImpl): DeleteGroupUseCase

    @Binds
    fun bindDeleteSoccerPlayerUseCase(useCaseImpl: DeleteSoccerPlayerUseCaseImpl): DeleteSoccerPlayerUseCase

    @Binds
    fun bindGetGrupoComJogadoresByIdUseCase(useCaseImpl: GetGrupoComJogadoresByIdUseCaseImpl): GetGrupoComJogadoresByIdUseCase

    @Binds
    fun bindAddQuickCompetitionUseCase(useCaseImpl: AddQuickCompetitionUseCaseImpl): AddQuickCompetitionUseCase

}