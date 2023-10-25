package com.padawanbr.smartsoccer.framework.di


import com.padawanbr.smartsoccer.core.usecase.AddGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.AddGroupUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.AddQuickCompetitionUseCase
import com.padawanbr.smartsoccer.core.usecase.AddQuickCompetitionUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.AddSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.AddSoccerPlayerUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.AddSoccersPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.AddSoccersPlayerUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.DeleteCompetitionUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteCompetitionUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.DeleteGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteGroupUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayerUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayersByGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayersByGroupUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.GetCompetitionUseCase
import com.padawanbr.smartsoccer.core.usecase.GetCompetitionUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.GetSoccerPlayersByGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.GetSoccerPlayersByGroupUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.GetGroupsUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGroupsUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCaseImpl
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresETorneiosUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresETorneiosUseCaseImpl
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
    fun bindAddSoccersPlayerUseCase(useCaseImpl: AddSoccersPlayerUseCaseImpl): AddSoccersPlayerUseCase

    @Binds
    fun bindGetSoccerPlayersByGroupUseCase(useCaseImpl: GetSoccerPlayersByGroupUseCaseImpl): GetSoccerPlayersByGroupUseCase

    @Binds
    fun bindDeleteGroupUseCase(useCaseImpl: DeleteGroupUseCaseImpl): DeleteGroupUseCase

    @Binds
    fun bindDeleteSoccerPlayerUseCase(useCaseImpl: DeleteSoccerPlayerUseCaseImpl): DeleteSoccerPlayerUseCase

    @Binds
    fun bindDeleteSoccerPlayersByGroupUseCase(useCaseImpl: DeleteSoccerPlayersByGroupUseCaseImpl): DeleteSoccerPlayersByGroupUseCase

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