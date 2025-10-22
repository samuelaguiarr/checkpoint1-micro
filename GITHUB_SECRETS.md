# Configuração de Secrets do GitHub

Para que os workflows funcionem corretamente, você precisa configurar os seguintes secrets no seu repositório GitHub:

## Secrets Necessários

### Para o workflow de CD (Continuous Delivery)

1. **DOCKER_USERNAME**: Seu nome de usuário do Docker Hub
2. **DOCKER_PASSWORD**: Sua senha ou token de acesso do Docker Hub

## Como configurar os secrets

1. Acesse seu repositório no GitHub
2. Vá em **Settings** > **Secrets and variables** > **Actions**
3. Clique em **New repository secret**
4. Adicione cada secret com o nome e valor correspondente

## Exemplo de configuração

```
DOCKER_USERNAME: seu-usuario-dockerhub
DOCKER_PASSWORD: sua-senha-ou-token-dockerhub
```

## Notas importantes

- O `DOCKER_PASSWORD` pode ser sua senha normal do Docker Hub ou um token de acesso (recomendado)
- Para criar um token de acesso no Docker Hub:
  1. Acesse https://hub.docker.com/settings/security
  2. Clique em "New Access Token"
  3. Dê um nome para o token
  4. Copie o token gerado e use como `DOCKER_PASSWORD`

## Verificação

Após configurar os secrets, os workflows devem funcionar automaticamente quando:
- Fizer push para branches develop/feature/hotfix (CI)
- Fizer pull request para main (CD)
- Fizer push de tag para main (Release)
