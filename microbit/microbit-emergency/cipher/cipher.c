#include <stdio.h>
#include <string.h>

#include "cipher.h"

/* @brief
 * Vigenere Cipher Encryption
 * For encryption take first letter of message and new key. Take the alphabet in Vigenere Cipher Table.
 * Repeat the same process for all remaining alphabets in message text.
 * 
 * The algorithm can be expressed in algebraic form as given below. The cipher text can be generated by below equation.
 * Ei = (Pi + Ki) mod 126
 * Here P is plain text and K is key.
 *
 * @params
 * key : The key for cipher : char *.
 * message : Message to encrypt : char *.
 * encryptedMsg : String where we put the encrypted message, size of message : char *.
 */
void encrypt(
    const char *key, 
    char *message, 
    char *encryptedMsg
) {
    int msgLen = strlen(message), i;
 
    char newKey[msgLen];
 
    /* Generating new key */
    generateKey(key ,newKey, msgLen);
 
    /* Encryption */
    for(i = 0; i < msgLen; ++i) {
        encryptedMsg[i] = ((message[i] + newKey[i]) % 126);
    }
 
    encryptedMsg[i] = '\0';

} /* encrypt */

/* @brief
 * Vigenere Cipher Decryption
 * Take first alphabet of encrypted message and generated key. 
 * Analyze Vigenere Cipher Table, look for alphabet, the corresponding row will be the first alphabet of original message.
 * Repeat the same process for all the alphabets in encrypted message.
 * 
 * Above process can be represented in algebraic form by following equation.
 * Pi = (Ei – Ki + 126) mod 126
 * Here P is plain text and K is key.
 *
 * @params
 * key : The key for cipher : char *.
 * message : Message to decrypt : char *.
 * decryptedMsg : String where we put the decrypted message, size of message : char *.
 */
void decrypt(
    const char *key, 
    char *encryptedMsg,
    char *decryptedMsg
) {
    int msgLen = strlen(encryptedMsg), i;
 
    char newKey[msgLen];
 
    /* Generating new key */
    generateKey(key ,newKey, msgLen);
 
    /* Decryption */
    for(i = 0; i < msgLen; ++i) {
        decryptedMsg[i] = (((encryptedMsg[i] - newKey[i]) + 126) % 126);
    }
 
    decryptedMsg[i] = '\0';

} /* decrypt */

/* @brief
 * Here we have to obtain a new key by repeating the given key till its length become equal to original message length.
 *
 * @params
 * key : The key for cipher : char *.
 * newKey : String where we put the new key, size of message : char *.
 * msgLen : Size of message : int.
 */
void generateKey(
    const char *key, 
    char *newKey, 
    int msgLen
) {
    int keyLen = strlen(key), 
        i, 
        j;
 
    /* Generating new key */
    for(i = 0, j = 0; i < msgLen; ++i, ++j){
        if(j == keyLen)
            j = 0;
 
        newKey[i] = key[j];
    }
 
    newKey[i] = '\0';

} /* generateKey */