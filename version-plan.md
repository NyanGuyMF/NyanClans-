# NyanClans versions

## Snapshots
### 0.1.N 
project initial architecture

### 0.2.N 
#### basic fucntionatily
 - /clan [create|delete] commands
 - chat placeholders

### 0.3.N
 - /clan [invite|accept] commands
 - /clanchat command implementation with events API
 - /clan tpall commands

### 0.4.N
#### clan information is available:
 - clan name
 - clan level (with clanlevels.yml)
 - clan leader
 - clan welcome message
 - clan blance
 - is set home
 - clan PvP mode
 - members list with ranks

#### clan stats is available:
 - clan rating
 - time since clan created
 - the highest clan balance level
 - the highest number of members
 - players kills
 - kills of monsters
 - mob kills
 - general deaths of players
 - blocks placed
 - blocks destroyed

### 0.6.N too much work, so skip 0.5 version
 - implementation of stats and info backend commands
 - implementation of clans level system

### 0.7.N
#### clan ranks are available: /c ranks {subcommand}
 - create «rank alias» «rank colored name» [permissions list]
 - delete «rank alias»
 - info «alias» - information about rank (players, permissions etc.)
 - list - list of rank aliases
 - «rank alias» add «permission»
 - «rank alias» remove «permission»
 - «rank alias» rename «new name»
 - permissions - list of all permissions
 - /c setrank «player» «rank alias»

### 0.8.N
#### WorldGuard & WorldEdit integration:
 - clan homes are regions now
 - clanhome.yml with clan homes sizes
#### Vault integration:
 - /clan [pay|take] «amount» commands
 - /clan create now requires money
 - /clan delete - DO NOT FORGET TO DELETE CLAN BANK

### 0.9.N
 - Maybe clan wars functionality
 - known bugs fixes
 - hard test

### 1.0.N RELEASE
 - look for new functionality
 - bug fixes
