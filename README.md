# Discordbot
A discordbot that uses JDA (discord api) and Lavalink to create a music player that takes commands from users.


This is a personal project that i decided to do since me and my friends wanted to listen to music together while sitting in a discord server. The code is 100% java and works with Discord through their api. To be able to playback audio files i used the Lavalink that handles the type of audio files that YouTube videos and Soundcloud uses.

by reading the documentation on the JDA the project was easily setup and i did not find anything "hard", overall a really fun project to do.


-------------------------------------------
The bot is currently constructed to handle the following user commands:
-------------------------------------------

!play link
 - The bot joins and plays the audiofile from the link a user has attached. If a audiofile already and the command is used again, the new file is added to a queue.


!leave
 - The bot leaves the channel

!pause
 - The audioplayback is paused

!resume
 - The audioplayback is resumed

!skip
 - Skips to the next track in queue


