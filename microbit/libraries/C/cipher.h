#ifndef __CIPHER_H__
#define __CIPHER_H__

/*
 * Vigenere Cipher is kind of polyalphabetic substitution method. 
 * It is used for encryption of alphabetic text. 
 * For encryption and decryption Vigenere Cipher Table is used in which alphabets from A to Z are written in 26 rows.
 */

/* Default key */
static const char default_key[] = "J'adoreLesChlibs";

/* @brief
 * Vigenere Cipher Encryption
 * For encryption take first letter of message and new key. Take the alphabet in Vigenere Cipher Table.
 * Repeat the same process for all remaining alphabets in message text.
 */
void encrypt(
    char *key, 
    char *message, 
    char *encryptedMsg
);

/* @brief
 * Vigenere Cipher Decryption
 * Take first alphabet of encrypted message and generated key. 
 * Analyze Vigenere Cipher Table, look for alphabet, the corresponding row will be the first alphabet of original message.
 * Repeat the same process for all the alphabets in encrypted message.
 */
void decrypt(
    char *key, 
    char *encryptedMsg,
    char *decryptedMsg
);

/* @brief
 * Here we have to obtain a new key by repeating the given key till its length become equal to original message length.
 */
void generateKey(
    char *key, 
    char *newKey, 
    int msgLen
);

#endif