# Tracker TK303G

![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)
![Min SDK](https://img.shields.io/badge/minSdk-21-blue)
![Target SDK](https://img.shields.io/badge/targetSdk-37-blue)
![Version](https://img.shields.io/badge/version-4.1.0-orange)

Aplicativo Android para controlar rastreadores veiculares **Coban TK303G** por SMS, sem depender de nenhum servidor ou backend próprio: os comandos são montados no app e enviados diretamente por SMS para o número configurado do rastreador.

## Funcionalidades

- Envio dos comandos principais do protocolo Coban: localizar, bloquear/desbloquear veículo, verificar status, modo monitor/rastreador, GPRS/SMS, auto track
- Configuração de senha, APN, IP/porta do servidor e números autorizados
- Recebimento e histórico das respostas (callbacks) enviadas pelo rastreador via SMS
- Tutorial embutido explicando o passo a passo de configuração inicial
- Suporte a português (Brasil) e inglês

## Tecnologias

- Java, Android Views (sem Compose)
- [OrmLite](https://ormlite.com/) para persistência local
- AndroidX (AppCompat, Material Components)
- Google Mobile Ads (AdMob)
- Gradle / Android Gradle Plugin

## Requisitos

- Android Studio ou linha de comando com [Android SDK](https://developer.android.com/studio) configurado
- JDK 17
- Um rastreador Coban TK303G (ou compatível) com chip habilitado para SMS

## Como buildar

```bash
git clone git@github.com:pilovieira/tk303g.git
cd tk303g
./gradlew assembleDebug
```

O APK gerado fica em `app/build/outputs/apk/debug/`.

Para instalar direto em um dispositivo/emulador conectado:

```bash
./gradlew installDebug
```

## Estrutura do projeto

```
app/src/main/java/br/com/pilovieira/tk303g/
├── business/   # Montagem dos comandos do protocolo TK303G
├── comm/       # Envio e recebimento de SMS
├── location/   # Parsing e histórico de localização
├── log/        # Tela e persistência do log do servidor/rastreador
├── persist/    # Preferências e acesso a dados (OrmLite)
├── utils/      # Utilitários (idioma, anúncios, navegador)
└── view/       # Activities e Fragments (UI)
```

## Aviso

Este app é apenas um emissor de comandos SMS para o protocolo do rastreador. O uso é de inteira responsabilidade de quem o utiliza.

## Contato

Dúvidas ou sugestões: appsfuncionais@gmail.com
