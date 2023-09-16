<div align="center">
<a href="https://purpurmc.org"><img src="https://user-images.githubusercontent.com/74448585/150906023-101cd383-da82-4a3c-9603-a3b5741c3994.png" alt="Purpur"></a>

## PurpurClient

[![MIT License](https://img.shields.io/github/license/PurpurMC/PurpurClient?&logo=github)](LICENSE)
[![Join us on Discord](https://img.shields.io/discord/685683385313919172.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://purpurmc.org/discord)

[![PurpurClient's Stargazers](https://img.shields.io/github/stars/PurpurMC/PurpurClient?label=stars&logo=github)](https://github.com/PurpurMC/PurpurClient/stargazers)
[![PurpurClient Forks](https://img.shields.io/github/forks/PurpurMC/PurpurClient?label=forks&logo=github)](https://github.com/PurpurMC/PurpurClient/network/members)
[![PurpurClient Watchers](https://img.shields.io/github/watchers/PurpurMC/PurpurClient?label=watchers&logo=github)](https://github.com/PurpurMC/PurpurClient/watchers)

PurpurClient is a Fabric client mod that aims to fix various client-side bugs in the game. A lot of these bugfixes end up in the vanilla game eventually, but for those who can't wait, this mod is for you.

PurpurClient is designed to work together with [Purpur](https://github.com/PurpurMC/Purpur) servers (hence the name), but most features can still be used with any server type, as these are fixes to vanilla bugs that are not caused by Purpur itself.

</div>

### Current Features in 1.20.1:

* Customizable mob passenger offsets
* Adds bee counts inside beehives to debug screen¹
* Fancy Purpur-themed Loading Screen (can be disabled in the config)
* Fancy Window Title that changes based on what kind of server you're in ([translations can be PR'd](./src/main/resources/assets/purpurclient/lang/))
* Displays Custom Enchantments instead of stripping them from the client
* Removes the client-side limit of 255 levels

¹ only works when connected to [Purpur](https://github.com/PurpurMC/Purpur) servers

### Past Features (to get an idea of what this mod has done and will do in the future):

* Asynchronous chat processing (removed since Mojang's new chat system is confusing)
* Fix rain particles appearing under water [MC-131770](https://bugs.mojang.com/browse/MC-131770)
* Fix sky darkening when riding minecart not on tracks [MC-51150](https://bugs.mojang.com/browse/MC-51150)
* Fix chat stutter [MC-218167](https://bugs.mojang.com/browse/MC-218167)
* Fix warnings in logs about invalid flying attributes [Purpur-744](https://github.com/PurpurMC/Purpur/pull/744)
* Fix debug markers ignoring the blue and red values that are set [MC-234030](https://bugs.mojang.com/browse/MC-234030) (Shows as Unresolved even though it's been fixed)
* Fix client preemptively removing item entities from the world (MC-???)

Note: PurpurClient adds a fancy animated splash screen on game startup alongside a better window title message. Both of these can be disabled in the config if preferred.

## Contact
[![Join us on Discord](https://img.shields.io/discord/685683385313919172.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/mtAAnkk)

Join us on [Discord](https://purpurmc.org/discord)

## Downloads
[![Downloads](https://img.shields.io/modrinth/dt/purpurclient?color=00AF5C&label=modrinth&style=flat&logo=modrinth)](https://modrinth.com/mod/purpurclient/versions)

Downloads can be obtained from our page on [Modrinth](https://modrinth.com/mod/purpurclient/versions).

## License
[![MIT License](https://img.shields.io/github/license/PurpurMC/PurpurClient?&logo=github)](LICENSE)

All code is licensed under the MIT license, unless otherwise noted.

## Donate
We accept donations via [GitHub Sponsors](https://github.com/sponsors/purpurmc) and the [OpenCollective](https://opencollective.com/purpurmc).
