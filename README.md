## Desafio Técnico GetNet
### __Criado os testes de API do site: https://reqres.in/__
* Para mapear os testes a serem automatizados e o mínimo aceitável de cobertura, foi utilizado as técnicas de testes: Partição de equivalência com tabela de decisão.
  
### __Para executar todos os testes, execute o seguinte comando:__
<code> mvn surefire:test </code>

### __Estrutura do projeto__
[desafioTecnicoGetNet](./desafioTecnicoGetNet/)
* [src/](./src/)
    * [test/](./test/)
        * [java/](./java/)
            * [com.fragoso/](./com.fragoso/)
                * [config/](./config/)
                    * [Configurations](./Configurations/)
                * [factory/](./factory/)
                    * [LoginDataFactory](./LoginDataFactory/)
                    * [RegisterDataFactory](./RegisterDataFactory/)
                    * [UserDataFactory](./UserDataFactory/)
                * [isoleted/](./isoleted/)
                    * [LoginTest](./LoginTest/)
                    * [RegisterTest](./RegisterTest/)
                    * [UserTest](./UserTest/)
                * [pojo/](./pojo/)
                    * [Login](./Login/)
                    * [Register](./Register/)
                    * [User](./User/)
        * [resources/](./resources/)
          * [bodies/](./bodies/)
            * [postUser.json](./postUser.json/)
            * [putUser.json](./putUser.json/)
          * [properties/](./properties/)
            * [test.properties](./test.properties/)
