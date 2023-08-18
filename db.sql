--Cria grupos
INSERT OR REPLACE INTO `grupo` (`id`,`nome`,`quantidadeTimes`,`tipoEsporte`,`quantidadeMinimaPorTime`)
VALUES
    ('b467a7bc-4015-4e4f-844b-e16b17b0d0ae', 'Nome do grupo 1', 2, 'FUTEBOL_CAMPO', 11),
    ('c5f7d816-0c76-48c4-b15a-895cbf87f2a3', 'Nome do grupo 2', 2, 'FUTEBOL_CAMPO', 11),
    ('e5b18485-855d-47e1-9c44-2d4c890ca108', 'Nome do grupo 3', 2, 'FUTEBOL_CAMPO', 11),
    ('f7a1b92e-e1c2-4379-99b2-08f2e1583c5a', 'Nome do grupo 4', 2, 'FUTEBOL_CAMPO', 11),
    ('ee827d88-42d6-4ff0-bde2-b7c06d138e88', 'Nome do grupo 5', 2, 'FUTEBOL_CAMPO', 11);

--Cria jogadores
INSERT INTO `jogador` (`jogadorId`,`nome`,`idade`,`posicao`,`habilidades`,`estaNoDepartamentoMedico`,`grupoId`) VALUES
    ('6d20cf4b-6e7b-4701-948d-2d8491ad8c13', 'Nome Jogador 1', 30, 'GOLEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('a71e3e72-1d59-415e-80d1-5b462486c51d', 'Nome Jogador 2', 35, 'GOLEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('6a8c605a-92f2-4da4-8eb0-1e5151ea18a0', 'Nome Jogador 3', 40, 'LATERAL', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('2d50e11b-0d15-4ec1-82d7-947f01122f59', 'Nome Jogador 4', 45, 'LATERAL', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('77c50e8e-63d6-47da-bcd4-6e6e7cc5c8ca', 'Nome Jogador 5', 50, 'ZAGUEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('65d3b8a5-015e-4f76-8a1a-8e9538a61f78', 'Nome Jogador 6', 55, 'ZAGUEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('6f41320f-aae4-4c95-988f-1aa5ebd68bae', 'Nome Jogador 7', 60, 'ZAGUEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('5c8a0e7f-7847-4b9e-bc5a-bc8f0b66d144', 'Nome Jogador 8', 35, 'ZAGUEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('f676b169-0799-4a4d-8846-905d4d1303a1', 'Nome Jogador 9', 40, 'ZAGUEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('68f1271f-7291-4b5c-a122-25e74c37a8a0', 'Nome Jogador 10', 45, 'ZAGUEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('8977175d-c04f-4a81-980b-869ce4f45ac2', 'Nome Jogador 11', 50, 'ZAGUEIRO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('86d22591-e589-4845-8e3a-b90e67f14c82', 'Nome Jogador 12', 55, 'VOLANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('a6b48792-6fe0-4433-9e45-13e7f1c23bb0', 'Nome Jogador 13', 60, 'VOLANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('da64c7db-4de6-41d3-babf-28651b30e1e6', 'Nome Jogador 14', 30, 'VOLANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('25a6727e-e855-41a0-8f1d-4c6c918b32f2', 'Nome Jogador 15', 35, 'VOLANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('a55b8d89-d5aa-452a-a582-d5a9451e6dc9', 'Nome Jogador 16', 40, 'VOLANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('bfc00339-b82c-409d-bdaa-d6a09efb5973', 'Nome Jogador 17', 45, 'MEIA', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('7f6e1bf1-0c6a-4f3d-845e-8c7031f15582', 'Nome Jogador 18', 50, 'MEIA', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('06a8462f-13c3-40ea-8ebd-8b37e80cbf5a', 'Nome Jogador 19', 55, 'MEIA_OFENSIVO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('a576e0a0-52c0-4e80-9e2a-46b48848fb4d', 'Nome Jogador 20', 60, 'MEIA_OFENSIVO', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('0d7aa9c7-0865-4e21-9c1b-e94a794c7b06', 'Nome Jogador 21', 30, 'ATACANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('dc2a1f39-2a1c-4b17-af2f-62571781e0b0', 'Nome Jogador 22', 35, 'ATACANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('1e1d09b7-c243-4a72-9184-2d06c72715d6', 'Nome Jogador 23', 40, 'ATACANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('4d76b9d1-319b-4ed3-9dbb-5806e4e58f82', 'Nome Jogador 24', 45, 'ATACANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('ab6894a1-9f2d-4af2-82cd-0cebc89e8704', 'Nome Jogador 25', 50, 'ATACANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae'),
    ('6788f11e-ebc9-4c0e-9e47-5d6f9880736f', 'Nome Jogador 26', 55, 'ATACANTE', '{"Velocidade":4.0,"Chute":4.0,"Passe":4.0,"Marcação":4.0,"Drible":4.0,"Raça":4.0}', 0, 'b467a7bc-4015-4e4f-844b-e16b17b0d0ae');

--Cria torneio rápido
INSERT OR REPLACE INTO `torneio` (`id`,`nome`,`tipoTorneio`,`grupoId`) VALUES (?,?,?,?) with bindArgs: [af2ab4b5-3859-4b6f-9a9d-4601878b27b0, QuickCompetition, ELIMINATORIAS, b467a7bc-4015-4e4f-844b-e16b17b0d0ae]
INSERT OR REPLACE INTO `time` (`id`,`nome`,`mediaHabilidades`,`torneioId`) VALUES (?,?,?,?) with bindArgs: [b2a714d0-396d-4490-907e-cdf607302add,	Time A , 4.0 af2ab4b5-3859-4b6f-9a9d-4601878b27b0]
INSERT OR REPLACE INTO `time` (`id`,`nome`,`mediaHabilidades`,`torneioId`) VALUES (?,?,?,?) with bindArgs: [55ec5fa5-d6ae-4cc2-9c81-ad5b385c88c0, Time B, 4.0, af2ab4b5-3859-4b6f-9a9d-4601878b27b0]



--Consulta torneios
input:
SELECT * FROM torneio WHERE torneioId = ("6838c831-36dc-489f-9318-4dfbd8bbb14b")
output:
torneioId | nome | tipoTorneio | grupoId
a5377866-ee0f-49a1-a88e-654476d60a74	QuickCompetition	JOGO_UNICO	b467a7bc-4015-4e4f-844b-e16b17b0d0ae


input:
SELECT `timeId`,`nome`,`mediaHabilidades`,`torneioId` FROM `time` WHERE `torneioId` IN ("6838c831-36dc-489f-9318-4dfbd8bbb14b")
output:
timeId | nome | mediaHabilidades | torneioId
8b6fbaaf-70a1-4732-bc77-635d47ea125d	Time A	4.0	a5377866-ee0f-49a1-a88e-654476d60a74
7183fe7b-f276-4299-bc3b-34889a0c4321	Time B	4.0	a5377866-ee0f-49a1-a88e-654476d60a74

input:
SELECT
_junction.`timeId` ,
`jogador`.`jogadorId` AS `jogadorId`,
`jogador`.`nome` AS `nome`,
`jogador`.`idade` AS `idade`,
`jogador`.`posicao` AS `posicao`,
`jogador`.`habilidades` AS `habilidades`,
`jogador`.`estaNoDepartamentoMedico` AS `estaNoDepartamentoMedico`,
`jogador`.`grupoId` AS `grupoId`


FROM `time_jogador_cross_ref` AS _junction
INNER JOIN `jogador` ON (_junction.`jogadorId` = `jogador`.`jogadorId`)
WHERE _junction.`timeId` IN ("8fb16792-87d1-4138-b726-4658ebd5bfc2", "06a8462f-13c3-40ea-8ebd-8b37e80cbf5a")