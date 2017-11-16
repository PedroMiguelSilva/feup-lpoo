> SETUP/INSTALLATION PROCEDURE FOR APP
A instalação da aplicação baseia-se somente na instalação do apk entregue. Há que ter em atenção que a versão de android minima para correr a aplicação é a 5.0 Lollipop (API 21).

> SETUP/INSTALLATION PROCEDURE FOR PROJECT
Se o código do projeto for compilado, a opção de sign in com o google é provável que nao funcione. Isto deve-se ao facto de para ter acesso a este serviço  é preciso registar a fingerprint SHA-1 do projeto na Firebase, no entanto esta chave varia de computador para computador. Neste momento estão associadas a este projeto 2 chaves SHA-1, a dos computadores de cada membro deste grupo. Caso alguém queira compilar este projeto num computador diferente terá de fornecer a chave SHA-1 a um membro deste grupo para depois ser adicionada à Firebase.

> DESIGN PATTERS
Singleton - Há duas classes que usam este padrão de desenho: ViewManager e DataManager. Estas classes albergam e gerem a informação da app da parte logica e do design.

> RELEVANT DESIGN DECISIONS
Foi implementada uma base de dados para guardar toda a informaçao utilizada na aplicação. Esta tem 3 tabelas: tabela de Users (int id, string name, string email, string password, bool googleAccount, string photoUrl), Categorias(int id, string name, int idUser) e Tasks (int id, string name, string day, bool checked, bool archived, long latitude, long longitude, int idCategory).
Usamos 3 classes para representar informação (User, Category e Task) para facilitar o manuseamento desta mesma informação.
Foram usadas as preferências do android no intuito do login ser feito automaticamente se o user nao tiver feito Sign Out.
Foram usados Instrumented Tests para realizar os Unit Tests visto que era necessario o contexto para criar a base de dados de teste.

> MAJOR DIFICULTIES
Uma das maiores dificuldades foi a estruturaçao da parte lógica visto que foi a primeira vez que usámos uma base de dados. Acabamos por decidir que acessos diretos à base de dados era a melhor opção visto que nao havia necessidade de "cache".

> LESSONS LEARNED
...

> OVERALL TIME SPENT DEVELOPING
...

> WORK DISTRIBUTION
Sofia Silva (50%):
Implementação de toda a parte grafica da aplicação
Implementação do ViewManager
Implemetação do gps, mapa e escolha da localização

Julieta Frade (50%):
Implementação da base de dados
Implementação do DataManager
Implementação do login com a API do google

