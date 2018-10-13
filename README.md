#Tele2 test assignment

Create a simple command line app that calls a remote API and displays results on STDOUT.  
#### The app should:  
- send a call to Chuck Norris database and retrieve a random joke (http://www.icndb.com/api/)

#### What are we after?
- we like to see clean, well abstracted code
- at some point we may want to replace the network driver to a diferent library
- at some point we would like the ability to easily plug in more joke databases (for simplicity let&#39;s
agree the API has the same in/out as the icndb.com one)