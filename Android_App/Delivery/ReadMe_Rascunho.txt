> SETUP/INSTALLATION PROCEDURE FOR APP
A instala��o da aplica��o baseia-se somente na instala��o do apk entregue. H� que ter em aten��o que a vers�o de android minima para correr a aplica��o � a 5.0 Lollipop (API 21).

> SETUP/INSTALLATION PROCEDURE FOR PROJECT
Se o c�digo do projeto for compilado, a op��o de sign in com o google � prov�vel que nao funcione. Isto deve-se ao facto de para ter acesso a este servi�o  � preciso registar a fingerprint SHA-1 do projeto na Firebase, no entanto esta chave varia de computador para computador. Neste momento est�o associadas a este projeto 2 chaves SHA-1, a dos computadores de cada membro deste grupo. Caso algu�m queira compilar este projeto num computador diferente ter� de fornecer a chave SHA-1 a um membro deste grupo para depois ser adicionada � Firebase.

> DESIGN PATTERS
Singleton - H� duas classes que usam este padr�o de desenho: ViewManager e DataManager. Estas classes albergam e gerem a informa��o da app da parte logica e do design.

> RELEVANT DESIGN DECISIONS
Foi implementada uma base de dados para guardar toda a informa�ao utilizada na aplica��o. Esta tem 3 tabelas: tabela de Users (int id, string name, string email, string password, bool googleAccount, string photoUrl), Categorias(int id, string name, int idUser) e Tasks (int id, string name, string day, bool checked, bool archived, long latitude, long longitude, int idCategory).
Usamos 3 classes para representar informa��o (User, Category e Task) para facilitar o manuseamento desta mesma informa��o.
Foram usadas as prefer�ncias do android no intuito do login ser feito automaticamente se o user nao tiver feito Sign Out.
Foram usados Instrumented Tests para realizar os Unit Tests visto que era necessario o contexto para criar a base de dados de teste.

> MAJOR DIFICULTIES
Uma das maiores dificuldades foi a estrutura�ao da parte l�gica visto que foi a primeira vez que us�mos uma base de dados. Acabamos por decidir que acessos diretos � base de dados era a melhor op��o visto que nao havia necessidade de "cache".

> LESSONS LEARNED
...

> OVERALL TIME SPENT DEVELOPING
...

> WORK DISTRIBUTION
Sofia Silva (50%):
Implementa��o de toda a parte grafica da aplica��o
Implementa��o do ViewManager
Implemeta��o do gps, mapa e escolha da localiza��o

Julieta Frade (50%):
Implementa��o da base de dados
Implementa��o do DataManager
Implementa��o do login com a API do google

