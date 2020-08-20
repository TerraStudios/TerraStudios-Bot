# TerraStudios-Bot
Discord Bot for the TerraStudios Discord Server. 

# Setting Up
In order to setup the bot, you must have a JDK capable of building the jar. Before setting up the bot there are a few different configurations needed. 

# Configurations
The following steps are needed in order to properly setup the bot:
- In the <code>config.json</code> the token for your discord bot must be entered in the <code>token</code> property 
- Along with the token the field <code>pastebin-dev-key</code> must contain a valid pastebin.com Developer Key. This is used for various commands including the listing of available timezones.
- A <code>customcommands.json</code> file needs to be created containing <code>[]</code> for JSON storage
- A <code>timezones.json</code> file needs to be created containing <code>{}</code> for JSON storage

# Commands
|    Command Name     |                         Description                         |                                    Usage                                     |
|:-------------------:|:-----------------------------------------------------------:|:----------------------------------------------------------------------------:|
|        play         |    Sends a DM to the user about how to access the game.     |                                     play                                     |
|     makecommand     |   Makes a command that will embed a message on execution.   |                          makecommand <command-name>                          |
|      listtimes      |      Lists all of the TimeZone regions to select from       |                                  listtimes                                   |
|      listtimes      |      Lists all of the TimeZone regions to select from       |                                  listtimes                                   |
|        ping         |           Returns the current latency of the bot.           |                                     ping                                     |
|     makecommand     |   Makes a command that will embed a message on execution.   |                          makecommand <command-name>                          |
|     editcommand     |                  Edits an existing command                  | editcommand <command-name> <title | text | color | image | name> <new-value> |
| fetchallcommanddata |        Retrieves all command data in a table format         |                             fetchallcommanddata                              |
|     makecommand     |   Makes a command that will embed a message on execution.   |                          makecommand <command-name>                          |
|    binarytotext     |             Converts base2 (binary) into text.              |                            binarytotext <binary>                             |
|        help         |          Displays the information about a command.          |                             help <command-name>                              |
|    texttobinary     |             Converts text into base2 (binary).              |                             texttobinary <text>                              |
|     makecommand     |   Makes a command that will embed a message on execution.   |                          makecommand <command-name>                          |
|       addtime       | Adds a user timezone (supports abbreviations and worldapi). |                          addtime <name> <timezone>                           |
|     makecommand     |   Makes a command that will embed a message on execution.   |                          makecommand <command-name>                          |
|      sendembed      |    Sends an embed to a specified channel on the server.     |                                  sendembed                                   |
|     makecommand     |   Makes a command that will embed a message on execution.   |                          makecommand <command-name>                          |
|        time         |        Displays the time of a user in military time.        |                                 time <name>                                  |
|      commands       |           Lists all of the commands for the bot.            |                                   commands                                   |
|        info         |            Displays information about the server            |                                     info                                     |

# Questions?
If you have any questions or you would like to try out the bot, join our server: https://discord.gg/F5rnvDu
