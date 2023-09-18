package com.blimas.smartsoccer.framework.di


import com.blimas.smartsoccer.core.usecase.AddGroupUseCase
import com.blimas.smartsoccer.core.usecase.AddGroupUseCaseImpl
import com.blimas.smartsoccer.core.usecase.AddQuickCompetitionUseCase
import com.blimas.smartsoccer.core.usecase.AddQuickCompetitionUseCaseImpl
import com.blimas.smartsoccer.core.usecase.AddSoccerPlayerUseCase
import com.blimas.smartsoccer.core.usecase.AddSoccerPlayerUseCaseImpl
import com.blimas.smartsoccer.core.usecase.DeleteCompetitionUseCase
import com.blimas.smartsoccer.core.usecase.DeleteCompetitionUseCaseImpl
import com.blimas.smartsoccer.core.usecase.DeleteGroupUseCase
import com.blimas.smartsoccer.core.usecase.DeleteGroupUseCaseImpl
import com.blimas.smartsoccer.core.usecase.DeleteSoccerPlayerUseCase
import com.blimas.smartsoccer.core.usecase.DeleteSoccerPlayerUseCaseImpl
import com.blimas.smartsoccer.core.usecase.GetCompetitionUseCase
import com.blimas.smartsoccer.core.usecase.GetCompetitionUseCaseImpl
import com.blimas.smartsoccer.core.usecase.GetSoccerPlayersByGroupUseCase
import com.blimas.smartsoccer.core.usecase.GetSoccerPlayersByGroupUseCaseImpl
import com.blimas.smartsoccer.core.usecase.GetGroupsUseCase
import com.blimas.smartsoccer.core.usecase.GetGroupsUseCaseImpl
import com.blimas.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCase
import com.blimas.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCaseImpl
import com.blimas.smartsoccer.core.usecase.GetGrupoComJogadoresETorneiosUseCase
import com.blimas.smartsoccer.core.usecase.GetGrupoComJogadoresETorneiosUseCaseImpl
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
    fun bindGetGrupoComJogadoresETorneiosUseCase(useCaseImpl: GetGrupoComJogadoresETorneiosUseCaseImpl): GetGrupoComJogadoresETorneiosUseCase

    @Binds
    fun bindAddQuickCompetitionUseCase(useCaseImpl: AddQuickCompetitionUseCaseImpl): AddQuickCompetitionUseCase

    @Binds
    fun bindGetCompetitionUseCase(useCaseImpl: GetCompetitionUseCaseImpl): GetCompetitionUseCase

    @Binds
    fun bindDeleteCompetitionUseCase(useCaseImpl: DeleteCompetitionUseCaseImpl): DeleteCompetitionUseCase

}