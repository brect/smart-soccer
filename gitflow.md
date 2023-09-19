# Fluxo de Branches com Git Flow

Este documento descreve o fluxo de branches ideal para o desenvolvimento de software usando o modelo Git Flow.

## Branches Principais

### `main`

Esta é a branch onde o código em produção reside. Todas as mudanças em `main` são consideradas parte da base de código de produção.

### `develop`

Esta é a branch de desenvolvimento, onde os recursos são integrados antes de serem lançados em produção.

## Branches Auxiliares

### `feature/xxx`

Estas são branches onde o desenvolvimento de novos recursos ocorre. Cada novo recurso deve residir em sua própria branch, que pode ser empurrada para a branch `develop` conforme necessário.

### `hotfix/xxx`

Estas são branches onde correções de bugs rápidas para a versão de produção ocorrem.

### `release/xxx`

Estas são branches que preparam o código na branch `develop` para ser mesclado na branch `main` e lançado em produção.

## Fluxo Ideal

### Novo Recurso / Melhoria

1. Crie uma branch de feature a partir de `develop`:

    ```bash
    git checkout develop
    git checkout -b feature/my-new-feature
    ```

2. Desenvolva o recurso.
3. Faça um pull request para mesclar `feature/my-new-feature` em `develop`.
4. Depois que o pull request for aprovado e os testes passarem, faça a mesclagem.

### Preparando um Release

1. Crie uma branch de release a partir de `develop`:

    ```bash
    git checkout develop
    git checkout -b release/1.2.0
    ```

2. Qualquer ajuste necessário para o release é feito nesta branch.
3. Faça um pull request para mesclar `release/1.2.0` em `main` e `develop`.
4. Quando o pull request for aprovado e todos os testes passarem, faça a mesclagem e marque a commit na branch `main` com o número da versão.

### Correções de Bugs Críticos (Hotfixes)

1. Crie uma branch de hotfix a partir de `main`:

    ```bash
    git checkout main
    git checkout -b hotfix/critical-bug
    ```

2. Corrija o bug e faça um pull request para mesclar `hotfix/critical-bug` em `main` e `develop`.
3. Depois que o pull request for aprovado e os testes passarem, faça a mesclagem.

## Observações

- O nome das branches `feature/`, `hotfix/` e `release/` geralmente inclui um identificador para o recurso ou correção que está sendo desenvolvido. Por exemplo, `feature/user-authentication`, `hotfix/login-bug`, `release/1.2.0`.

- Antes de mesclar qualquer pull request, certifique-se de que ele atenda aos critérios definidos nas regras de proteção de branch (como revisões de código aprovadas, verificações de status passadas, etc.).