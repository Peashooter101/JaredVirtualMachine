# JaredVirtualMachine

![stability shield](https://img.shields.io/badge/stability-not-red) ![usability shield](https://img.shields.io/badge/usability-prob_not-orange)

### Why Jared Virtual Machine?

You see, I had a dream... That I could stop abandoning projects as soon as I started them...

While this dream is most certainly still a wild fever dream, I wanted to practice my Java by applying it to a bot.
The problem with a previous bot I tried to make (PeaScript) was that I, at least at the time, was fairly new and inexperienced to JavaScript.
Made it very hard to develop anything past a bot that enjoyed responding to messages.

By using the [Java Discord API](https://jda.wiki/), I can build a slightly more complex bot that I'd like to use on my actual Discord server without the navigation, syntax, and just general confusion that comes from being too unfamiliar with a language.

Oh also, the name "Jared Virtual Machine" is a play on Java Virtual Machine so yeah!

---

### Functionality

> **Warning**
> 
> Yeah no, there isn't anything that I'd consider to be stable right now. If you plan on taking my bot, uhh... Good luck...

`!ping`
- Pong!

`/vc-invite <user>`
- Used for people who have permission to join a voice channel, but their friends don't have the required permission.
- Both users must be connected to a Voice Channel and then the person with permission can invite the other to the channel.
- Uses fancy buttons for interactions and handling the invite requests.

`/abby`
- We have a [friend](https://github.com/RhythmicSys) with a cute dog.
- The command gives us easy access to Abby pictures.
- The pictures are saved and pulled from this repository!

`/valorant profile <valorantName>`
- Fetches some data about the Valorant account.
- valorantName should look like `Peashooter101#7016`.
- Special Thanks to [Henrik](https://docs.henrikdev.xyz/) for their Valorant API.

---

### Planned Functionality

`/vc <invite/request> <user>`
- Change the format of the command to have two subcommands.
- `/vc request` should be the same as `/vc invite` but they are requesting permission to join.

`/abby <get/add/remove/gallery>`
- Need more commands for usability without shutting down the bot!

`/valorant <profile/rank/history>`
- Pull generalized ranked data for use in `/valorant profile`.
- Pull detailed ranked data for the given episode in `/valorant rank`.
  - Provide an optional argument for Episode / Act.
- Pull and generate match history data for `/valorant history`.
- Consider caching the data locally somehow... :thinking:

`/<game>`
- Similar to the `/valorant` command, pull game information depending on the game.
- Not a priority since most related practice will be done on the `/valorant` command.

`/meme <memeID> <topText> <bottomText>`
- Create a meme format!

`<Some text you sent>`
- The bot responds to messages you send sometimes, with somewhat related responses.
  - The Easy Route: Use the Sentiment Analysis or other Microsoft AI services.
  - The Hard Route: Decide to figure out what's "related" on your own.

---

### Experimentation

I created a little playground to keep track of some of the experiments I am conducting in relation to this project. Think of it as a place for me to store my practice runs without having to force the bot to run. Some of the early experimentation may not be present in the Experimentation Package since I made this a few weeks after initially starting the project. This package *should* not compile when running Maven Package.

You can find the [Experimentation Package here](https://github.com/Peashooter101/JaredVirtualMachine/tree/main/src/main/java/com/Peashooter101/experimentation).

> **Note**
> 
> This is meant to be a playground and will not be updated for any reason outside of me doing random tests.
> 
> I did not name it `test` since I am not unit testing this application with this package but I am considering getting JUnit anyway so I don't have to make tons of mains... Hm...

Some experiments I have conducted...
- Using HttpRequest, HttpResponse, and HttpClient for GET requests.
- Using Jackson API to map JSON into Objects.
- Merging, scaling, and modifying images using BufferedImage and Graphics ([OverlayImagesExp.java](https://github.com/Peashooter101/JaredVirtualMachine/blob/main/src/main/java/com/Peashooter101/experimentation/OverlayImagesExp.java)).

Some ideas I have...
- Using the [Imgur API](https://apidocs.imgur.com/) for images I don't really mind losing later on.
