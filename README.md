# Impossible Differential
ImDiff.java -- The Java source code of our algorithm for finding impossible differentials of many block cipher structures,
 including Gen-CAST256, Misty, Gen-Skipjack, Four-Cell, Gen-MARS, Gen-RC6, SMS4, MIBS, Camellia*, LBlock, E2 and SNAKE.
 
Impossible Differential.txt -- The impossible differential results for many block  cipher structures. 
The impossible differentials for block ciphers are denoted like this, 
for example, we have a line :

Gen-CAST256 r=17 plaintext = [0, 0, a, 0] ciphertext = [0, a, 0, 0] : true

which shows 
the block cipher structure is ：Gen-CAST256
encryption round we test is: 17
the impossible differential is:  [0,0,a,0] --> [0, a, 0, 0].
