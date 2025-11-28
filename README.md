# 📱 RePasse – Organizador de Doações  
Aplicativo Android nativo desenvolvido em **Kotlin** para cadastrar, visualizar, editar e excluir itens que serão doados.  
Projeto da disciplina **Desenvolvimento Mobile – PUCPR**.

---

## ✨ Funcionalidades

- **Login simples** para entrada no app.  
- **Lista de doações** cadastradas.
- **Cadastro de novos itens** com:
  - Nome  
  - Categoria  
  - Quantidade  
  - Condição  
  - Observações  
  - Status automático (“Pendente”)  
- **Edição de itens já cadastrados**  
  → Toque no item para abrir a tela de edição.  
- **Exclusão com Long Press**  
  → Pressione e segure um item para deletar.  

---

## 🧱 Arquitetura utilizada

O app utiliza uma estrutura simples, adequada para projetos iniciais:

### **3 Activities principais**
- **MainActivity** → Tela de login  
- **ListaDoacoesActivity** → Exibe a lista e permite gerenciar itens  
- **CadastroDoacaoActivity** → Criar e editar doações  

### **Persistência de dados**
- **SQLite** via `SQLiteOpenHelper`  
  → Banco local para CRUD completo (Create, Read, Update, Delete)

### **Listagem otimizada**
- **RecyclerView + Adapter customizado**  
  → Para exibir as doações com melhor performance

---

## 📁 Estrutura de Pastas

```text
app/
 ├── java/com.example.repasse/
 │    ├── MainActivity.kt
 │    ├── ListaDoacoesActivity.kt
 │    ├── CadastroDoacaoActivity.kt
 │    ├── Doacao.kt
 │    ├── DoacaoAdapter.kt
 │    └── DoacaoDbHelper.kt
 │
 └── res/
      ├── layout/
      ├── values/
      └── drawable/

