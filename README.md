# GameShow

Plugin for a Game Show, allows multiple players to partake in challenges to find a word.  
The host decides whether a player has won a challenge or not.

When a player wins a challenge, they receive a random letter of the word they need to complete, players can guess what the word is, but if they do and it's wrong, they lose the game.

- **Commands** - show:
  - /show user help - _Get a list of all the user commands_
  - /show start <word> - _Start a game with a given word_
  - /show win <user> - _Appoint a winner and give them a letter_
  - /show reset - _Reset the game_
  - /show reload - _Reloads the configuration files_

- **Commands** - user:
  - /show user add <user> - _Add a user to the game_
  - /show user remove <user> - _Remove a user from the game_

- _Permission Nodes_:
  - show.host - _For hosting games_
  - show.admin - _For using admin commands_
