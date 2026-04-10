# Teste Pratico Iniflex

Este projeto foi desenvolvido em Java para atender aos requisitos do teste prático proposto.

## Estrutura do projeto

- `src/Pessoa.java`: classe base com os atributos `nome` e `dataNascimento`.
- `src/Funcionario.java`: herda de `Pessoa` e adiciona `salario` e `funcao`.
- `src/Principal.java`: classe principal que executa todos os requisitos do enunciado.

## Requisitos implementados

### 1. Classe Pessoa

A classe `Pessoa` possui:

- `nome` do tipo `String`
- `dataNascimento` do tipo `LocalDate`

### 2. Classe Funcionario

A classe `Funcionario` herda de `Pessoa` e possui:

- `salario` do tipo `BigDecimal`
- `funcao` do tipo `String`

Foi utilizado `BigDecimal` para evitar problemas de precisão em cálculos monetários.

### 3. Classe Principal

A classe `Principal` executa os itens pedidos:

- insere todos os funcionários com os mesmos dados da tabela
- remove o funcionário `João`
- imprime todos os funcionários com data no formato `dd/MM/yyyy`
- imprime valores monetários com ponto para milhar e vírgula para decimal
- aplica aumento de `10%` nos salários
- agrupa os funcionários por função em um `Map<String, List<Funcionario>>`
- imprime os grupos por função
- imprime os aniversariantes dos meses `10` e `12`
- encontra e imprime o funcionário com maior idade
- imprime os funcionários em ordem alfabética
- calcula o total dos salários
- calcula quantos salários mínimos cada funcionário recebe, considerando `R$ 1212,00`

## Decisões de implementação

- Foi usado `DateTimeFormatter` para padronizar o formato das datas.
- Foi usado `DecimalFormat` com símbolos do padrão brasileiro para formatar os valores.
- O agrupamento por função usa `LinkedHashMap` para manter a ordem de inserção.
- A maior idade é determinada pela menor data de nascimento.
- O cálculo de salários mínimos usa divisão com duas casas decimais e `RoundingMode.HALF_UP`.

## Comentários no código

O código contém comentários objetivos nas partes mais importantes, principalmente onde existe alguma decisão de implementação, como:

- remoção do funcionário `João`
- atualização dos salários
- agrupamento com preservação de ordem

## Como executar

No terminal, dentro da pasta do projeto:

```bash
javac src\Pessoa.java src\Funcionario.java src\Principal.java
java -cp src Principal
```

Se estiver usando IntelliJ, também é possível executar diretamente a classe `Principal`.

## Saída esperada

Ao executar, o programa imprime no console todos os resultados dos itens do teste em sequência.
