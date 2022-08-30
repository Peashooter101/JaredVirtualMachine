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
- Special Thanks to [Hendrik](https://docs.henrikdev.xyz/) for their Valorant API.

---

### Planned Functionality

`/abby <get/add/remove/gallery>`
- Need more commands for usability without shutting down the bot!

`/valorant <profile/rank/history>`
- Pull generalized ranked data for use in `/valorant profile`.
- Pull detailed ranked data for the given episode in `/valorant rank`.
  - Provide an optional argument for Episode / Act.
- Pull and generate match history data for `/valorant history`.
