package com.studi.biblio.encode;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class Empreinte {
    public static final Random r = new Random(); //On crée notre objet Random
    public static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // On crée un string contenant l'ensemble des caractères

    public String getHash(String toHash){ //renvoie une empreinte sha256 du string donné en paramètre. 
        return DigestUtils.sha256Hex(toHash);
    }


    public String getSalt(int taille) {
        String s = ""; //On initialise une chaine vide.
        for (int i = 0; i < taille; i++) {
            s +=alphabet.charAt(r.nextInt(alphabet.length())); //On pioche un caractère au hasard dans la chaine "alphabet", et on le concatène à s.
        }
        return s; //On renvoie s
    }
    public String getHashSalt(String password, String salt){ //renvoie une empreinte sha256 du string donné en paramètre auquel on a ajouté le sel.
        return getHash(password + salt);
    }
    public boolean isValidPassword(String hash, String password, String salt){
        return (getHash(password + salt).equals(hash));
    }
}