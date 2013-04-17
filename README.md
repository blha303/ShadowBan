ShadowBan
---------

[![Build Status](https://travis-ci.org/blha303/ShadowBan.png?branch=master)](https://travis-ci.org/blha303/ShadowBan)

This plugin attempts to duplicate reddit's shadow-ban feature. Once shadow-banned, any messages from the player are hidden to everyone but themselves.

![Banned player view](http://i.imgur.com/qS1JF0R.png) *Banned player*

![View for everyone else](http://i.imgur.com/EnJ2GVk.png) *Everyone else*

Commands
----------------
* **/shadowban <player>...** (*shadowban.use*) - Shadowbans a player (hides their chat messages from everyone else) - Alias: **/sban**
* **/shadowbanoffline <player>...** (*shadowban.offline*) - Shadowbans a player without checking for valid username - Alias: **/sbano**
* **/shadowbanreload** (*shadowban.reload*) - Reloads ShadowBan's config from file, discarding changes - Alias: **/sbanr**
* **/shadowunban <player>...** (*shadowban.unban*) - Removes the shadowban from a player (requires their exact username) - Alias: **/sunban**
* **/shadowbanlist** (*shadowban.list*) - Lists all current shadowbans - Alias: **/sbanl**

Links
--------
Development builds of this project can be acquired at the provided continuous integration server. These builds have not been approved by the BukkitDev staff. Use them at your own risk.

* Jenkins: [ci.drtshock.com/job/ShadowBan](http://ci.drtshock.com/view/blha303/job/ShadowBan/)
