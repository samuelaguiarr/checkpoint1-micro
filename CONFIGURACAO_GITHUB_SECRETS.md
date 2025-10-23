# Configuração dos Secrets do GitHub

Para que os workflows funcionem corretamente, é necessário configurar os seguintes secrets no repositório GitHub:

## Secrets Necessários

### 1. DOCKER_USERNAME
- **Descrição**: Nome de usuário do Docker Hub
- **Como configurar**:
  1. Acesse o repositório no GitHub
  2. Vá em Settings > Secrets and variables > Actions
  3. Clique em "New repository secret"
  4. Nome: `DOCKER_USERNAME`
  5. Valor: Seu nome de usuário do Docker Hub

### 2. DOCKER_PASSWORD
- **Descrição**: Senha ou token de acesso do Docker Hub
- **Como configurar**:
  1. Acesse o repositório no GitHub
  2. Vá em Settings > Secrets and variables > Actions
  3. Clique em "New repository secret"
  4. Nome: `DOCKER_PASSWORD`
  5. Valor: Sua senha ou token de acesso do Docker Hub

## Configuração do Docker Hub

1. Crie uma conta no Docker Hub (https://hub.docker.com)
2. Crie um repositório com o nome `globalsolution`
3. Configure as credenciais nos secrets do GitHub

## Verificação

Após configurar os secrets, os workflows serão executados automaticamente quando:
- **CI**: Push para branches `develop`, `feature`, `hotfix`
- **CD**: Pull Request para branch `main`
- **Release**: Push de tags para branch `main`

## Teste dos Workflows

Para testar os workflows:

1. **Teste CI**: Faça push para uma branch `develop`, `feature` ou `hotfix`
2. **Teste CD**: Crie um Pull Request para `main`
3. **Teste Release**: Crie e faça push de uma tag (ex: `v1.0.0`)

```bash
# Criar uma tag para testar o workflow de release
git tag v1.0.0
git push origin v1.0.0
```
