# gitup

A Minecraft mod used for backing up clients and servers using Git™.

Git and the Git logo are trademarks of the Software Freedom Conservancy.
This project is independent and is not associated with Git or the Git project in any way.

Gitup depends on the Git CLI being installed and on the user's PATH, and also requires for the game instance to have an already configured Git repo with push privledges.
All credentials are handled by the Git CLI itself and is not read or stored by Gitup.
All network access comes from Git commands using the repos already configured for that repository.

# How to use

1. Install the .jar file to your mods/ directory
2. Start the game, then configure the mod in mods/gitup.json

# Configuration

Gitup supports the following options in gitup.json:

1. extraArgs.fetch:     Add extra flags to when Gitup invokes git fetch.
2. extraArgs.pull:       Add extra flags to when Gitup invokes git pull.
3. extraArgs.add:        Add extra flags to when Gitup invokes git add.
4. extraArgs.commit:     Add extra flags to when Gitup invokes git commit.
5. extraArgs.push:       Add extra flags to when Gitup invokes git push.
6. extraArgs.diff_index: Add extra flags to when Gitup invokes git diff-index.
7. pushInterval: How often Gitup should attempt to create a backup. 0 = disable.
8. pushIntervalUnit: What unit the pushInterval option is in. SECONDS, MINUTES, HOURS, DAYS, WEEKS, and YEARS are currently supported.
9. commitMessageFormat: The text format to be used in the message of git commit. This option supports Java DateTimeFormatter formats.
10. lastPushTimestamp: The timestamp of the last backup. Should generally be ignored by most users.

# Dependencies

1. Cloth Config API
2. Fabric API

# Future features

1. An automatic configurable git pull or git reset when there are repeated game crashes
