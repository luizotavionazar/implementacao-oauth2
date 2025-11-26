# AUTENTICAÇÃO COM OAUTH2 (UTILIZANDO O GOOGLE)
Este projeto acadêmico demonstra o funcionamento básico do OAuth2 utilizando o Google como provedor de autenticação. A aplicação contém apenas uma página de login, cujo objetivo é permitir ao aluno compreender o fluxo essencial do OAuth2 na prática.

**OBJETIVO:**
- Implementar uma página simples de login que utilize o  OAuth2 com o Google para autenticar o usuário e exibir suas informações básicas de e-mail.

 **FUNCIONALIDADE**
  
* Botão “Entrar com Google”;
* Redirecionamento para a página de autenticação do Google;
* Retorno automático para a aplicação com o código de autorização (implícito na aplicação);
* Exibição dos dados da conta Google;
* Botão 'Sair' para logout;

**PROVEDOR UTILIZADO:**
  
* Google

**TECNOLOGIAS UTILIZADAS:**

* HTML, CSS, Java Script (Front-end);
* Spring Boot (Back-end).
  - spring-boot-starter-oauth2-client: Essa dependência habilita todos os fluxos OAuth2 e OpenID Connect que permitem ao usuário logar com contas externas.

**FUNCIONAMENTO E RISCOS:**

* Para que o protocolo OAuth2 funcione é necessário informar o Client ID (identificador público da aplicação que permite realizar a requisição) e Client Secret (senha confidencial para autenticação da aplicação) da conta Google.
* Como essas informações são especificas e sensíveis, principalmente o Client Secret - que não deve ficar exposto pois é uma informação crítica e sigilosa. Elas devem ser preenchidas diretamente no ambiente que a aplicação rodará.

* Para isso, é necessário definir as variaveis de ambiente **CLIENT_ID_GOOGLE** e **CLIENT_SECRET_GOOGLE** com os respectivos valores.

  * Exemplo em Terminal VsCode (Rodar no mesmo prompt de comando da aplicação antes de iniciar a mesma):
    - $Env:CLIENT_ID_GOOGLE = "[Client ID obtido no Google Cloud Console]"
    - $Env:CLIENT_SECRET_GOOGLE = "[Client Secret obtido no Google Cloud Console]"

  * Exemplo em Linux (Rodar no mesmo terminal da aplicação antes de iniciar a mesma):
    - export CLIENT_ID_GOOGLE=[Client ID obtido no Google Cloud Console]
    - export CLIENT_SECRET_GOOGLE=[Client Secret obtido no Google Cloud Console]

  * Exemplo em Windows (Rodar no mesmo prompt de comando da aplicação antes de iniciar a mesma):
    - set CLIENT_ID_GOOGLE=[Client ID obtido no Google Cloud Console]
    - set CLIENT_SECRET_GOOGLE=[Client Secret obtido no Google Cloud Console]

* Essas variáveis de ambiente serão lidas pela aplicação no momento da sua inicialização, permitindo que OAuth2 funcione corretamente.
 
* Para acessar a página de login, basta acessar 'localhost:8080' (a depender da porta utilizada) no navegador.

**Abaixo algumas imagens do funcionamento da aplicação desenvolvida:**

  <img width="1853" height="863" alt="Captura de tela 2025-11-19 234235" src="https://github.com/user-attachments/assets/be18c489-0371-401a-ad8a-967188965414" />
  

  <img width="1636" height="740" alt="Captura de tela 2025-11-19 234331" src="https://github.com/user-attachments/assets/21b87e6e-fda4-4d88-bea5-297444edcedf" />


<img width="1469" height="797" alt="Captura de tela 2025-11-19 234405" src="https://github.com/user-attachments/assets/929d2f62-cefa-4b75-a307-8cbf9123d2b7" />
<img width="1318" height="797" alt="Captura de tela 2025-11-19 234434" src="https://github.com/user-attachments/assets/ba6d782f-d2bd-4af7-9b46-2f237ea67148" />
