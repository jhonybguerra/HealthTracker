<h1 align="center">HealthTracker</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat" border="0" alt="API"></a>
  <br>
  <a href="https://wa.me/+5511986726064"><img alt="WhatsApp" src="https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white"/></a>
  <a href="https://www.linkedin.com/in/jhonybguerra/"><img alt="Linkedin" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"/></a>
  <a href="mailto:jhonybguerra@gmail.com"><img alt="Gmail" src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/></a>
</p>

<p align="center">  

⭐ Esse é um projeto para demonstrar meu conhecimento técnico no desenvolvimento Android nativo com Kotlin. Mais informações técnicas abaixo.

💪 Aplicativo composto de duas telas, que realiza cálculos de IMC, TMB e ingestão de água diária, baseados em inputs do usuário, com funcionalidade de salvar e exibir resultados em um banco de dados integrado.

</p>

</br>

<p float="left" align="center">
<img src="screenshots/screenshot_1.png" width="30%"/>
<img src="screenshots/screenshot_2.png" width="30%"/>
<img src="screenshots/screenshot_3.png" width="30%"/>
</p>

## Download
<a href='https://play.google.com/store/apps/details?id=com.jbgcomposer.healthtracker'><img width="20%" alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/></a>

Ou faça o download da <a href="apk/app-debug.apk?raw=true">APK diretamente</a>. Você pode ver <a href="https://www.google.com/search?q=como+instalar+um+apk+no+android">aqui</a> como instalar uma APK no seu aparelho android.

## Tecnologias usadas e bibliotecas de código aberto

- Minimum SDK level: 24
- [Linguagem Kotlin](https://kotlinlang.org/)
- TextViews, ImageViews, EditTexts, RecyclerViews e DenseDropdownInput
- Adapters, Toasts, AlertDialogs, Toolbar, Menu e Intents
- Utilização de listas e validações de formulários


- Jetpack

  - ViewBinding: Liga os componentes do XML no Kotlin através de uma classe que garante segurança de tipo e outras vantagens.
  - RoomDatabase: Integração com banco de dados e operações assíncronas com Threads
  
- Arquitetura
  - **HealthTracker** foi construído em MVC, sendo composto por duas telas.

## Features

### Entrada dos dados e exibição do resultado
<img src="screenshots/record_1.gif" width="25%"/>

O usuário insere os dados solicitados e ao clicar em um item da recyclerView, recebe o resultado através de um Dialog.

### Navegação e consulta do histórico salvo
<img src="screenshots/record_2.gif" width="25%"/>

O usuário pode rolar horizontalmente a recyclerView e navegar entre os históricos pelo menu da toolbar.

# Licença

```xml
Copyright [2023] [Jhony Bossolane Guerra]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
