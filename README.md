📱 RePasse – App Mobile de Organização de Doações

Projeto desenvolvido para a disciplina de Desenvolvimento Mobile – PUCPR

O RePasse é um aplicativo Android nativo desenvolvido em Kotlin, criado para facilitar o registro e organização de itens destinados a doação.
O app permite adicionar, visualizar, editar e excluir doações, oferecendo uma interface simples, prática e responsiva.

🎯 Funcionalidades principais

Login básico
Apenas para entrada no app (não há autenticação real nesta versão).

Cadastro de novas doações
Inclui:

Nome do item

Categoria

Quantidade

Condição

Observações opcionais

Listagem das doações em cards

Exibição organizada usando RecyclerView

Cards limpos e com boa hierarquia visual

Mostra nome, categoria, quantidade, condição e observações

Edição de doações já cadastradas
Toque no item → abre tela de edição.

Exclusão com “long press”
Segure o item da lista → opção de deletar.

🧱 Arquitetura utilizada

O app utiliza uma estrutura simples, adequada para projetos iniciais:

3 Activities principais

MainActivity → Tela de login

ListaDoacoesActivity → Lista e gerenciamento de doações

CadastroDoacaoActivity → Criar/editar doações

SQLite (via SQLiteOpenHelper)
Banco de dados local para CRUD completo.

RecyclerView + Adapter personalizado
Para listar itens de forma otimizada.

🗂 Estrutura de Pastas
app/
 ├── java/com.example.repasse/
 │   ├── MainActivity.kt
 │   ├── ListaDoacoesActivity.kt
 │   ├── CadastroDoacaoActivity.kt
 │   ├── Doacao.kt
 │   ├── DoacaoAdapter.kt
 │   └── DoacaoDbHelper.kt
 └── res/
     ├── layout/
     ├── values/
     └── drawable/

💾 Tecnologias usadas

Kotlin

Android Studio

SQLite

RecyclerView

Material Design básico

🚀 Como rodar o projeto

Clone este repositório:

git clone https://github.com/carolyneluz/RePasse.git


Abra o projeto no Android Studio

Aguarde o Gradle sincronizar

Rode no emulador ou em um dispositivo físico
