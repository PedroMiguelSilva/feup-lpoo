# LPOO1617_T5G6
Projects for the Object Oriented Programming Lab (LPOO) class of the Master in Informatics and Computer Engineering (MIEIC) at the Faculty of Engineering of the University of Porto (FEUP).
<br><br>
### Team Members
Bárbara Sofia Lopez de Carvalho Ferreira da Silva<br>
* Student Number: 201505628
* E-Mail: up201505628@fe.up.pt

Julieta Pintado Jorge Frade
* Student Number: 201506530
* E-Mail: up201506530@fe.up.pt
<br><br>

### Instalação da Aplicação
-----
. *A instalação da aplicação baseia-se somente na instalação do apk entregue. Há que ter em atenção que a versão de android mínima para correr a aplicação é a 5.0 Lollipop (API 21).*<br><br>

### Setup do Projeto
-----
. *Se o código do projeto for compilado, a opção de Sign In com o Google é provável que não funcione. Isto deve-se ao facto de para ter acesso a este serviço é preciso registar a fingerprint SHA-1 do projeto na Firebase, no entanto esta chave varia de computador para computador. Neste momento estão associadas a este projeto 2 chaves SHA-1, a dos computadores de cada membro deste grupo. Caso alguém queira compilar este projeto num computador diferente terá de fornecer a chave SHA-1 a um membro deste grupo para depois ser adicionada à Firebase.*<br><br>

### Padrões de Desenho
-----
. **singleton**<br>
. *Há duas classes que usam este padrão de desenho: ViewManager e DataManager. Estas classes albergam e gerem a informação da app da parte logica e do design.*<br><br>

### Decisões Relevantes
-----
. *As feautures que achamos mais apropriadas usar neste contexto foi Mobile (usada na obtenção da localização atual do utilizador através do GPS) e Social (usada para fazer login através de uma conta da google).* <br>
. *Foi implementada uma base de dados para guardar toda a informaçao utilizada na aplicação. Esta tem 3 tabelas: tabela de Users (int id, string name, string email, string password, bool googleAccount, string photoUrl), Categorias(int id, string name, int idUser) e Tasks (int id, string name, string day, bool checked, bool archived, long latitude, long longitude, int idCategory).
Usamos 3 classes para representar informação (User, Category e Task) para facilitar o manuseamento desta mesma informação.* <br>
. *Foram usadas as preferências do android no intuito do Login ser feito automaticamente se o user não tiver feito Sign Out.* <br>
. *Foram usados Instrumented Tests para realizar os Unit Tests visto que era necessario o contexto para criar a base de dados de teste. (path: Android_App/MerakiApp/app/src/androidTest/java/com/damon/merakiapp/InstrumentedTests_DataBaseTests.java).*<br>
. *Visto que implementamos uma Navigation Drawer, foi fundamental a incorporação de fragmentos, de maneira a permitir uma navegação smooth na app.*<br>
. *Para alterar certos campos de informação, como o nome do utilizador, achou-se mais intuitivo criar dialogos em vez de atividades.*
<br><br>

### Dificuldades
-----
. *Efetivamente, foram notadas algumas dificuldades ao longo do desenvolvimento deste projeto. Primeiramente, foi a estruturaçao da parte lógica, visto que, foi a primeira vez que usámos uma base de dados. Acabamos por decidir que acessos diretos à base de dados era a melhor opção visto que não havia necessidade de "cache".*<br>
. *Em último lugar, toda a implementação em Android nativo revelou-se desafiante, pois não só era a primeira vez que lidavamos com a plataforma, como o tema nunca foi realmente abordado nas aulas, apenas no fim do semestre quando já era tarde de mais. Foram precisos longos dias e noites para cimentar e implementar todos os novos conceitos necessários ao bom funcionamento da aplicação.*<br><br>

### Conclusão
-----
. *O edificação deste projeto contribuiu para o desenvolvimento das nossas capacidades a nível de trabalho em equipa, pesquisa pro-ativa, estudo autodidato e, principalmente, para a aprendizagem de Android, Java e bases de dados.*<br><br>

### Tempo de Trabalho
-----
. **Bárbara Sofia Silva**<br>
. *80 horas.* <br> <br>
. **Julieta Frade**<br>
. *40 horas.* <br> <br>

### Distribuiçao de Tarefas
-----
. **Bárbara Sofia Silva (50%)**<br>
. *Implementação de toda a parte gráfica da aplicação.*<br>
. *Implementação do ViewManager.* <br>
. *Implemetação do gps, mapa e escolha da localização.* <br>
. *Implementação do upload da foto de perfil.*<br><br>
. **Julieta Frade (50%)**<br>
. *Implementação da base de dados.*<br>
. *Implementação do DataManager.* <br>
. *Implementação do login com a API do google.*

### Manual de Utilização
-----
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/1.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/2.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/3.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/4.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/5.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/6.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/7.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/13.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/8.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/9.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/10.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/11.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/12.png" width="800"><br><br>
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UserManual/14.png" width="800"><br><br>

### UML
-----
<img src="https://github.com/literallysofia/LPOO1617_T5G6/blob/finalRelease/Android_App/Delivery/UML.png" width="1000"><br><br>
